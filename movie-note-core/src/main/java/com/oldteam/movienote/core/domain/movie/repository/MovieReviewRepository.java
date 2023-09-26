package com.oldteam.movienote.core.domain.movie.repository;

import com.oldteam.movienote.core.domain.movie.Movie;
import com.oldteam.movienote.core.domain.movie.MovieReview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MovieReviewRepository extends JpaRepository<MovieReview, Long> {

    @Query("select movieReview from MovieReview movieReview join fetch movieReview.member mem left join fetch movieReview.statistics left join fetch mem.uploadFile where movieReview.id = :id")
    Optional<MovieReview> findByIdJoinedMember(Long id);

    @Query(value = "select movieReview from MovieReview movieReview join fetch movieReview.member mem left join fetch movieReview.statistics left join fetch mem.uploadFile",
            countQuery = "select count (movieReview) from MovieReview movieReview")
    Page<MovieReview> findAllJoinedMember(Pageable pageable);

    @Query(value = "select movieReview from MovieReview movieReview join fetch movieReview.member mem left join fetch movieReview.statistics left join fetch mem.uploadFile where mem.id = :memberId",
            countQuery = "select count (movieReview) from MovieReview movieReview inner join movieReview.member mem where mem.id = :memberId")
    Page<MovieReview> findAllByMemberIdCustom(Long memberId, Pageable pageable);


}
