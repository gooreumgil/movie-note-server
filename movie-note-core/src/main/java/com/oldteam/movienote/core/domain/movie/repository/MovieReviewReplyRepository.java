package com.oldteam.movienote.core.domain.movie.repository;

import com.oldteam.movienote.core.domain.movie.MovieReviewReply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MovieReviewReplyRepository extends JpaRepository<MovieReviewReply, Long> {

    Optional<MovieReviewReply> findByIdAndMovieReviewId(Long id, Long movieReviewId);


    @Query("select reply from MovieReviewReply reply join fetch reply.member where reply.id = :id and reply.movieReview.id = :movieReviewId")
    Optional<MovieReviewReply> findByIdAndMovieReviewIdJoinedMember(Long id, Long movieReviewId);

    @Query(value = "select reply from MovieReviewReply reply join fetch reply.member where reply.movieReview.id = :movieReviewId",
            countQuery = "select count (reply) from MovieReviewReply reply inner join reply.member where reply.movieReview.id = :movieReviewId")
    Page<MovieReviewReply> findAllByMovieReviewIdJoinedMember(Long movieReviewId, Pageable pageable);

    int countAllByMovieReviewId(Long movieReviewId);

}
