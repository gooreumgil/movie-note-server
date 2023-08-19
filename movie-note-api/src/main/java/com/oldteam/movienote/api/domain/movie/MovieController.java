package com.oldteam.movienote.api.domain.movie;

import com.oldteam.movienote.api.domain.member.mapper.MemberTokenMapper;
import com.oldteam.movienote.api.domain.movie.dto.MovieResDto;
import com.oldteam.movienote.api.domain.movie.dto.MovieReviewSaveReqDto;
import com.oldteam.movienote.api.domain.movie.dto.MovieSaveReqDto;
import com.oldteam.movienote.core.domain.movie.Movie;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/movies")
public class MovieController {

    private final MovieService movieService;

    @GetMapping("/{id}")
    public ResponseEntity<MovieResDto> findOne(@PathVariable Long id) {
        Movie movie = movieService.findById(id).orElseThrow(RuntimeException::new);
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
