package com.oldteam.movienote.clients.kobis.provider;

import com.oldteam.movienote.clients.kobis.dto.KobisMovieReqDto;
import com.oldteam.movienote.clients.kobis.dto.MovieList;
import com.oldteam.movienote.clients.kobis.dto.MovieListResultWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Slf4j
@RequiredArgsConstructor
public class KobisProvider {

    private final WebClient kobisClient;

    public MovieListResultWrapper getMovies(String query) throws ExecutionException, InterruptedException {

        StringBuilder requestUrlBuilder = new StringBuilder();

        appendUrlParam(requestUrlBuilder, "movieNm", query);

        String url = requestUrlBuilder.toString();

        return kobisClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(MovieListResultWrapper.class).toFuture().get();
    }

    public void appendUrlParam(StringBuilder stringBuilder, String paramName, String paramValue) {

        if (paramValue == null) {
            return;
        }

        if (stringBuilder.isEmpty()) {
            stringBuilder.append("?");
        } else {
            stringBuilder.append("&");
        }

        stringBuilder
                .append(paramName)
                .append("=")
                .append(paramValue);

    }


}
