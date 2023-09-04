package com.oldteam.movienote.core.domain.movie;

import com.oldteam.movienote.core.audit.AuditingDomain;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Movie extends AuditingDomain {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String nameEn;
    private String code;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    private List<MovieReview> movieReviewList = new ArrayList<>();


    public static Movie create(String name, String nameEn, String code) {
        Movie movie = new Movie();
        movie.name = name;
        movie.nameEn = nameEn;
        movie.code = code;
        return movie;
    }

    public void addMovieReview(MovieReview movieReview) {
        this.movieReviewList.add(movieReview);
        movieReview.setMovie(this);
    }
}
