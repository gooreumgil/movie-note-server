package com.oldteam.movienote.api.domain.uploadfile;

import com.oldteam.movienote.api.domain.uploadfile.dto.UploadFileResDto;
import com.oldteam.movienote.core.domain.uploadfile.UploadFile;
import com.oldteam.movienote.core.domain.uploadfile.UploadFileDirectory;
import com.oldteam.movienote.core.domain.uploadfile.UploadFileType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/api/v1/upload-files")
@RequiredArgsConstructor
public class UploadFileController {

    private final UploadFileService uploadFileService;

    @PostMapping
    public ResponseEntity<UploadFileResDto> save(@ModelAttribute MultipartFile file, @RequestParam UploadFileType fileType) {
        UploadFile uploadFile = uploadFileService.save(file, StringUtils.lowerCase(fileType.name()), fileType);
        return ResponseEntity.ok(new UploadFileResDto(uploadFile));
    }

}
