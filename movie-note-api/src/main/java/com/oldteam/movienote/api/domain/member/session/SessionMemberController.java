package com.oldteam.movienote.api.domain.member.session;

import com.oldteam.movienote.api.domain.member.MemberService;
import com.oldteam.movienote.api.domain.member.mapper.MemberTokenMapper;
import com.oldteam.movienote.api.domain.movie.dto.MovieReviewResDto;
import com.oldteam.movienote.api.domain.movie.dto.MovieReviewSaveReqDto;
import com.oldteam.movienote.api.domain.movie.dto.MovieReviewUpdateReqDto;
import com.oldteam.movienote.api.domain.movie.helper.MovieReviewHelper;
import com.oldteam.movienote.api.domain.movie.service.MovieReviewService;
import com.oldteam.movienote.core.common.dto.PageDto;
import com.oldteam.movienote.core.domain.movie.MovieReview;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/session-member")
@RequiredArgsConstructor
public class SessionMemberController {

    private final MovieReviewService movieReviewService;
    private final MovieReviewHelper movieReviewHelper;

    @GetMapping("/movie-reviews")
    public ResponseEntity<PageDto.ListResponse<MovieReviewResDto>> findAllMovieReviews(
            @ParameterObject @PageableDefault(sort = "createdDateTime", direction = Sort.Direction.DESC) Pageable pageable,
            @AuthenticationPrincipal MemberTokenMapper tokenMapper) {

        Page<MovieReview> movieReviewPage = movieReviewService.findAllByMemberId(tokenMapper.getId(), pageable);
        List<MovieReviewResDto> movieReviewResDtoList = movieReviewHelper.convertPageToMovieReviewResDto(movieReviewPage).toList();
        for (MovieReviewResDto movieReviewResDto : movieReviewResDtoList) {
            movieReviewHelper.setMovieReviewLiked(movieReviewResDto, tokenMapper.getId());
        }

        return ResponseEntity.ok(new PageDto.ListResponse<>(movieReviewPage, movieReviewResDtoList));
    }

    @PostMapping("/movie-reviews")
    public ResponseEntity<MovieReviewResDto> save(@RequestBody MovieReviewSaveReqDto dto, @AuthenticationPrincipal MemberTokenMapper tokenMapper) {

        MovieReview movieReview = movieReviewService.save(dto, tokenMapper.getId());
        MovieReviewResDto movieReviewResDto = movieReviewHelper.convertMovieReviewResDto(movieReview);

        return ResponseEntity.ok(movieReviewResDto);
    }

    @PatchMapping("/movie-reviews/{movieReviewId}")
    public ResponseEntity<MovieReviewResDto> update(@PathVariable Long movieReviewId, @RequestBody MovieReviewUpdateReqDto dto, @AuthenticationPrincipal MemberTokenMapper tokenMapper) {

        MovieReview movieReview = movieReviewService.update(movieReviewId, dto);
        MovieReviewResDto movieReviewResDto = movieReviewHelper.convertMovieReviewResDto(movieReview);
        movieReviewHelper.setMovieReviewLiked(movieReviewResDto, tokenMapper.getId());

        return ResponseEntity.ok(movieReviewResDto);
    }

    @DeleteMapping("/movie-reviews/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, @AuthenticationPrincipal MemberTokenMapper tokenMapper) {
        movieReviewService.deleteById(id, tokenMapper.getId());
        return ResponseEntity.ok().build();

    }

}
