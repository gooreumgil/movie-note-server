package com.oldteam.movienote.api.domain.movie.controller;

import com.oldteam.movienote.api.domain.movie.asyncevent.MovieSaveEvent;
import com.oldteam.movienote.api.domain.movie.asyncevent.MovieSaveEventListener;
import com.oldteam.movienote.api.domain.movie.service.MovieService;
import com.oldteam.movienote.api.domain.movie.dto.MovieResDto;
import com.oldteam.movienote.api.domain.movie.dto.MovieSaveReqDto;
import com.oldteam.movienote.clients.kobis.dto.KobisMovieReqDto;
import com.oldteam.movienote.clients.kobis.dto.MovieList;
import com.oldteam.movienote.clients.kobis.dto.MovieListResult;
import com.oldteam.movienote.clients.kobis.dto.MovieListResultWrapper;
import com.oldteam.movienote.clients.kobis.provider.KobisProvider;
import com.oldteam.movienote.common.exception.HttpException;
import com.oldteam.movienote.common.exception.HttpExceptionCode;
import com.oldteam.movienote.core.domain.movie.Movie;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/movies")
public class MovieController {

    private final MovieService movieService;
    private final KobisProvider kobisProvider;
    private final MovieSaveEventListener movieSaveEventListener;

    @GetMapping("/{id}")
    public ResponseEntity<MovieResDto> findById(@PathVariable Long id) {
        Movie movie = movieService.findById(id).orElseThrow(() -> new HttpException(HttpStatus.BAD_REQUEST, HttpExceptionCode.NOT_FOUND, "존재하지 않는 movie 입니다. movieId -> " + id));
        MovieResDto movieResDto = new MovieResDto(movie);
        return ResponseEntity.ok(movieResDto);
    }

    @GetMapping
    public ResponseEntity<MovieListResultWrapper> findAll(@RequestParam String query) {

        MovieListResultWrapper movies;

        try {
            movies = kobisProvider.getMovies(query);
            MovieListResult movieListResult = movies.getMovieListResult();
            if (movieListResult != null && !movieListResult.getMovieList().isEmpty()) {
                List<MovieList> movieList = movieListResult.getMovieList();
                for (MovieList movie : movieList) {
                    Long movieId = movieService.findIdByNameAndCode(movie.getMovieNm(), movie.getMovieCd());
                    if (movieId != null) {
                        movie.setId(movieId);
                    }
                }
                movieSaveEventListener.handleMovieSaveEvent(movieList);
            }
        } catch (ExecutionException | InterruptedException e) {
            throw new HttpException(HttpStatus.BAD_REQUEST, HttpExceptionCode.EXTERNAL_EXCEPTION, e.getMessage());
        }

        return ResponseEntity.ok(movies);

    }

}
