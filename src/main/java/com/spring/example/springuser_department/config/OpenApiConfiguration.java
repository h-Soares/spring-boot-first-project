package com.spring.example.springuser_department.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class OpenApiConfiguration {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(new Info().title("Users and departments API").version("1.0.0")
                .description("My first API Restful.")
                .contact(new Contact()
                            .name("Linkedin")
                            .url("https://www.linkedin.com/in/hiago-soares-de-araujo-96840a271")));
    }
}