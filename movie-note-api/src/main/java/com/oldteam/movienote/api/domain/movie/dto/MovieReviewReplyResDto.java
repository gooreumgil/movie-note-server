package com.oldteam.movienote.api.domain.movie.dto;

import com.oldteam.movienote.core.domain.movie.MovieReviewReply;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MovieReviewReplyResDto {

    private Long id;
    private String content;

    public MovieReviewReplyResDto(MovieReviewReply movieReviewReply) {
        this.id = movieReviewReply.getId();
        this.content = movieReviewReply.getContent();
    }
}
