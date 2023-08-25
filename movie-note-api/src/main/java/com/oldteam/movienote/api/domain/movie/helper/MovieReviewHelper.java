package com.oldteam.movienote.api.domain.movie.helper;

import com.oldteam.movienote.api.domain.member.dto.MemberResDto;
import com.oldteam.movienote.api.domain.movie.dto.MovieReviewResDto;
import com.oldteam.movienote.api.domain.uploadfile.dto.UploadFileResDto;
import com.oldteam.movienote.core.domain.member.Member;
import com.oldteam.movienote.core.domain.movie.MovieReview;
import com.oldteam.movienote.core.domain.movie.MovieReviewUploadFileRelation;
import com.oldteam.movienote.core.domain.uploadfile.UploadFile;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MovieReviewHelper {

    public MovieReviewResDto convertMovieReviewResDto(MovieReview movieReview) {

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

    }

    public Page<MovieReviewResDto> convertPageToMovieReviewResDto(Page<MovieReview> movieReviewPage) {
        return movieReviewPage.map(movieReview -> {

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

        });
    }

}
