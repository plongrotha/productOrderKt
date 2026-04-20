package org.plongrotha.productorder.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Contact
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.License
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenApiConfig {

    @Bean
    fun customOpenAPI(): OpenAPI {
        return OpenAPI().info(
            Info().title("CRUD API").version("1.0.0")
                .description("API documentation for Product, Customer, and Order management.").contact(
                    Contact().name("Developer Support").email("support@example.com")
                ).license(
                    License().name("Apache 2.0").url("https://springdoc.org")
                )
        )
    }
}