package com.zapcom.ReviewProcessor.Config;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI reviewProjectOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Review Service API")
                        .description("API documentation for the Review microservice")
                        .version("1.0")
                        .contact(new Contact()
                                .name("Thirumalesh Gajula")
                                .email("gajulathirumalesh22448@gmail.com")
                                .url("https://your-portfolio-or-linkedin.com")
                        )
                );
    }
}
