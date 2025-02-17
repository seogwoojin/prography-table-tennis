package com.prography.tabletennis.global.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(apiInfo())
                .servers(List.of(new Server().url("http://localhost:8080")));
    }

    private Info apiInfo() {
        return new Info()
                .title("TableTennis")
                .description("Prography Assignment API Swagger")
                .version("1.0.0");
    }
}
