package com.oldteam.movienote.api.domain.movie.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MovieReviewSaveReqDto {

    private String title;
    private String content;
    private List<Long> uploadFileIds = new ArrayList<>();

}
