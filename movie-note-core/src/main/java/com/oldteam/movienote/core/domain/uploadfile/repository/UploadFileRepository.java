package com.oldteam.movienote.core.domain.uploadfile.repository;

import com.oldteam.movienote.core.domain.uploadfile.UploadFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UploadFileRepository extends JpaRepository<UploadFile, Long> {
}
