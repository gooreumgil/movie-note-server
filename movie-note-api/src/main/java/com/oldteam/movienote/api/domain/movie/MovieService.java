package com.oldteam.movienote.api.domain.movie;

import com.oldteam.movienote.api.domain.movie.dto.MovieSaveReqDto;
import com.oldteam.movienote.core.domain.movie.Movie;
import com.oldteam.movienote.core.domain.movie.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;

    @Transactional
    public Movie save(MovieSaveReqDto dto) {
        String code = dto.getCode();
        String imageUrl = dto.getImageUrl();

        Movie movie = Movie.create(code, imageUrl);
        return movieRepository.save(movie);

    }

    @Transactional(readOnly = true)
    public Movie findById(Long id) {
        return movieRepository.findById(id).orElseThrow(RuntimeException::new);
    }

}
