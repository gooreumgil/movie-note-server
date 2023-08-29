package com.oldteam.movienote.api.domain.movie.service;

import com.oldteam.movienote.common.exception.HttpException;
import com.oldteam.movienote.common.exception.HttpExceptionCode;
import com.oldteam.movienote.core.domain.member.Member;
import com.oldteam.movienote.core.domain.movie.MovieReviewReply;
import com.oldteam.movienote.core.domain.movie.repository.MovieReviewReplyRepository;
import com.oldteam.movienote.core.domain.movie.repository.MovieReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MovieReviewReplyService {

    private final MovieReviewReplyRepository movieReviewReplyRepository;

    public Optional<MovieReviewReply> findByIdAndMovieReviewId(Long id, Long movieReviewId) {
        return movieReviewReplyRepository.findByIdAndMovieReviewIdJoinedMember(id, movieReviewId);
    }

    public Page<MovieReviewReply> findAllByMovieReviewId(Long movieReviewId, Pageable pageable) {
        return movieReviewReplyRepository.findAllByMovieReviewIdJoinedMember(movieReviewId, pageable);
    }

    @Transactional
    public void delete(Long movieReviewId, Long id, Long memberId) {

        MovieReviewReply movieReviewReply = findByIdAndMovieReviewId(id, movieReviewId)
                .orElseThrow(() -> new HttpException(
                        HttpStatus.BAD_REQUEST,
                        HttpExceptionCode.NOT_FOUND,
                        "존재하지 않는 movieReviewReply 입니다. id -> " + id + ", movieReviewId -> " + movieReviewId
                ));

        Member member = movieReviewReply.getMember();
        if (!member.getId().equals(memberId)) {
            throw new HttpException(HttpStatus.BAD_REQUEST, HttpExceptionCode.NOT_OWNED, "movieReplyReply는 작성자만 삭제할 수 있습니다. id -> " + movieReviewId);
        }

        movieReviewReplyRepository.delete(movieReviewReply);

    }
}
