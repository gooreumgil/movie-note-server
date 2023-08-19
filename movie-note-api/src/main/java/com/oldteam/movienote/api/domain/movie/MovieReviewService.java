package com.oldteam.movienote.api.domain.movie;

import com.oldteam.movienote.api.domain.member.MemberService;
import com.oldteam.movienote.api.domain.movie.dto.MovieReviewSaveReqDto;
import com.oldteam.movienote.api.domain.uploadfile.UploadFileService;
import com.oldteam.movienote.core.domain.member.Member;
import com.oldteam.movienote.core.domain.movie.Movie;
import com.oldteam.movienote.core.domain.movie.MovieReview;
import com.oldteam.movienote.core.domain.movie.MovieReviewUploadFileRelation;
import com.oldteam.movienote.core.domain.movie.repository.MovieReviewRepository;
import com.oldteam.movienote.core.domain.uploadfile.UploadFile;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MovieReviewService {

    private final MovieReviewRepository movieReviewRepository;
    private final MovieService movieService;
    private final MemberService memberService;
    private final UploadFileService uploadFileService;

    public Page<MovieReview> findAll(Pageable pageable) {
        return movieReviewRepository.findAll(pageable);
    }

    @Transactional
    public void save(MovieReviewSaveReqDto dto, Long memberId) {

        Long movieId = dto.getMovieId();
        Member member = memberService.findById(memberId);
        List<Long> uploadFileIds = dto.getUploadFileIds();
        MovieReview movieReview = MovieReview.create(dto.getTitle(), dto.getContent());


        if (movieId != null) {
            movieService.findById(movieId)
                    .ifPresent(movie -> movie.addMovieReview(movieReview));
        }


        if (!uploadFileIds.isEmpty()) {
            List<UploadFile> uploadFileList = uploadFileService.findByIds(uploadFileIds);
            for (UploadFile uploadFile : uploadFileList) {
                MovieReviewUploadFileRelation movieReviewUploadFileRelation = new MovieReviewUploadFileRelation();
                movieReviewUploadFileRelation.setUploadFile(uploadFile);
                movieReview.addFile(movieReviewUploadFileRelation);
            }
        }

        member.addMovieReview(movieReview);

    }


}
