package com.oldteam.movienote.core.domain.movie;

import com.oldteam.movienote.core.audit.AuditingDomain;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MovieReviewStatistics extends AuditingDomain {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int replyTotal;
    private int likeTotal;
    private int viewsTotal;

    public static MovieReviewStatistics create () {
        MovieReviewStatistics movieReviewStatistics = new MovieReviewStatistics();
        movieReviewStatistics.replyTotal = 0;
        movieReviewStatistics.likeTotal = 0;
        movieReviewStatistics.viewsTotal = 0;
        return movieReviewStatistics;
    }

    public static MovieReviewStatistics create(int replyTotal, int likeTotal, int viewsTotal) {
        MovieReviewStatistics movieReviewStatistics = new MovieReviewStatistics();
        movieReviewStatistics.replyTotal = replyTotal;
        movieReviewStatistics.likeTotal = likeTotal;
        movieReviewStatistics.viewsTotal = viewsTotal;
        return movieReviewStatistics;
    }
}
