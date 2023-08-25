package com.oldteam.movienote.api.domain.movie.service;

import com.oldteam.movienote.core.domain.movie.MovieReviewReply;
import com.oldteam.movienote.core.domain.movie.repository.MovieReviewReplyRepository;
import com.oldteam.movienote.core.domain.movie.repository.MovieReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MovieReviewReplyService {

    private final MovieReviewReplyRepository movieReviewReplyRepository;

    public Page<MovieReviewReply> findAllByMovieReviewId(Long movieReviewId, Pageable pageable) {
        return movieReviewReplyRepository.findAllByMovieReviewIdJoinedMember(movieReviewId, pageable);
    }

}
