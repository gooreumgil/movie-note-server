package com.oldteam.movienote.api.domain.movie.helper;

import com.oldteam.movienote.api.domain.movie.dto.MovieReviewReplyResDto;
import com.oldteam.movienote.api.domain.movie.dto.MovieReviewResDto;
import com.oldteam.movienote.core.domain.member.Member;
import com.oldteam.movienote.core.domain.movie.MovieReviewReply;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MovieReviewReplyHelper {

    public MovieReviewReplyResDto convertToMovieReviewReplyResDto(MovieReviewReply movieReviewReply) {
        MovieReviewReplyResDto movieReviewReplyResDto = new MovieReviewReplyResDto(movieReviewReply);
        Member member = movieReviewReply.getMember();
        if (member != null) {
            movieReviewReplyResDto.setMember(member);
        }
        return movieReviewReplyResDto;
    }

    public Page<MovieReviewReplyResDto> convertPageToMovieReviewReplyResDto(Page<MovieReviewReply> movieReviewReplyPage) {
        return movieReviewReplyPage.map(this::convertToMovieReviewReplyResDto);
    }

}
