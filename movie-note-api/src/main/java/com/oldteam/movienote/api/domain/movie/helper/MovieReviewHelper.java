package com.oldteam.movienote.api.domain.movie.helper;

import com.oldteam.movienote.api.domain.member.dto.MemberResDto;
import com.oldteam.movienote.api.domain.movie.dto.MovieReviewResDto;
import com.oldteam.movienote.api.domain.movie.dto.MovieReviewStatisticsResDto;
import com.oldteam.movienote.api.domain.movie.service.MovieReviewLikeService;
import com.oldteam.movienote.api.domain.movie.service.MovieReviewService;
import com.oldteam.movienote.api.domain.uploadfile.dto.UploadFileResDto;
import com.oldteam.movienote.core.domain.member.Member;
import com.oldteam.movienote.core.domain.movie.MovieReview;
import com.oldteam.movienote.core.domain.movie.MovieReviewStatistics;
import com.oldteam.movienote.core.domain.movie.MovieReviewUploadFileRelation;
import com.oldteam.movienote.core.domain.uploadfile.UploadFile;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MovieReviewHelper {

    private final MovieReviewLikeService movieReviewLikeService;
    private final MovieReviewService movieReviewService;

    public MovieReviewResDto convertMovieReviewResDto(MovieReview movieReview) {

        MovieReviewResDto movieReviewResDto = new MovieReviewResDto(movieReview);

        Member member = movieReview.getMember();
        MemberResDto memberResDto = new MemberResDto(member);

        UploadFile profileImage = member.getUploadFile();
        if (profileImage != null) {
            memberResDto.setProfileImageUrl(profileImage.getUrl());
        }

        movieReviewResDto.setMember(memberResDto);

        List<MovieReviewUploadFileRelation> fileList = movieReview.getFileList();

        for (MovieReviewUploadFileRelation movieReviewUploadFileRelation : fileList) {
            UploadFile uploadFile = movieReviewUploadFileRelation.getUploadFile();
            if (uploadFile != null) {
                UploadFileResDto uploadFileResDto = new UploadFileResDto(uploadFile);
                movieReviewResDto.addUploadFile(uploadFileResDto);
            }
        }

        MovieReviewStatistics statistics = movieReview.getStatistics();
        if (statistics == null) {
            movieReviewService.setStatistics(movieReview.getId());
        } else {
            movieReviewResDto.setStatistics(new MovieReviewStatisticsResDto(statistics));
        }

        return movieReviewResDto;

    }

    public void setMovieReviewLiked(MovieReviewResDto movieReviewResDto, Long sessionMemberId) {
        Long movieReviewLikeId = movieReviewLikeService.findIdByMovieReviewIdAndMemberId(movieReviewResDto.getId(), sessionMemberId);
        if (movieReviewLikeId != null) {
            movieReviewResDto.setIsLike(true);
            movieReviewResDto.setLikeId(movieReviewLikeId);
        } else {
            movieReviewResDto.setIsLike(false);
        }
    }

    public void setOwn(MovieReviewResDto movieReviewResDto, Long sessionMemberId) {
        MemberResDto member = movieReviewResDto.getMember();
        if (member == null) {
            movieReviewResDto.setIsOwn(false);
        } else {
            movieReviewResDto.setIsOwn(member.getId().equals(sessionMemberId));
        }
    }

    public Page<MovieReviewResDto> convertPageToMovieReviewResDto(Page<MovieReview> movieReviewPage) {
        return movieReviewPage.map(this::convertMovieReviewResDto);
    }

}
