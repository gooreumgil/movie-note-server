package com.oldteam.movienote.api.domain.movie.service;

import com.oldteam.movienote.core.domain.movie.MovieReviewLike;
import com.oldteam.movienote.core.domain.movie.repository.MovieReviewLikeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MovieReviewLikeService {

    private final MovieReviewLikeRepository movieReviewLikeRepository;

    public Long findIdByMovieReviewIdAndMemberId(Long movieReviewId, Long memberId) {
        return movieReviewLikeRepository.findIdByMovieReviewIdAndMemberId(movieReviewId, memberId);
    }

    public boolean exitsByMovieReviewIdAndMemberId(Long movieReviewId, Long memberId) {
        return movieReviewLikeRepository.existsByMovieReviewIdAndMemberId(movieReviewId, memberId);
    }

    @Transactional
    public void delete(Long id, Long movieReviewId, Long memberId) {
        movieReviewLikeRepository.deleteByIdAndMovieReviewIdAndMemberId(id, movieReviewId, memberId);
    }

    public int countByMovieReviewId(Long movieReviewId) {
        return movieReviewLikeRepository.countAllByMovieReviewId(movieReviewId);
    }

}
