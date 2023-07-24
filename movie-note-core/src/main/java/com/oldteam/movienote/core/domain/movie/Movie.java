package com.oldteam.movienote.core.domain.movie;

import com.oldteam.movienote.core.audit.AuditingDomain;
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
public class Movie extends AuditingDomain {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;
    private String imageUrl;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    private List<MovieReview> movieReviewList = new ArrayList<>();


    public static Movie create(String code, String imageUrl) {
        Movie movie = new Movie();
        movie.code = code;
        movie.imageUrl = imageUrl;
        return movie;
    }
}
