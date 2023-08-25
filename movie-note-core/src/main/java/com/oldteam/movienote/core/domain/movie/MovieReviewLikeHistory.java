package com.oldteam.movienote.core.domain.movie;

import com.oldteam.movienote.core.audit.AuditingDomain;
import com.oldteam.movienote.core.domain.member.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MovieReviewLikeHistory extends AuditingDomain {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ReviewLikeStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_review_id")
    private MovieReview movieReview;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public MovieReviewLikeHistory(ReviewLikeStatus status) {
        this.status = status;
    }

    public void setMovieReview(MovieReview movieReview) {
        this.movieReview = movieReview;
    }

    public void setMember(Member member) {
        this.member = member;
    }
}
