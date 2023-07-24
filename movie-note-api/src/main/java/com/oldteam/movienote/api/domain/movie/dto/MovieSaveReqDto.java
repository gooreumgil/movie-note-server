package com.oldteam.movienote.api.domain.movie.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MovieSaveReqDto {

    private String code;
    private String imageUrl;

}
