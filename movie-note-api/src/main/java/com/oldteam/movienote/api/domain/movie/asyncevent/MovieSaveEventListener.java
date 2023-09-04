package com.oldteam.movienote.api.domain.movie.asyncevent;

import com.oldteam.movienote.api.domain.movie.dto.MovieSaveReqDto;
import com.oldteam.movienote.api.domain.movie.service.MovieService;
import com.oldteam.movienote.clients.kobis.dto.Director;
import com.oldteam.movienote.clients.kobis.dto.MovieList;
import com.oldteam.movienote.clients.kobis.provider.KobisProvider;
import com.oldteam.movienote.common.exception.HttpException;
import com.oldteam.movienote.core.domain.movie.Movie;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Slf4j
@Async
@Component
@RequiredArgsConstructor
public class MovieSaveEventListener {

    private final MovieService movieService;

    @EventListener
    public void handleMovieSaveEvent(List<MovieList> movieList) {
        for (MovieList movie : movieList) {
            String movieNm = movie.getMovieNm();
            String movieNmEn = movie.getMovieNmEn();
            String movieCd = movie.getMovieCd();

            try {
                movieService.save(new MovieSaveReqDto(movieNm, movieNmEn, movieCd));
            } catch (HttpException e) {
                log.error("MovieSaveEventListener -> handleMovieSaveEvent, errorMessage={}", e.getMessage());
            }

        }
    }

}
