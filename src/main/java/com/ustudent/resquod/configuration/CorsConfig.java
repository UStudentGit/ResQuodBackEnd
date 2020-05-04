package com.ustudent.resquod.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/*")
                .allowedMethods("GET", "POST", "PATCH", "DELETE", "OPTIONS")
                .allowedHeaders("Access-Control-Allow-Method",
                        "Access-Control-Allow-Origin",
                        "Access-Control-Allow-Credentials",
                        "Content-Type",
                        "Authorization")
                .allowCredentials(true);
    }
}
