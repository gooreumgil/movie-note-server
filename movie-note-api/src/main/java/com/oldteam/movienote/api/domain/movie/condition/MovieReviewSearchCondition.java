package com.oldteam.movienote.api.domain.movie.condition;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MovieReviewSearchCondition {

    private String query;
    private Long memberId;
    private Boolean isLike;

}
