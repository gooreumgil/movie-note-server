package com.oldteam.movienote.core.domain.movie.repository;

import com.oldteam.movienote.core.domain.movie.MovieReviewLikeHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieReviewLikeHistoryRepository extends JpaRepository<MovieReviewLikeHistory, Long> {
}
