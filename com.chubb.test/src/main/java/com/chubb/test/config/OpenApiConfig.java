package com.chubb.test.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

/**
 * Global OpenAPI metadata shown in the Swagger UI header
 * (http://localhost:8080/swagger-ui.html).
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI clientsApi() {
        return new OpenAPI().info(new Info()
                .title("Clients API")
                .description("REST API for client management. Lists, creates and deletes clients.")
                .version("1.0.0"));
    }
}
