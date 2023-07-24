package com.oldteam.movienote.api.domain.movie;

import com.oldteam.movienote.api.domain.movie.dto.MovieSaveReqDto;
import com.oldteam.movienote.core.domain.movie.Movie;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/movies")
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

}
