package com.oldteam.movienote.api.domain.uploadfile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/api/v1/upload-files")
@RequiredArgsConstructor
public class UploadFileController {

    @PostMapping
    public ResponseEntity<?> save(@ModelAttribute MultipartFile file) {
        return null;
    }

}
