package com.project.device.config;

import com.project.device.util.MessageConstants;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    OpenAPI swaggerCustomConfig() {
        return new OpenAPI().info(new Info().title(MessageConstants.SWAGGER_TITLE));
    }
}
