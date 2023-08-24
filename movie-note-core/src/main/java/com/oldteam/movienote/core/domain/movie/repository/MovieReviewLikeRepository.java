package com.oldteam.movienote.core.domain.movie.repository;

import com.oldteam.movienote.core.domain.movie.MovieReviewLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieReviewLikeRepository extends JpaRepository<MovieReviewLike, Long> {
    boolean existsByMovieReviewIdAndMemberId(Long MovieReviewId, Long memberId);
}
