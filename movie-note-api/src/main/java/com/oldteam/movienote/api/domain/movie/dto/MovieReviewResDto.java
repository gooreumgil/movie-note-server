package com.oldteam.movienote.api.domain.movie.dto;

import com.oldteam.movienote.api.domain.member.dto.MemberResDto;
import com.oldteam.movienote.api.domain.uploadfile.dto.UploadFileResDto;
import com.oldteam.movienote.core.domain.movie.MovieReview;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MovieReviewResDto {

    private Long id;
    private String title;
    private String content;
    private List<UploadFileResDto> uploadFileList = new ArrayList<>();
    private LocalDateTime createdDateTime;
    private LocalDateTime updatedDateTime;

    private MemberResDto member;
    private Boolean liked;

    public MovieReviewResDto(MovieReview movieReview) {
        this.id = movieReview.getId();
        this.title = movieReview.getTitle();
        this.content = movieReview.getContent();
        this.createdDateTime = movieReview.getCreatedDateTime();
        this.updatedDateTime = movieReview.getUpdatedDateTime();
    }

    public void addUploadFile(UploadFileResDto uploadFileResDto) {
        this.uploadFileList.add(uploadFileResDto);
    }

}
