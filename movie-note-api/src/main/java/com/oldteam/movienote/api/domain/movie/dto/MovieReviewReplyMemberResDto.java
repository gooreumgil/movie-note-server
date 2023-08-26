package com.oldteam.movienote.api.domain.movie.dto;

import com.oldteam.movienote.common.utils.AES256Util;
import com.oldteam.movienote.core.domain.member.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MovieReviewReplyMemberResDto {

    private Long id;
    private String nickname;
    private String email;

    public MovieReviewReplyMemberResDto(Member member) {
        this.id = member.getId();
        this.nickname = member.getNickname();
        try {
            this.email = AES256Util.decrypt(member.getEmail());
        } catch (Exception e) {
            log.error("email decrypt error, message={}", e.getMessage());
        }
    }
}
