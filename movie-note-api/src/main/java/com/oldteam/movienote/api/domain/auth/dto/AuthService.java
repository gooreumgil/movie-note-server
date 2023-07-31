package com.oldteam.movienote.api.domain.auth.dto;

import com.oldteam.movienote.api.domain.member.MemberService;
import com.oldteam.movienote.core.domain.member.Member;
import com.oldteam.movienote.core.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final MemberService memberService;

    public void login(AuthLoginReqDto dto) {
        String email = dto.getEmail();
        String password = dto.getPassword();
        Member member = memberService.findByEmail(email);
        String memberPassword = member.getPassword();

    }

}
