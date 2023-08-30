package com.oldteam.movienote.api.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.Parameter;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Pageable;
import org.springframework.web.method.HandlerMethod;

@Configuration
@OpenAPIDefinition(servers = {@Server(url = "/", description = "Default Server Url")})
public class MovieNoteApiSwaggerConfig {

    @Bean
    public GroupedOpenApi movieNotApi() {
        String[] paths = {"/api/v1/**"};

        return GroupedOpenApi.builder()
                .group("movieNoteApi")
                .pathsToMatch(paths)
                .addOperationCustomizer(operationCustomizer())
                .build();
    }

    @Bean
    public OperationCustomizer operationCustomizer() {
        return (Operation operation, HandlerMethod handlerMethod) -> {
            Parameter param = new Parameter()
                    .in(ParameterIn.HEADER.toString())  // 전역 헤더 설정
                    .schema(new StringSchema()._default("Bearer ")) // default값 설정
                    .name("Authorization")
                    .description("accessToken")
                    .required(null);
            operation.addParametersItem(param);
            return operation;
        };
    }

}
