package com.oldteam.movienote.api.domain.movie.dto;

import com.oldteam.movienote.core.domain.movie.Movie;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MovieResDto {

    private Long id;
    private String name;
    private String nameEn;
    private String code;
    private LocalDateTime createdDateTime;
    private LocalDateTime updatedDateTime;

    public MovieResDto(Movie movie) {
        this.id = movie.getId();
        this.name = movie.getName();
        this.nameEn = movie.getNameEn();
        this.code = movie.getCode();
        this.createdDateTime = movie.getCreatedDateTime();
        this.updatedDateTime = movie.getUpdatedDateTime();
    }
}
