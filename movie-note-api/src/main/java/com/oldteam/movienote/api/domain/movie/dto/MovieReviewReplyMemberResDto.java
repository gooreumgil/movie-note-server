package com.oldteam.movienote.api.domain.movie.dto;

import com.oldteam.movienote.common.utils.AES256Util;
import com.oldteam.movienote.core.domain.member.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MovieReviewReplyMemberResDto {

    private Long id;
    private String nickname;
    private String email;

    public MovieReviewReplyMemberResDto(Member member) throws Exception {
        this.id = member.getId();
        this.nickname = member.getNickname();
        this.email = AES256Util.decrypt(member.getEmail());
    }
}
