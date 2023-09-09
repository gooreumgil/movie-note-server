package com.oldteam.movienote.api.domain.uploadfile;

import com.oldteam.movienote.clients.awsresource.dto.AwsFileInfo;
import com.oldteam.movienote.clients.awsresource.service.AwsS3Service;
import com.oldteam.movienote.core.domain.uploadfile.UploadFile;
import com.oldteam.movienote.core.domain.uploadfile.UploadFileType;
import com.oldteam.movienote.core.domain.uploadfile.repository.UploadFileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UploadFileService {

    private final UploadFileRepository uploadFileRepository;
    private final AwsS3Service awsS3Service;

    public Optional<UploadFile> findById(Long id) {
        return uploadFileRepository.findById(id);
    }

    public List<UploadFile> findByIds(List<Long> ids) {
        return uploadFileRepository.findByIdIn(ids);
    }

    @Transactional
    public UploadFile save(MultipartFile file, String directoryName, UploadFileType uploadFileType) {

        try {
            AwsFileInfo awsFileInfo = awsS3Service.saveFile(file, directoryName);
            UploadFile uploadFile = UploadFile.create(awsFileInfo.getS3Key(), awsFileInfo.getFileUrl(), uploadFileType);
            return uploadFileRepository.save(uploadFile);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Transactional
    public void deleteByIdIn(List<Long> uploadFileIds) {

        List<UploadFile> uploadFileList = findByIds(uploadFileIds);

        for (UploadFile uploadFile : uploadFileList) {
            awsS3Service.deleteFile(uploadFile.getS3Key());
        }

        uploadFileRepository.deleteByIdIn(uploadFileIds);
    }
}
