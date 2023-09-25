package com.oldteam.movienote.api.domain.movie.service;

import com.oldteam.movienote.api.domain.member.MemberService;
import com.oldteam.movienote.api.domain.movie.condition.MovieReviewSearchCondition;
import com.oldteam.movienote.api.domain.movie.dto.MovieReviewReplySaveReqDto;
import com.oldteam.movienote.api.domain.movie.dto.MovieReviewSaveReqDto;
import com.oldteam.movienote.api.domain.movie.dto.MovieReviewUpdateReqDto;
import com.oldteam.movienote.api.domain.movie.repository.MovieReviewQuerydslRepository;
import com.oldteam.movienote.api.domain.uploadfile.UploadFileService;
import com.oldteam.movienote.clients.awsresource.service.AwsS3Service;
import com.oldteam.movienote.common.exception.HttpException;
import com.oldteam.movienote.common.exception.HttpExceptionCode;
import com.oldteam.movienote.core.domain.member.Member;
import com.oldteam.movienote.core.domain.movie.*;
import com.oldteam.movienote.core.domain.movie.repository.MovieReviewRepository;
import com.oldteam.movienote.core.domain.uploadfile.UploadFile;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
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
    private final MovieReviewQuerydslRepository movieReviewQuerydslRepository;
    private final MovieReviewStatisticsService movieReviewStatisticsService;
    private final MovieService movieService;
    private final MemberService memberService;
    private final MovieReviewLikeService movieReviewLikeService;
    private final UploadFileService uploadFileService;
    private final AwsS3Service awsS3Service;

    public Page<MovieReview> findAll(Pageable pageable) {
        return movieReviewRepository.findAllJoinedMember(pageable);
    }

    public Page<MovieReview> findAllByCondition(MovieReviewSearchCondition condition, Pageable pageable) {
        return movieReviewQuerydslRepository.findAllByCondition(condition, pageable);
    }

    public Page<MovieReview> findAllByMemberId(Long memberId, Pageable pageable) {
        return movieReviewRepository.findAllByMemberIdCustom(memberId, pageable);
    }

    @Transactional
    public MovieReview save(MovieReviewSaveReqDto dto, Long memberId) {

        Long movieId = dto.getMovieId();
        Member member = memberService.findById(memberId)
                .orElseThrow(() -> new HttpException(
                        HttpStatus.BAD_REQUEST,
                        HttpExceptionCode.NOT_FOUND,
                        "존재하지 않는 회원입니다. memberId -> " + memberId));
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

        return movieReview;

    }

    @Transactional
    public MovieReview update(Long id, MovieReviewUpdateReqDto dto) {

        MovieReview movieReview = findById(id)
                .orElseThrow(() -> new HttpException(HttpStatus.BAD_REQUEST, HttpExceptionCode.NOT_FOUND, "존재하지 않는 movieReview 입니다." + id));

        String title = dto.getTitle();
        String content = dto.getContent();

        movieReview.update(title, content);

        List<Long> uploadFileIds = dto.getUploadFileIds();

        if (!uploadFileIds.isEmpty()) {

            List<MovieReviewUploadFileRelation> fileList = movieReview.getFileList();
            for (MovieReviewUploadFileRelation movieReviewUploadFileRelation : fileList) {
                UploadFile uploadFile = movieReviewUploadFileRelation.getUploadFile();
                awsS3Service.deleteFile(uploadFile.getS3Key());
            }

            movieReview.clearUploadFiles();

            List<UploadFile> uploadFileList = uploadFileService.findByIds(uploadFileIds);
            for (UploadFile uploadFile : uploadFileList) {
                MovieReviewUploadFileRelation movieReviewUploadFileRelation = new MovieReviewUploadFileRelation();
                movieReviewUploadFileRelation.setUploadFile(uploadFile);
                movieReview.addFile(movieReviewUploadFileRelation);
            }

            movieReviewRepository.save(movieReview);

        }

        return movieReview;
    }

    @Transactional
    public void deleteById(Long id, Long memberId) {
        Optional<MovieReview> optionalMovieReview = movieReviewRepository.findById(id);
        if (optionalMovieReview.isEmpty()) {
            throw new HttpException(HttpStatus.BAD_REQUEST, HttpExceptionCode.NOT_FOUND, "존재하지 않는 movieReview 입니다." + id);
        }

        MovieReview movieReview = optionalMovieReview.get();
        Member member = movieReview.getMember();
        if (!memberId.equals(member.getId())) {
            throw new HttpException(HttpStatus.BAD_REQUEST, HttpExceptionCode.NOT_OWNED, "작성자가 아닙니다.");
        }

        movieReviewRepository.delete(movieReview);

    }

    public Optional<MovieReview> findById(Long id) {
        return movieReviewRepository.findByIdJoinedMember(id);
    }

    @Transactional
    public MovieReviewLike addLike(Long id, Long memberId) {

        boolean exits = movieReviewLikeService.exitsByMovieReviewIdAndMemberId(id, memberId);
        if (exits) {
            throw new HttpException(HttpStatus.BAD_REQUEST, HttpExceptionCode.ALREADY_EXIST, "이미 좋아요한 movieReview 입니다. movieReviewId -> " + id);
        }

        MovieReview movieReview = findById(id)
                .orElseThrow(() -> new HttpException(HttpStatus.BAD_REQUEST, HttpExceptionCode.NOT_FOUND, "존재하지 않는 movieReview 입니다. " + id));

        Member member = memberService.findById(memberId)
                .orElseThrow(() -> new HttpException(HttpStatus.BAD_REQUEST, HttpExceptionCode.NOT_FOUND, "존재하지 않는 member 입니다. " + memberId));

        MovieReviewLike movieReviewLike = new MovieReviewLike();
        MovieReviewLikeHistory movieReviewLikeHistory = new MovieReviewLikeHistory(ReviewLikeStatus.LIKE);

        movieReview.addLike(movieReviewLike);
        member.addReviewLike(movieReviewLike);

        movieReview.addLikeHistory(movieReviewLikeHistory);
        member.addReviewLikeHistory(movieReviewLikeHistory);

        return movieReviewLike;
    }

    @Transactional
    public MovieReviewReply addReply(Long id, Long memberId, MovieReviewReplySaveReqDto dto) {

        MovieReview movieReview = findById(id)
                .orElseThrow(() -> new HttpException(HttpStatus.BAD_REQUEST, HttpExceptionCode.NOT_FOUND, "존재하지 않는 movieReview 입니다. " + id));

        Member member = memberService.findById(memberId)
                .orElseThrow(() -> new HttpException(HttpStatus.BAD_REQUEST, HttpExceptionCode.NOT_FOUND, "존재하지 않는 member 입니다. " + memberId));

        String content = dto.getContent();
        MovieReviewReply movieReviewReply = MovieReviewReply.create(content);

        movieReview.addReply(movieReviewReply);
        member.addReviewReply(movieReviewReply);

        return movieReviewReply;

    }

    @Transactional
    public void setStatistics(Long id) {

        MovieReview movieReview = findById(id)
                .orElseThrow(() -> new HttpException(HttpStatus.BAD_REQUEST, HttpExceptionCode.NOT_FOUND, "존재하지 않는 movieReview 입니다. " + id));



//        movieReviewStatisticsService.save()
//        movieReview.setMovieReviewStatistics();
    }
}
