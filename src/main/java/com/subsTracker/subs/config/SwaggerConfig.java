package com.subsTracker.subs.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI subsTrackerOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Subs Tracker API")
                        .description("Subscription management backend")
                        .version("1.0"));
    }
}
