package com.oldteam.movienote.api.domain.movie;

import com.oldteam.movienote.api.domain.member.mapper.MemberTokenMapper;
import com.oldteam.movienote.api.domain.movie.dto.MovieReviewResDto;
import com.oldteam.movienote.api.domain.movie.dto.MovieReviewSaveReqDto;
import com.oldteam.movienote.api.domain.uploadfile.dto.UploadFileResDto;
import com.oldteam.movienote.core.common.dto.PageDto;
import com.oldteam.movienote.core.domain.movie.MovieReview;
import com.oldteam.movienote.core.domain.movie.MovieReviewUploadFileRelation;
import com.oldteam.movienote.core.domain.uploadfile.UploadFile;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@RequestMapping("/api/v1/movie-reviews")
@RequiredArgsConstructor
public class MovieReviewController {

    private final MovieReviewService movieReviewService;

    @GetMapping
    public ResponseEntity<PageDto.ListResponse<MovieReviewResDto>> findAll(
            @PageableDefault(sort = "createdDateTime", direction = Sort.Direction.DESC) Pageable pageable) {

        Page<MovieReview> movieReviewPage = movieReviewService.findAll(pageable);
        List<MovieReviewResDto> movieReviewResDtos = movieReviewPage.map(movieReview -> {

            MovieReviewResDto movieReviewResDto = new MovieReviewResDto();
            movieReviewResDto.setId(movieReview.getId());
            movieReviewResDto.setTitle(movieReviewResDto.getTitle());
            movieReviewResDto.setContent(movieReviewResDto.getContent());

            List<MovieReviewUploadFileRelation> fileList = movieReview.getFileList();

            for (MovieReviewUploadFileRelation movieReviewUploadFileRelation : fileList) {
                UploadFile uploadFile = movieReviewUploadFileRelation.getUploadFile();
                if (uploadFile != null) {
                    UploadFileResDto uploadFileResDto = new UploadFileResDto();
                    uploadFileResDto.setId(uploadFile.getId());
                    uploadFileResDto.setUrl(uploadFile.getUrl());
                    uploadFileResDto.setS3Key(uploadFile.getS3Key());
                    uploadFileResDto.setType(uploadFile.getType().name());
                    movieReviewResDto.addUploadFile(uploadFileResDto);
                }
            }

            return movieReviewResDto;

        }).toList();

        return ResponseEntity.ok(new PageDto.ListResponse<>(movieReviewPage, movieReviewResDtos));

    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody MovieReviewSaveReqDto dto, @AuthenticationPrincipal MemberTokenMapper tokenMapper) {
        movieReviewService.save(dto, tokenMapper.getId());
        return ResponseEntity.ok().build();
    }


}
