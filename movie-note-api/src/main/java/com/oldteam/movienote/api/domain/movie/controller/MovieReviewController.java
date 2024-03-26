package com.oldteam.movienote.api.domain.movie.controller;

import com.oldteam.movienote.api.domain.member.mapper.MemberTokenMapper;
import com.oldteam.movienote.api.domain.movie.condition.MovieReviewSearchCondition;
import com.oldteam.movienote.api.domain.movie.dto.*;
import com.oldteam.movienote.api.domain.movie.helper.MovieReviewHelper;
import com.oldteam.movienote.api.domain.movie.helper.MovieReviewReplyHelper;
import com.oldteam.movienote.api.domain.movie.service.MovieReviewLikeService;
import com.oldteam.movienote.api.domain.movie.service.MovieReviewReplyService;
import com.oldteam.movienote.api.domain.movie.service.MovieReviewService;
import com.oldteam.movienote.common.exception.HttpException;
import com.oldteam.movienote.common.exception.HttpExceptionCode;
import com.oldteam.movienote.core.common.dto.PageDto;
import com.oldteam.movienote.core.domain.movie.MovieReview;
import com.oldteam.movienote.core.domain.movie.MovieReviewLike;
import com.oldteam.movienote.core.domain.movie.MovieReviewReply;
import com.oldteam.movienote.core.domain.movie.MovieReviewStatistics;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/v1/movie-reviews")
@RequiredArgsConstructor
public class MovieReviewController {

    private final MovieReviewService movieReviewService;
    private final MovieReviewReplyService movieReviewReplyService;
    private final MovieReviewLikeService movieReviewLikeService;
    private final MovieReviewHelper movieReviewHelper;
    private final MovieReviewReplyHelper movieReviewReplyHelper;

    @GetMapping("/{id}")
    public ResponseEntity<MovieReviewResDto> findOne(@PathVariable Long id, @AuthenticationPrincipal MemberTokenMapper tokenMapper) {
        MovieReview movieReview = movieReviewService.findById(id)
                .orElseThrow(() -> new HttpException(
                        HttpStatus.BAD_REQUEST,
                        HttpExceptionCode.NOT_FOUND,
                        "존재하지 않는 movieReview 입니다. movieReviewId -> " + id));

        MovieReviewResDto movieReviewResDto = movieReviewHelper.convertMovieReviewResDto(movieReview);
        if (tokenMapper != null) {
            movieReviewHelper.setMovieReviewLiked(movieReviewResDto, tokenMapper.getId());
            movieReviewHelper.setOwn(movieReviewResDto, tokenMapper.getId());
        }

        return ResponseEntity.ok(movieReviewResDto);

    }

    @GetMapping
    public ResponseEntity<PageDto.ListResponse<MovieReviewResDto>> findAll(
            @RequestParam(required = false) String query,
            @ParameterObject @PageableDefault(sort = "createdDateTime", direction = Sort.Direction.DESC) Pageable pageable,
            @AuthenticationPrincipal MemberTokenMapper tokenMapper) {

        MovieReviewSearchCondition movieReviewSearchCondition = new MovieReviewSearchCondition();
        movieReviewSearchCondition.setQuery(query);

        Page<MovieReview> movieReviewPage = movieReviewService.findAllByCondition(movieReviewSearchCondition, pageable);
        List<MovieReviewResDto> movieReviewResDtos = movieReviewHelper.convertPageToMovieReviewResDto(movieReviewPage).toList();
        if (tokenMapper != null) {
            for (MovieReviewResDto movieReviewResDto : movieReviewResDtos) {
                movieReviewHelper.setMovieReviewLiked(movieReviewResDto, tokenMapper.getId());
                movieReviewHelper.setOwn(movieReviewResDto, tokenMapper.getId());
            }
        }

        return ResponseEntity.ok(new PageDto.ListResponse<>(movieReviewPage, movieReviewResDtos));

    }

    @GetMapping("/{id}/statistics")
    public ResponseEntity<MovieReviewStatisticsResDto> findStatistics(@PathVariable Long id) {

        Optional<MovieReviewStatistics> optionalMovieReviewStatistics = movieReviewService.findStatisticsById(id);

        if (optionalMovieReviewStatistics.isPresent()) {
            MovieReviewStatistics movieReviewStatistics = optionalMovieReviewStatistics.get();
            return ResponseEntity.ok(new MovieReviewStatisticsResDto(movieReviewStatistics));
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @PatchMapping("/{id}/statistics/views-total")
    public ResponseEntity<?> plusReplyTotal(@PathVariable Long id) {
        movieReviewService.plusViewsTotal(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/likes")
    public ResponseEntity<MovieReviewLikeResDto> addLike(@PathVariable Long id, @AuthenticationPrincipal MemberTokenMapper tokenMapper) {
        MovieReviewLike movieReviewLike = movieReviewService.addLike(id, tokenMapper.getId());
        return ResponseEntity.ok(new MovieReviewLikeResDto(movieReviewLike));
    }

    @DeleteMapping("/{id}/likes/{likeId}")
    public ResponseEntity<?> deleteLike(@PathVariable Long id, @PathVariable Long likeId, @AuthenticationPrincipal MemberTokenMapper tokenMapper) {
        movieReviewLikeService.delete(likeId, id, tokenMapper.getId());
        movieReviewService.minusLikeTotal(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/replies")
    public ResponseEntity<PageDto.ListResponse<MovieReviewReplyResDto>> findReplies(
            @PathVariable Long id,
            @ParameterObject @PageableDefault(sort = "createdDateTime", direction = Sort.Direction.ASC) Pageable pageable) {

        Page<MovieReviewReply> movieReviewReplyPage = movieReviewReplyService.findAllByMovieReviewId(id, pageable);
        List<MovieReviewReplyResDto> movieReviewReplyResDtoList = movieReviewReplyHelper.convertPageToMovieReviewReplyResDto(movieReviewReplyPage).toList();

        return ResponseEntity.ok(new PageDto.ListResponse<>(movieReviewReplyPage, movieReviewReplyResDtoList));

    }

    @PostMapping("/{id}/replies")
    public ResponseEntity<MovieReviewReplyResDto> addReply(@PathVariable Long id, @RequestBody MovieReviewReplySaveReqDto dto, @AuthenticationPrincipal MemberTokenMapper tokenMapper) {
        MovieReviewReply movieReviewReply = movieReviewService.addReply(id, tokenMapper.getId(), dto);
        MovieReviewReplyResDto movieReviewReplyResDto = movieReviewReplyHelper.convertToMovieReviewReplyResDto(movieReviewReply);
        return ResponseEntity.ok(movieReviewReplyResDto);
    }

    @PatchMapping("/{id}/replies/{replyId}")
    public ResponseEntity<Void> updateReply(
            @PathVariable Long id, @PathVariable Long replyId,
            @RequestBody MovieReviewReplyUpdateReqDto dto,
            @AuthenticationPrincipal MemberTokenMapper tokenMapper) {

        movieReviewReplyService.update(id, replyId, dto, tokenMapper.getId());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}/replies/{replyId}")
    public ResponseEntity<?> saveReply(@PathVariable Long id, @PathVariable Long replyId, @AuthenticationPrincipal MemberTokenMapper tokenMapper) {
        movieReviewReplyService.delete(id, replyId, tokenMapper.getId());
        movieReviewService.minusReplyTotal(id);
        return ResponseEntity.ok().build();
    }


}
