package com.example.microservice5.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // Allow CORS for specific endpoints or all endpoints
        registry.addMapping("/**") // You can specify a more specific URL pattern if needed
                .allowedOrigins("*") // Replace with your Angular app URL
                .allowedMethods("GET", "POST", "PUT", "DELETE") // Allow the HTTP methods you need
                .allowedHeaders("*"); // Allow all headers
    }
}
