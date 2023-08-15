package com.oldteam.movienote.clients.awsresource.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.oldteam.movienote.clients.awsresource.dto.AwsFileInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;

@Slf4j
@RequiredArgsConstructor
public class AwsS3Service {

    private final AmazonS3 s3Client;

    @Value("${spring.profiles.active}")
    private String profiles;

    private final static String BUCKET_NAME = "movie-note";
    private final static String DELIMITER = "/";

    public String getFileKeyFromUrl(String fileUrl) {
        int keyStartIndex = fileUrl.indexOf(DELIMITER + getCurrentDeployProfile());
        if (keyStartIndex == -1) return "";
        return fileUrl.substring(keyStartIndex + 1);
    }

    // Controller 단에서 입력받은 파일을 저장
    public AwsFileInfo saveFile(MultipartFile file, String directoryName) throws Exception {

        if (file == null || file.isEmpty()) {
            throw new RuntimeException("uploadFile 실패");
        }

        String newFileName = getNewFileName(file);
        String s3FileKey = getS3FileKey(directoryName, newFileName);

        ObjectMetadata om = new ObjectMetadata();
        om.setContentLength(file.getSize());
        om.setContentType(file.getContentType());

        s3Client.putObject(BUCKET_NAME, s3FileKey, file.getInputStream(), om);
        String s3Url = s3Client.getUrl(BUCKET_NAME, s3FileKey).toString();
        return AwsFileInfo.of(s3FileKey, newFileName, s3Url);

    }

    public String getS3FileKey(String prefix, String fileName) {
        return getCurrentDeployProfile() + DELIMITER + prefix + DELIMITER + fileName;
    }

    private String getNewFileName(MultipartFile file) {
        String originalName = file.getOriginalFilename();
        return Instant.now().toEpochMilli()
                        + String.format("%06d", (int) Math.floor(Math.random() * 1000000))
                        + "."
                        + originalName.substring(originalName.lastIndexOf(".") + 1);
    }

    private String getCurrentDeployProfile() {
        if (profiles.contains("prod")) {
            return "prod";
        } else {
            return "dev";
        }
    }


}
