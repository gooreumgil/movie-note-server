package com.oldteam.movienote.core.domain.movie.repository;

import com.oldteam.movienote.core.domain.movie.MovieReviewLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface MovieReviewLikeRepository extends JpaRepository<MovieReviewLike, Long> {
    boolean existsByMovieReviewIdAndMemberId(Long MovieReviewId, Long memberId);

    @Query("select movieReviewLike.id from MovieReviewLike movieReviewLike where movieReviewLike.movieReview.id = :movieReviewId and movieReviewLike.member.id = :memberId")
    Long findIdByMovieReviewIdAndMemberId(Long movieReviewId, Long memberId);

    int countAllByMovieReviewId(Long movieReviewId);

    @Modifying
    void deleteByIdAndMovieReviewIdAndMemberId(Long id, Long movieReviewId, Long memberId);


}
