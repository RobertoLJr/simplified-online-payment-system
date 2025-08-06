package com.robertoljr.sops.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Simplified Online Payment System - SOPS")
                        .version("v1")
                        .description("RESTful API for a simplified online payment system.")
                );
    }
}
