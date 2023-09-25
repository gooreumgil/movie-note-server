package com.oldteam.movienote.core.domain.movie.repository;

import com.oldteam.movienote.core.domain.movie.MovieReviewStatistics;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieReviewStatisticsRepository extends JpaRepository<MovieReviewStatistics, Long> {
}
