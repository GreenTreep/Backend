package fr.parisnanterre.greentrip.backend.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("GreenTrip API")
                .version("v1")
                .description("API de gestion de GreenTrip")
                .termsOfService("http://greentrip.us/terms")
                .contact(new Contact().name("Support GreenTrip").email("support@greentrip.us").url("https://greentrip.us"))
                .license(new License().name("Apache 2.0").url("http://springdoc.org"))
            )
            .externalDocs(new ExternalDocumentation()
                .description("Documentation supplémentaire")
                .url("https://github.com/greentreep")
            );
    }
}
