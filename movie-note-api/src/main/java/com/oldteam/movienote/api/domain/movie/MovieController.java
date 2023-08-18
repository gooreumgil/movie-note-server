package com.oldteam.movienote.api.domain.movie;

import com.oldteam.movienote.api.domain.member.mapper.MemberTokenMapper;
import com.oldteam.movienote.api.domain.movie.dto.MovieReviewSaveReqDto;
import com.oldteam.movienote.api.domain.movie.dto.MovieSaveReqDto;
import com.oldteam.movienote.core.domain.movie.Movie;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public Movie findOne(@PathVariable Long id) {
        return movieService.findById(id);
    }

    @PostMapping
    public Movie save(@RequestBody MovieSaveReqDto dto) {
        return movieService.save(dto);
    }

    @PostMapping("/{id}/movie-reviews")
    public void addMovieReview(
            @PathVariable Long id,
            @RequestBody MovieReviewSaveReqDto dto,
            @AuthenticationPrincipal MemberTokenMapper tokenMapper) {

        movieService.addMovieReview(id, tokenMapper.getId(), dto);

    }


}
