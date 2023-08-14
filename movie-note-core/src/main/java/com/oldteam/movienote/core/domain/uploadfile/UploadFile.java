package com.oldteam.movienote.core.domain.uploadfile;

import com.oldteam.movienote.core.audit.AuditingDomain;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UploadFile extends AuditingDomain {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String s3Key;
    private String url;

    @Enumerated(EnumType.STRING)
    private UploadFileType type;


}
