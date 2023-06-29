package com.mytasks.app.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.security.SecuritySchemes;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(title = "My Tasks API", version = "1.0",
        contact = @Contact(name = "Roberto G Santos", email = "ccp.gualberto@gmail.com")),
        security = {@SecurityRequirement(name = "basicAuth"), @SecurityRequirement(name = "bearerToken")}
)
@SecuritySchemes(@SecurityScheme(name = "bearerToken", type = SecuritySchemeType.HTTP, scheme = "bearer", bearerFormat = "JWT"))
public class OpenApiConfig {
}
