package com.oldteam.movienote.api.domain.member;

import com.oldteam.movienote.api.domain.auth.dto.AuthSignUpReqDto;
import com.oldteam.movienote.common.utils.AES256Util;
import com.oldteam.movienote.core.domain.member.Member;
import com.oldteam.movienote.core.domain.member.MemberRole;
import com.oldteam.movienote.core.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Member save(AuthSignUpReqDto saveReqDto) {

        String name = saveReqDto.getName();
        String nickname = saveReqDto.getNickname();
        String email = saveReqDto.getEmail();
        String encryptEmail;

        try {
            encryptEmail = AES256Util.encrypt(email);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        String password = saveReqDto.getPassword();
        String encodedPassword = passwordEncoder.encode(password);
        Member member = Member.create(name, nickname, encryptEmail, encodedPassword, MemberRole.MEMBER);

        return memberRepository.save(member);
    }

    public Member findById(Long id) {
        return memberRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    public Member findByEmail(String email) {

        String encryptEmail;

        try {
            encryptEmail = AES256Util.encrypt(email);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return memberRepository.findByEmail(encryptEmail).orElseThrow(RuntimeException::new);
    }

    public List<Member> findAll() {
        return memberRepository.findAll();
    }

}
