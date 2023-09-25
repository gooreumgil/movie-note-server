package com.oldteam.movienote.api.domain.movie.dto;

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

}
