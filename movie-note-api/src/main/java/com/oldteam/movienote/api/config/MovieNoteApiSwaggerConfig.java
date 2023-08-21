package com.oldteam.movienote.api.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Pageable;

@Configuration
@OpenAPIDefinition(servers = {@Server(url = "/", description = "Default Server Url")})
public class MovieNoteApiSwaggerConfig {

    @Bean
    public GroupedOpenApi movieNotApi() {
        String[] paths = {"/api/v1/**"};

        return GroupedOpenApi.builder()
                .group("movieNotApi")
                .pathsToMatch(paths)
                .build();
    }

}
