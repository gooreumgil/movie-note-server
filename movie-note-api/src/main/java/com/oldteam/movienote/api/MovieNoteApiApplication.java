package com.oldteam.movienote.api;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackageClasses = {MovieNoteApiApplication.class}, basePackages = "com.oldteam.movienote.core")
public class MovieNoteApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(MovieNoteApiApplication.class, args);
    }

}
