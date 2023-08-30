package com.oldteam.movienote.clients.kobis.dto;

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
public class MovieListResult {

    private Integer totCnt;
    private String source;
    private List<MovieList> movieList = new ArrayList<>();

}
