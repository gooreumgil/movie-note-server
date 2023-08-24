package com.oldteam.movienote.api.domain.movie.controller;

import com.oldteam.movienote.api.domain.movie.service.MovieService;
import com.oldteam.movienote.api.domain.movie.dto.MovieResDto;
import com.oldteam.movienote.api.domain.movie.dto.MovieSaveReqDto;
import com.oldteam.movienote.common.exception.HttpException;
import com.oldteam.movienote.common.exception.HttpExceptionCode;
import com.oldteam.movienote.core.domain.movie.Movie;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/movies")
public class MovieController {

    private final MovieService movieService;

    @GetMapping("/{id}")
    public ResponseEntity<MovieResDto> findOne(@PathVariable Long id) {
        Movie movie = movieService.findById(id).orElseThrow(() -> new HttpException(HttpStatus.BAD_REQUEST, HttpExceptionCode.NOT_FOUND, "존재하지 않는 movie 입니다. movieId -> " + id));
        MovieResDto movieResDto = new MovieResDto(movie.getId(), movie.getCode(), movie.getImageUrl());
        return ResponseEntity.ok(movieResDto);
    }

    @PostMapping
    public ResponseEntity<MovieResDto> save(@RequestBody MovieSaveReqDto dto) {
        Movie movie = movieService.save(dto);
        MovieResDto movieResDto = new MovieResDto(movie.getId(), movie.getCode(), movie.getImageUrl());
        return ResponseEntity.ok(movieResDto);
    }


}
