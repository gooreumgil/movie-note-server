package com.oldteam.movienote.core.domain.uploadfile.repository;

import com.oldteam.movienote.core.domain.uploadfile.UploadFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.List;

public interface UploadFileRepository extends JpaRepository<UploadFile, Long> {
    List<UploadFile> findByIdIn(List<Long> ids);

    @Modifying
    void deleteByIdIn(List<Long> ids);

}
