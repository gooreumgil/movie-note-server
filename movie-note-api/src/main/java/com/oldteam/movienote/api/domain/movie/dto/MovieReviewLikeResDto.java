package com.oldteam.movienote.api.domain.movie.dto;

import com.oldteam.movienote.core.domain.movie.MovieReviewLike;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MovieReviewLikeResDto {

    private Long id;
    private LocalDateTime createdDateTime;
    private LocalDateTime updatedDateTime;

    public MovieReviewLikeResDto(MovieReviewLike movieReviewLike) {
        this.id = id;
        this.createdDateTime = movieReviewLike.getCreatedDateTime();
        this.updatedDateTime = movieReviewLike.getUpdatedDateTime();
    }
}
