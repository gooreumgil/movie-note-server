package com.oldteam.movienote.api.domain.auth;

import com.oldteam.movienote.api.domain.auth.dto.*;
import com.oldteam.movienote.api.domain.member.MemberService;
import com.oldteam.movienote.api.domain.member.mapper.MemberTokenMapper;
import com.oldteam.movienote.api.utils.JwtUtil;
import com.oldteam.movienote.common.utils.AES256Util;
import com.oldteam.movienote.core.domain.member.Member;
import io.jsonwebtoken.Claims;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final MemberService memberService;
    private final JwtUtil jwtUtil;
    private static final String AUTHORIZATION = "Authorization";
    private static final String TOKEN_PREFIX_CONTAINS_SPACE = "Bearer ";

    @PostMapping("/sign-up")
    public ResponseEntity<Void> signUp(@ParameterObject @ModelAttribute AuthSignUpReqDto dto) {
        authService.signUp(dto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<AuthTokenResDto> login(@RequestBody AuthLoginReqDto dto) throws Exception {
        Member member = authService.login(dto);
        String decryptedEmail = AES256Util.decrypt(member.getEmail());
        String accessToken = jwtUtil.createToken(member);
        return ResponseEntity.ok(AuthTokenResDto.of(member, decryptedEmail, accessToken));
    }

    @PostMapping("/verification")
    public ResponseEntity<AuthTokenResDto> verify(HttpServletRequest request) {

        String authorization = request.getHeader(AUTHORIZATION);
        if (!StringUtils.hasText(authorization) || !authorization.contains(TOKEN_PREFIX_CONTAINS_SPACE)) {
            throw new RuntimeException("유효하지 않은 토큰입니다.");
        }

        String accessToken = authorization.substring(TOKEN_PREFIX_CONTAINS_SPACE.length());
        Claims claims = jwtUtil.getClaims(accessToken);
        if (claims == null) {
            throw new RuntimeException("유효하지 않은 토큰입니다.");
        }

        MemberTokenMapper memberTokenMapper = new MemberTokenMapper(claims);
        Member member = memberService.findById(memberTokenMapper.getId());
        String decryptedEmail;

        try {
            decryptedEmail = AES256Util.decrypt(member.getEmail());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        boolean tokenExpired = jwtUtil.checkTokenExpired(claims.getExpiration());
        if (tokenExpired) {
            try {

                String newAccessToken = jwtUtil.createToken(member);
                return ResponseEntity.ok(AuthTokenResDto.of(member, decryptedEmail, newAccessToken));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        return ResponseEntity.ok(AuthTokenResDto.of(member, decryptedEmail));

    }

}
