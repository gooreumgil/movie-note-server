package com.oldteam.movienote.api.domain.movie;

import com.oldteam.movienote.api.domain.member.MemberService;
import com.oldteam.movienote.api.domain.movie.dto.MovieReviewSaveReqDto;
import com.oldteam.movienote.api.domain.movie.dto.MovieSaveReqDto;
import com.oldteam.movienote.api.domain.uploadfile.UploadFileService;
import com.oldteam.movienote.core.domain.member.Member;
import com.oldteam.movienote.core.domain.movie.Movie;
import com.oldteam.movienote.core.domain.movie.MovieReview;
import com.oldteam.movienote.core.domain.movie.MovieReviewUploadFileRelation;
import com.oldteam.movienote.core.domain.movie.repository.MovieRepository;
import com.oldteam.movienote.core.domain.uploadfile.UploadFile;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;
    private final MemberService memberService;
    private final UploadFileService uploadFileService;

    @Transactional
    public Movie save(MovieSaveReqDto dto) {
        String code = dto.getCode();
        String imageUrl = dto.getImageUrl();

        Movie movie = Movie.create(code, imageUrl);
        return movieRepository.save(movie);

    }

    @Transactional(readOnly = true)
    public Movie findById(Long id) {
        return movieRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    @Transactional
    public void addMovieReview(Long id, Long memberId, MovieReviewSaveReqDto dto) {

        Movie movie = findById(id);
        Member member = memberService.findById(memberId);
        List<Long> uploadFileIds = dto.getUploadFileIds();
        MovieReview movieReview = MovieReview.create(dto.getTitle(), dto.getContent());

        if (!uploadFileIds.isEmpty()) {
            List<UploadFile> uploadFileList = uploadFileService.findByIds(uploadFileIds);
            for (UploadFile uploadFile : uploadFileList) {
                MovieReviewUploadFileRelation movieReviewUploadFileRelation = new MovieReviewUploadFileRelation();
                movieReviewUploadFileRelation.setUploadFile(uploadFile);
                movieReview.addFile(movieReviewUploadFileRelation);
            }
        }

        movie.addMovieReview(movieReview);
        member.addMovieReview(movieReview);

    }

}
