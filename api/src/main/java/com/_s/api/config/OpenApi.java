package com._s.api.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "3S Api",
                description = "API de gestão empresarial interna: 3s Letreiros",
                version = "v1",
                contact = @Contact(
                        name = "Samuel Maia",
                        email = "smaia190500@gmail.com"
                )
        )
)
public class OpenApi {
}

