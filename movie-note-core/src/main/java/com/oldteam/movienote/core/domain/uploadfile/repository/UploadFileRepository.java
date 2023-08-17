package com.oldteam.movienote.core.domain.uploadfile.repository;

import com.oldteam.movienote.core.domain.uploadfile.UploadFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UploadFileRepository extends JpaRepository<UploadFile, Long> {
    List<UploadFile> findByIdIn(List<Long> ids);
}
