package com.compass.reinan.api_ecommerce.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocOpenApiConfig {

    @Bean
    public OpenAPI openAPI(){
        return new OpenAPI()
                .info(info());
    }
    private Info info(){
        return  new Info()
                .title("Demo Park API")
                .description("API for managing parking lots and their vehicles")
                .version("1.0.0")
                .contact(new Contact()
                        .name("Reinan")
                        .email("reinan@gmail.com")
                        .url("https://github.com/reinanpl")
                );
    }
}
