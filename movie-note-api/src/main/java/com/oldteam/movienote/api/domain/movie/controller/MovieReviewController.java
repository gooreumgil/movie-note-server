package com.oldteam.movienote.api.domain.movie.controller;

import com.oldteam.movienote.api.domain.member.dto.MemberResDto;
import com.oldteam.movienote.api.domain.member.mapper.MemberTokenMapper;
import com.oldteam.movienote.api.domain.movie.dto.*;
import com.oldteam.movienote.api.domain.movie.helper.MovieReviewHelper;
import com.oldteam.movienote.api.domain.movie.service.MovieReviewReplyService;
import com.oldteam.movienote.api.domain.movie.service.MovieReviewService;
import com.oldteam.movienote.api.domain.uploadfile.dto.UploadFileResDto;
import com.oldteam.movienote.common.exception.HttpException;
import com.oldteam.movienote.common.exception.HttpExceptionCode;
import com.oldteam.movienote.core.common.dto.PageDto;
import com.oldteam.movienote.core.domain.member.Member;
import com.oldteam.movienote.core.domain.movie.MovieReview;
import com.oldteam.movienote.core.domain.movie.MovieReviewLike;
import com.oldteam.movienote.core.domain.movie.MovieReviewReply;
import com.oldteam.movienote.core.domain.movie.MovieReviewUploadFileRelation;
import com.oldteam.movienote.core.domain.uploadfile.UploadFile;
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

@Slf4j
@RestController
@RequestMapping("/api/v1/movie-reviews")
@RequiredArgsConstructor
public class MovieReviewController {

    private final MovieReviewService movieReviewService;
    private final MovieReviewReplyService movieReviewReplyService;
    private final MovieReviewHelper movieReviewHelper;

    @GetMapping("/{id}")
    public ResponseEntity<MovieReviewResDto> findOne(@PathVariable Long id) {
        MovieReview movieReview = movieReviewService.findById(id)
                .orElseThrow(() -> new HttpException(
                        HttpStatus.BAD_REQUEST,
                        HttpExceptionCode.NOT_FOUND,
                        "존재하지 않는 movieReview 입니다. movieReviewId -> " + id));

        MovieReviewResDto movieReviewResDto = movieReviewHelper.convertMovieReviewResDto(movieReview);
        return ResponseEntity.ok(movieReviewResDto);

    }

    @PatchMapping("/{id}")
    public ResponseEntity<MovieReviewResDto> update(@PathVariable Long id, @RequestBody MovieReviewUpdateReqDto dto) {

        MovieReview movieReview = movieReviewService.update(id, dto);
        MovieReviewResDto movieReviewResDto = movieReviewHelper.convertMovieReviewResDto(movieReview);

        return ResponseEntity.ok(movieReviewResDto);
    }

    @GetMapping
    public ResponseEntity<PageDto.ListResponse<MovieReviewResDto>> findAll(
            @ParameterObject @PageableDefault(sort = "createdDateTime", direction = Sort.Direction.DESC) Pageable pageable) {

        Page<MovieReview> movieReviewPage = movieReviewService.findAll(pageable);
        List<MovieReviewResDto> movieReviewResDtos = movieReviewHelper.convertPageToMovieReviewResDto(movieReviewPage).toList();

        return ResponseEntity.ok(new PageDto.ListResponse<>(movieReviewPage, movieReviewResDtos));

    }

    @PostMapping
    public ResponseEntity<MovieReviewResDto> save(@RequestBody MovieReviewSaveReqDto dto, @AuthenticationPrincipal MemberTokenMapper tokenMapper) {

        MovieReview movieReview = movieReviewService.save(dto, tokenMapper.getId());
        MovieReviewResDto movieReviewResDto = movieReviewHelper.convertMovieReviewResDto(movieReview);

        return ResponseEntity.ok(movieReviewResDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, @AuthenticationPrincipal MemberTokenMapper tokenMapper) {
        movieReviewService.deleteById(id, tokenMapper.getId());
        return ResponseEntity.ok().build();

    }

    @PostMapping("/{id}/likes")
    public ResponseEntity<MovieReviewLikeResDto> addLike(@PathVariable Long id, @AuthenticationPrincipal MemberTokenMapper tokenMapper) {
        MovieReviewLike movieReviewLike = movieReviewService.addLike(id, tokenMapper.getId());
        return ResponseEntity.ok(new MovieReviewLikeResDto(movieReviewLike));
    }

    @GetMapping("/{id}/replies")
    public ResponseEntity<?> findReplies(
            @PathVariable Long id,
            @ParameterObject @PageableDefault(sort = "createdDateTime", direction = Sort.Direction.DESC) Pageable pageable) {

        Page<MovieReviewReply> movieReviewReplyPage = movieReviewReplyService.findAllByMovieReviewId(id, pageable);
        List<MovieReviewReplyResDto> movieReviewReplyResDtoList = movieReviewReplyPage.map(movieReviewReply -> {
            MovieReviewReplyResDto movieReviewReplyResDto = new MovieReviewReplyResDto(movieReviewReply);
            Member member = movieReviewReply.getMember();
            if (member != null) {
                try {
                    movieReviewReplyResDto.setMember(member);
                } catch (Exception e) {
                    log.error("findReplies -> email decrypt exception, errorMessage={}", e.getMessage());
                }
            }

            return movieReviewReplyResDto;
        }).toList();

        return ResponseEntity.ok(new PageDto.ListResponse<>(movieReviewReplyPage, movieReviewReplyResDtoList));

    }

    @PostMapping("/{id}/replies")
    public ResponseEntity<MovieReviewReplyResDto> addReply(@PathVariable Long id, @RequestBody MovieReviewReplySaveReqDto dto, @AuthenticationPrincipal MemberTokenMapper tokenMapper) {
        MovieReviewReply movieReviewReply = movieReviewService.addReply(id, tokenMapper.getId(), dto);
        return ResponseEntity.ok(new MovieReviewReplyResDto(movieReviewReply));
    }


}
