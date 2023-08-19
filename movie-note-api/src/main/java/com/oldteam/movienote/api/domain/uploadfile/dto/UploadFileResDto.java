package com.oldteam.movienote.api.domain.uploadfile.dto;

import com.oldteam.movienote.core.domain.uploadfile.UploadFileType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UploadFileResDto {

    private Long id;
    private String s3Key;
    private String url;
    private String type;

}
