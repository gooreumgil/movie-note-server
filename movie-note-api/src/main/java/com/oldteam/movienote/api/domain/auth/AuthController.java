package com.oldteam.movienote.api.domain.auth;

import com.oldteam.movienote.api.domain.auth.dto.AuthLoginReqDto;
import com.oldteam.movienote.api.domain.auth.dto.AuthSignUpReqDto;
import com.oldteam.movienote.api.domain.auth.dto.AuthSignUpResDto;
import com.oldteam.movienote.api.domain.auth.helper.AuthHelper;
import com.oldteam.movienote.api.utils.JwtUtil;
import com.oldteam.movienote.core.domain.member.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final AuthHelper authHelper;
    private final JwtUtil jwtUtil;

    @PostMapping("/sign-up")
    public ResponseEntity<AuthSignUpResDto> signUp(@RequestBody AuthSignUpReqDto dto) throws Exception {
        Member member = authService.signUp(dto);
        String accessToken = jwtUtil.createToken(member);
        AuthSignUpResDto authSignUpResDto = authHelper.convertToAuthSignUpResDto(member, accessToken);
        return ResponseEntity.ok(authSignUpResDto);
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody AuthLoginReqDto dto) {
        Member member = authService.login(dto);
        return null;
    }

}
