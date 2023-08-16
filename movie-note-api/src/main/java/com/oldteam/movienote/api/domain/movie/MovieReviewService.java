package com.oldteam.movienote.api.domain.movie;

import com.oldteam.movienote.api.domain.uploadfile.UploadFileService;
import com.oldteam.movienote.core.domain.movie.repository.MovieReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MovieReviewService {

    private final MovieReviewRepository movieReviewRepository;
    private final MovieService movieService;
    private final UploadFileService uploadFileService;

    @Transactional
    public void save() {

    }


}
