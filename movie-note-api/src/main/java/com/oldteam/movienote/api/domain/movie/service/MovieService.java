package com.oldteam.movienote.api.domain.movie.service;

import com.oldteam.movienote.api.domain.member.MemberService;
import com.oldteam.movienote.api.domain.movie.dto.MovieReviewSaveReqDto;
import com.oldteam.movienote.api.domain.movie.dto.MovieSaveReqDto;
import com.oldteam.movienote.api.domain.uploadfile.UploadFileService;
import com.oldteam.movienote.common.exception.HttpException;
import com.oldteam.movienote.common.exception.HttpExceptionCode;
import com.oldteam.movienote.core.domain.member.Member;
import com.oldteam.movienote.core.domain.movie.Movie;
import com.oldteam.movienote.core.domain.movie.MovieReview;
import com.oldteam.movienote.core.domain.movie.MovieReviewUploadFileRelation;
import com.oldteam.movienote.core.domain.movie.repository.MovieRepository;
import com.oldteam.movienote.core.domain.uploadfile.UploadFile;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;
    private final MemberService memberService;
    private final UploadFileService uploadFileService;

    @Transactional
    public Movie save(MovieSaveReqDto dto) {
        String name = dto.getName();
        String code = dto.getCode();

        if (existsByNameAndCode(name, code)) {
            throw new HttpException(HttpStatus.BAD_REQUEST, HttpExceptionCode.ALREADY_EXIST, "이미 존재하는 movie 입니다. name = " + name + ", code = " + code);
        }

        Movie movie = Movie.create(name, dto.getNameEn(), code);
        return movieRepository.save(movie);
    }

    @Transactional(readOnly = true)
    public Optional<Movie> findById(Long id) {
        return movieRepository.findById(id);
    }

    @Transactional
    public void addMovieReview(Long id, Long memberId, MovieReviewSaveReqDto dto) {

        Optional<Movie> optionalMovie = findById(id);
        Member member = memberService.findById(memberId)
                .orElseThrow(() -> new HttpException(
                        HttpStatus.BAD_REQUEST,
                        HttpExceptionCode.NOT_FOUND,
                        "존재하지 않는 회원입니다. memberId -> " + memberId));
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

        optionalMovie.ifPresent(movie -> movie.addMovieReview(movieReview));
        member.addMovieReview(movieReview);

    }

    public Boolean existsByName(String name) {
        return movieRepository.existsByName(name);
    }

    public Boolean existsByNameAndCode(String name, String code) {
        return movieRepository.existsByNameAndCode(name, code);
    }

}
