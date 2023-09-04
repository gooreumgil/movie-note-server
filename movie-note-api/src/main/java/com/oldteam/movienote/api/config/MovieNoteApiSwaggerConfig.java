package com.oldteam.movienote.api.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import org.springdoc.core.customizers.OpenApiBuilderCustomizer;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Pageable;
import org.springframework.web.method.HandlerMethod;

import java.util.List;

@Configuration
@SecurityScheme(
        type = SecuritySchemeType.APIKEY, in = SecuritySchemeIn.HEADER,
        name = "Authorization", description = "Auth Token"
)
@OpenAPIDefinition(servers = {@Server(url = "/", description = "Default Server Url")})
public class MovieNoteApiSwaggerConfig {

    private static final String AUTH_TOKEN_HEADER = "Authorization";

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
            operation.security(List.of(new SecurityRequirement().addList(AUTH_TOKEN_HEADER)));
            return operation;
        };
    }

//    @Bean
//    public OpenAPI customOpenApi() {
//        OpenAPI openAPI = new OpenAPI();
//        Components components = new Components();
//        SecurityScheme securityScheme = new SecurityScheme();
//        securityScheme.scheme("Bearer");
//        securityScheme.bearerFormat("JWT");
//        components.addSecuritySchemes("bearer-key", securityScheme);
//        return openAPI;
//    }


}
