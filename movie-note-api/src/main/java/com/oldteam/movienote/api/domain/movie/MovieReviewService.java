package com.oldteam.movienote.api.domain.movie;

import com.oldteam.movienote.api.domain.member.MemberService;
import com.oldteam.movienote.api.domain.movie.dto.MovieReviewSaveReqDto;
import com.oldteam.movienote.api.domain.uploadfile.UploadFileService;
import com.oldteam.movienote.core.domain.member.Member;
import com.oldteam.movienote.core.domain.movie.Movie;
import com.oldteam.movienote.core.domain.movie.MovieReview;
import com.oldteam.movienote.core.domain.movie.MovieReviewUploadFileRelation;
import com.oldteam.movienote.core.domain.movie.repository.MovieReviewRepository;
import com.oldteam.movienote.core.domain.uploadfile.UploadFile;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MovieReviewService {

    private final MovieReviewRepository movieReviewRepository;
    private final MovieService movieService;
    private final MemberService memberService;
    private final UploadFileService uploadFileService;

    public Page<MovieReview> findAll(Pageable pageable) {
        return movieReviewRepository.findAllJoinedMember(pageable);
    }

    @Transactional
    public MovieReview save(MovieReviewSaveReqDto dto, Long memberId) {

        Long movieId = dto.getMovieId();
        Member member = memberService.findById(memberId);
        List<Long> uploadFileIds = dto.getUploadFileIds();
        MovieReview movieReview = MovieReview.create(dto.getTitle(), dto.getContent());


        if (movieId != null) {
            movieService.findById(movieId)
                    .ifPresent(movie -> movie.addMovieReview(movieReview));
        }


        if (!uploadFileIds.isEmpty()) {
            List<UploadFile> uploadFileList = uploadFileService.findByIds(uploadFileIds);
            for (UploadFile uploadFile : uploadFileList) {
                MovieReviewUploadFileRelation movieReviewUploadFileRelation = new MovieReviewUploadFileRelation();
                movieReviewUploadFileRelation.setUploadFile(uploadFile);
                movieReview.addFile(movieReviewUploadFileRelation);
            }
        }

        member.addMovieReview(movieReview);

        return movieReview;

    }

    @Transactional
    public void deleteById(Long id, Long memberId) {
        Optional<MovieReview> optionalMovieReview = movieReviewRepository.findById(id);
        if (optionalMovieReview.isEmpty()) {
            throw new RuntimeException("존재하지 않아");
        }

        MovieReview movieReview = optionalMovieReview.get();
        Member member = movieReview.getMember();
        if (!memberId.equals(member.getId())) {
            throw new RuntimeException("작성자만 지울 수 있어");
        }

        movieReviewRepository.delete(movieReview);

    }
}
