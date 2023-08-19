package com.oldteam.movienote.api.domain.movie.dto;

import com.oldteam.movienote.api.domain.uploadfile.dto.UploadFileResDto;
import com.oldteam.movienote.core.domain.uploadfile.UploadFile;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    public void addUploadFile(UploadFileResDto uploadFileResDto) {
        this.uploadFileList.add(uploadFileResDto);
    }

}
