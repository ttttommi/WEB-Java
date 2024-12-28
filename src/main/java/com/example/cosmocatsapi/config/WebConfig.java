package com.example.cosmocatsapi.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {

    @Configuration
    @Profile("default")
    public static class DefaultResourceConfig implements WebMvcConfigurer {
        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
            registry.addResourceHandler("/resources/api-specs/**")
                    .addResourceLocations("file:/default/path/to/resources/api-specs/");
        }
    }

    @Configuration
    @Profile("prod")
    public static class ProdResourceConfig implements WebMvcConfigurer {
        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
            registry.addResourceHandler("/resources/api-specs/**")
                    .addResourceLocations("file:/prod/path/to/resources/api-specs/");
        }
    }
}
