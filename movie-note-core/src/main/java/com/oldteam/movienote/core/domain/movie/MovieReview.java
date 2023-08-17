package com.oldteam.movienote.core.domain.movie;

import com.oldteam.movienote.core.audit.AuditingDomain;
import com.oldteam.movienote.core.domain.member.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MovieReview extends AuditingDomain {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "movieReview", cascade = CascadeType.ALL)
    private List<MovieReviewUploadFileRelation> fileList = new ArrayList<>();

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
}
