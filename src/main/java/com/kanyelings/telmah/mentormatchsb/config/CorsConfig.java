package com.kanyelings.telmah.mentormatchsb.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebMvc
@Configuration
public class CorsConfig implements WebMvcConfigurer {
    private static final String[] ALLOWED_ORIGINS = new String[] {
            "http://localhost:8080",
            "http://localhost:4200",
            "http://localhost:8000",
            "https://mentormatch-ng.vercel.app",
            "https://mentormatch-ng-iota.vercel.app"
    };

    @Override
    public void addCorsMappings(CorsRegistry corsRegistry) {
        corsRegistry.addMapping("/**")
                .allowedOrigins(ALLOWED_ORIGINS);
    }
}
