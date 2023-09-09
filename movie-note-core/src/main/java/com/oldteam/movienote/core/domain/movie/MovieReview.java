package com.oldteam.movienote.core.domain.movie;

import com.oldteam.movienote.core.audit.AuditingDomain;
import com.oldteam.movienote.core.domain.member.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MovieReview extends AuditingDomain implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;

    @Column(columnDefinition = "MEDIUMTEXT")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "movieReview", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MovieReviewUploadFileRelation> fileList = new ArrayList<>();

    @OneToMany(mappedBy = "movieReview", cascade = CascadeType.ALL)
    private List<MovieReviewLike> likeList = new ArrayList<>();

    @OneToMany(mappedBy = "movieReview", cascade = CascadeType.ALL)
    private List<MovieReviewLikeHistory> likeHistoryList = new ArrayList<>();

    @OneToMany(mappedBy = "movieReview", cascade = CascadeType.ALL)
    private List<MovieReviewReply> replyList = new ArrayList<>();

    public static MovieReview create(String title, String content) {
        MovieReview movieReview = new MovieReview();
        movieReview.title = title;
        movieReview.content = content;
        return movieReview;
    }


    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public void addFile(MovieReviewUploadFileRelation movieReviewUploadFileRelation) {
        this.fileList.add(movieReviewUploadFileRelation);
        movieReviewUploadFileRelation.setMovieReview(this);
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void clearUploadFiles() {
        this.fileList.clear();
    }

    public void addLike(MovieReviewLike movieReviewLike) {
        this.likeList.add(movieReviewLike);
        movieReviewLike.setMovieReview(this);
    }

    public void addLikeHistory(MovieReviewLikeHistory movieReviewLikeHistory) {
        this.likeHistoryList.add(movieReviewLikeHistory);
        movieReviewLikeHistory.setMovieReview(this);
    }

    public void addReply(MovieReviewReply movieReviewReply) {
        this.replyList.add(movieReviewReply);
        movieReviewReply.setMovieReview(this);
    }
}
