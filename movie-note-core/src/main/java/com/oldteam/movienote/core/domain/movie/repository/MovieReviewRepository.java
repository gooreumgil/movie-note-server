package com.oldteam.movienote.core.domain.movie.repository;

import com.oldteam.movienote.core.domain.movie.MovieReview;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieReviewRepository extends JpaRepository<MovieReview, Long> {
}
