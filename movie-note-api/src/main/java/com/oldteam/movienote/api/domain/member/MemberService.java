package com.oldteam.movienote.api.domain.member;

import com.oldteam.movienote.api.domain.member.dto.MemberSaveReqDto;
import com.oldteam.movienote.core.domain.member.Member;
import com.oldteam.movienote.core.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Member save(MemberSaveReqDto saveReqDto) {
        String name = saveReqDto.getName();
        String email = saveReqDto.getEmail();
        String password = saveReqDto.getPassword();
        Member member = Member.create(name, email, password);
        return memberRepository.save(member);
    }

    public Member findById(Long id) {
        return memberRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    @Transactional(readOnly = true)
    public List<Member> findAll() {
        return memberRepository.findAll();
    }

}
