package com.oldteam.movienote.api.domain.movie.service;

import com.oldteam.movienote.core.domain.movie.MovieReviewStatistics;
import com.oldteam.movienote.core.domain.movie.repository.MovieReviewStatisticsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MovieReviewStatisticsService {

    private final MovieReviewStatisticsRepository movieReviewStatisticsRepository;

    @Transactional
    public MovieReviewStatistics save(int replyTotal, int likeTotal, int viewsTotal) {
        return movieReviewStatisticsRepository.save(MovieReviewStatistics.create(replyTotal, likeTotal, viewsTotal));
    }

    @Transactional
    public MovieReviewStatistics update(MovieReviewStatistics movieReviewStatistics) {
        return movieReviewStatisticsRepository.save(movieReviewStatistics);
    }

}
