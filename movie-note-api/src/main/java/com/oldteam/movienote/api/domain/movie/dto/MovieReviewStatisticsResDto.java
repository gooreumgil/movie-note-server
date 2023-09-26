package com.oldteam.movienote.api.domain.movie.dto;

import com.oldteam.movienote.core.domain.movie.MovieReviewStatistics;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MovieReviewStatisticsResDto {

    private Long id;
    private Integer replyTotal;
    private Integer likeTotal;
    private Integer viewsTotal;

    public MovieReviewStatisticsResDto(MovieReviewStatistics movieReviewStatistics) {
        this.id = movieReviewStatistics.getId();
        this.replyTotal = movieReviewStatistics.getReplyTotal();
        this.likeTotal = movieReviewStatistics.getLikeTotal();
        this.viewsTotal = movieReviewStatistics.getViewsTotal();
    }
}
