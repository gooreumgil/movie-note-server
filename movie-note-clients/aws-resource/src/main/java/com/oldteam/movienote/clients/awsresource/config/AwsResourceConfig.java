package com.oldteam.movienote.clients.awsresource.config;

import com.amazonaws.services.s3.AmazonS3;
import com.oldteam.movienote.clients.awsresource.service.AwsS3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class AwsResourceConfig {

    private final AmazonS3 amazonS3;

    @Bean
    public AwsS3Service awsS3Service() {
        return new AwsS3Service(amazonS3);
    }

}
