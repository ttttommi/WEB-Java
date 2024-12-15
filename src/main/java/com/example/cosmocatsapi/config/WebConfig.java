package com.example.cosmocatsapi.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String resourceLocation = "file:/default/path/to/resources/api-specs/";

        if (System.getenv("SPRING_PROFILES_ACTIVE").equals("prod")) {
            resourceLocation = "file:/prod/path/to/resources/api-specs/";
        }

        registry.addResourceHandler("/resources/api-specs/**")
                .addResourceLocations(resourceLocation);
    }
}
