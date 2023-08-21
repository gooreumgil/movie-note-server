package com.oldteam.movienote.api.config;

import com.oldteam.movienote.api.utils.JwtUtil;
import com.oldteam.movienote.clients.awsresource.config.AwsResourceConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@Import({AwsResourceConfig.class})
public class MovieNoteApiAppConfig {

    @Value("${jwt.secret-key}")
    private String jwtSecret;

    @Bean
    public JwtUtil jwtUtil() {
        return new JwtUtil(jwtSecret);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}
