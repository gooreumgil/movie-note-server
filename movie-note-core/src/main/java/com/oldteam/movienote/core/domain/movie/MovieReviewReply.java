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
public class MovieReviewReply extends AuditingDomain {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_review_id")
    private MovieReview movieReview;

    public static MovieReviewReply create(String content) {
        MovieReviewReply movieReviewReply = new MovieReviewReply();
        movieReviewReply.content = content;
        return movieReviewReply;
    }

    public void setMovieReview(MovieReview movieReview) {
        this.movieReview = movieReview;
    }
}
