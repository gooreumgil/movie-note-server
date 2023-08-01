package com.oldteam.movienote.api.domain.member;

import com.oldteam.movienote.api.domain.member.dto.MemberSaveReqDto;
import com.oldteam.movienote.core.domain.member.Member;
import com.oldteam.movienote.core.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/{id}")
    public Member findOne(@PathVariable Long id) {
        return memberService.findById(id);
    }

    @PostMapping
    public Member save(@RequestBody MemberSaveReqDto dto) {
        return memberService.save(dto);
    }


}
