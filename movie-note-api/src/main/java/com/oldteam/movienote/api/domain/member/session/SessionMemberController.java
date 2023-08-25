package com.oldteam.movienote.api.domain.member.session;

import com.oldteam.movienote.api.domain.member.MemberService;
import com.oldteam.movienote.api.domain.member.mapper.MemberTokenMapper;
import com.oldteam.movienote.api.domain.movie.dto.MovieReviewResDto;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

        return ResponseEntity.ok(new PageDto.ListResponse<>(movieReviewPage, movieReviewResDtoList));
    }

}
