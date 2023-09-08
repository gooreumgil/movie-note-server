package com.oldteam.movienote.core.domain.movie;

import com.oldteam.movienote.core.audit.AuditingDomain;
import com.oldteam.movienote.core.domain.uploadfile.UploadFile;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MovieReviewUploadFileRelation extends AuditingDomain {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_review_id")
    private MovieReview movieReview;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "upload_file_id")
    private UploadFile uploadFile;

    public void setMovieReview(MovieReview movieReview) {
        this.movieReview = movieReview;
    }

    public void setUploadFile(UploadFile uploadFile) {
        this.uploadFile = uploadFile;
    }
}
