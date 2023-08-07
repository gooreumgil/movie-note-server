package com.oldteam.movienote.api.domain.auth;

import com.oldteam.movienote.api.domain.auth.dto.AuthLoginReqDto;
import com.oldteam.movienote.api.domain.auth.dto.AuthSignUpReqDto;
import com.oldteam.movienote.api.domain.member.MemberService;
import com.oldteam.movienote.clients.awsresource.service.AwsS3Service;
import com.oldteam.movienote.core.domain.member.Member;
import com.oldteam.movienote.core.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    public Member login(AuthLoginReqDto dto) {
        String email = dto.getEmail();
        String password = dto.getPassword();
        Member member = memberService.findByEmail(email);
        String memberPassword = member.getPassword();
        boolean matches = passwordEncoder.matches(password, memberPassword);
        if (!matches) {
            throw new RuntimeException();
        }

        return member;

    }

    @Transactional
    public Member signUp(AuthSignUpReqDto dto) {
        return memberService.save(dto);
    }

}
