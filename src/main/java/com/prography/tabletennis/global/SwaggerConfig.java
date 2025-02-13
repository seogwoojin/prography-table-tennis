package com.prography.tabletennis.global;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
class SwaggerConfig {
	@Bean
	public OpenAPI openAPI() {
		return new OpenAPI().info(apiInfo()).servers(
			List.of(new Server().url("http://localhost:8088"))
		);
	}

	private Info apiInfo() {
		return new Info().title("TableTennis").description("Prography Assignment API Swagger").version("1.0.0");
	}
}