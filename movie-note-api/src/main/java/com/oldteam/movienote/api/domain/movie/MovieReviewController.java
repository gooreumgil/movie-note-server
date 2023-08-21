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
import org.springdoc.core.annotations.ParameterObject;
import org.springdoc.core.converters.models.PageableAsQueryParam;
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
            @ParameterObject @PageableDefault(sort = "createdDateTime", direction = Sort.Direction.DESC) Pageable pageable) {

        Page<MovieReview> movieReviewPage = movieReviewService.findAll(pageable);
        List<MovieReviewResDto> movieReviewResDtos = movieReviewPage.map(movieReview -> {

            MovieReviewResDto movieReviewResDto = new MovieReviewResDto(movieReview);
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


}
