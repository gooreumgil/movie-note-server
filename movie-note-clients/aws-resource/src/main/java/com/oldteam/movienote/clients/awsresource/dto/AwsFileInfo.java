package com.oldteam.movienote.clients.awsresource.dto;

import lombok.Getter;

@Getter
public class AwsFileInfo {
    private String s3Key;
    private String fileName;
    private String fileUrl;

    public AwsFileInfo(String s3Key, String fileName, String fileUrl) {
        this.s3Key = s3Key;
        this.fileName = fileName;
        this.fileUrl = fileUrl;
    }

    public static AwsFileInfo of(String s3Key, String fileName, String fileUrl) {
        return new AwsFileInfo(s3Key, fileName, fileUrl);
    }
}
