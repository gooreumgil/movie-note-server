package com.oldteam.movienote.api.domain.movie.dto;

import com.oldteam.movienote.core.domain.member.Member;
import com.oldteam.movienote.core.domain.movie.MovieReviewReply;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MovieReviewReplyResDto {

    private Long id;
    private String content;
    private LocalDateTime createdDateTime;
    private LocalDateTime updatedDateTime;
    private MovieReviewReplyMemberResDto member;

    public MovieReviewReplyResDto(MovieReviewReply movieReviewReply) {
        this.id = movieReviewReply.getId();
        this.content = movieReviewReply.getContent();
        this.createdDateTime = movieReviewReply.getCreatedDateTime();
        this.updatedDateTime = movieReviewReply.getUpdatedDateTime();
    }

    public void setMember(Member member) throws Exception {
        this.member = new MovieReviewReplyMemberResDto(member);
    }

}
