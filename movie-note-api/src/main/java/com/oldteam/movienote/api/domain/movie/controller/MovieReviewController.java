package com.oldteam.movienote.api.domain.movie.controller;

import com.oldteam.movienote.api.domain.member.dto.MemberResDto;
import com.oldteam.movienote.api.domain.member.mapper.MemberTokenMapper;
import com.oldteam.movienote.api.domain.movie.dto.*;
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

    @GetMapping("/{id}")
    public ResponseEntity<MovieReviewResDto> findOne(@PathVariable Long id) {
        MovieReview movieReview = movieReviewService.findById(id)
                .orElseThrow(() -> new HttpException(
                        HttpStatus.BAD_REQUEST,
                        HttpExceptionCode.NOT_FOUND,
                        "존재하지 않는 movieReview 입니다. movieReviewId -> " + id));
        MovieReviewResDto movieReviewResDto = new MovieReviewResDto(movieReview);
        Member member = movieReview.getMember();
        if (member != null) {
            MemberResDto memberResDto = new MemberResDto(member);
            UploadFile uploadFile = member.getUploadFile();
            if (uploadFile != null) {
                memberResDto.setProfileImageUrl(uploadFile.getUrl());
            }
            movieReviewResDto.setMemberResDto(memberResDto);
        }
        return ResponseEntity.ok(movieReviewResDto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<MovieReviewResDto> update(@PathVariable Long id, @RequestBody MovieReviewUpdateReqDto dto) {

        MovieReview movieReview = movieReviewService.update(id, dto);
        MovieReviewResDto movieReviewResDto = new MovieReviewResDto(movieReview);

        Member member = movieReview.getMember();
        MemberResDto memberResDto = new MemberResDto(member);

        UploadFile profileImage = member.getUploadFile();
        if (profileImage != null) {
            memberResDto.setProfileImageUrl(profileImage.getUrl());
        }

        movieReviewResDto.setMemberResDto(memberResDto);

        List<MovieReviewUploadFileRelation> fileList = movieReview.getFileList();

        for (MovieReviewUploadFileRelation movieReviewUploadFileRelation : fileList) {
            UploadFile uploadFile = movieReviewUploadFileRelation.getUploadFile();
            if (uploadFile != null) {
                UploadFileResDto uploadFileResDto = new UploadFileResDto(uploadFile);
                movieReviewResDto.addUploadFile(uploadFileResDto);
            }
        }

        return ResponseEntity.ok(movieReviewResDto);
    }

    @GetMapping
    public ResponseEntity<PageDto.ListResponse<MovieReviewResDto>> findAll(
            @ParameterObject @PageableDefault(sort = "createdDateTime", direction = Sort.Direction.DESC) Pageable pageable) {

        Page<MovieReview> movieReviewPage = movieReviewService.findAll(pageable);
        List<MovieReviewResDto> movieReviewResDtos = movieReviewPage.map(movieReview -> {

            MovieReviewResDto movieReviewResDto = new MovieReviewResDto(movieReview);

            Member member = movieReview.getMember();
            MemberResDto memberResDto = new MemberResDto(member);

            UploadFile profileImage = member.getUploadFile();
            if (profileImage != null) {
                memberResDto.setProfileImageUrl(profileImage.getUrl());
            }

            movieReviewResDto.setMemberResDto(memberResDto);

            List<MovieReviewUploadFileRelation> fileList = movieReview.getFileList();

            for (MovieReviewUploadFileRelation movieReviewUploadFileRelation : fileList) {
                UploadFile uploadFile = movieReviewUploadFileRelation.getUploadFile();
                if (uploadFile != null) {
                    UploadFileResDto uploadFileResDto = new UploadFileResDto(uploadFile);
                    movieReviewResDto.addUploadFile(uploadFileResDto);
                }
            }

            return movieReviewResDto;

        }).toList();

        return ResponseEntity.ok(new PageDto.ListResponse<>(movieReviewPage, movieReviewResDtos));

    }

    @PostMapping
    public ResponseEntity<MovieReviewResDto> save(@RequestBody MovieReviewSaveReqDto dto, @AuthenticationPrincipal MemberTokenMapper tokenMapper) {

        MovieReview movieReview = movieReviewService.save(dto, tokenMapper.getId());

        MovieReviewResDto movieReviewResDto = new MovieReviewResDto(movieReview);
        List<MovieReviewUploadFileRelation> fileList = movieReview.getFileList();

        for (MovieReviewUploadFileRelation movieReviewUploadFileRelation : fileList) {

            UploadFile uploadFile = movieReviewUploadFileRelation.getUploadFile();
            if (uploadFile != null) {
                UploadFileResDto uploadFileResDto = new UploadFileResDto(uploadFile);
                movieReviewResDto.addUploadFile(uploadFileResDto);
            }

        }

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

    @PostMapping("/{id}/replies")
    public ResponseEntity<MovieReviewReplyResDto> addReply(@PathVariable Long id, @RequestBody MovieReviewReplySaveReqDto dto, @AuthenticationPrincipal MemberTokenMapper tokenMapper) {
        MovieReviewReply movieReviewReply = movieReviewService.addReply(id, tokenMapper.getId(), dto);
        return ResponseEntity.ok(new MovieReviewReplyResDto(movieReviewReply));
    }


}
