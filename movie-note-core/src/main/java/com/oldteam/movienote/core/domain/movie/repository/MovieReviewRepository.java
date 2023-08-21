package com.oldteam.movienote.core.domain.movie.repository;

import com.oldteam.movienote.core.domain.movie.MovieReview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MovieReviewRepository extends JpaRepository<MovieReview, Long> {

    @Query("select movieReview from MovieReview movieReview join fetch movieReview.member mem left join fetch mem.uploadFile")
    Page<MovieReview> findAllJoinedMember(Pageable pageable);

}
