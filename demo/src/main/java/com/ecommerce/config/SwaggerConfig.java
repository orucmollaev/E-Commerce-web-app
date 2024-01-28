package com.ecommerce.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(
        title = "Swagger file for E-Commerce project. ",
        version = "1.0.0",
        description = "Welcome to my E-Commerce project introduction! "))
public class SwaggerConfig {
}