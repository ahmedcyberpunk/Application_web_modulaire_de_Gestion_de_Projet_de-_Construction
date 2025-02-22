package tn.esprit.pi.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration // ✅ Add this to ensure Spring Boot loads it!
public class WebConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {


                registry.addMapping("/**") // Apply to all endpoints
                        .allowedOrigins("http://localhost:4200") // Allow Angular frontend
                        .allowedMethods(
                                HttpMethod.GET.name(),
                                HttpMethod.POST.name(),
                                HttpMethod.PUT.name(),
                                HttpMethod.DELETE.name(),
                                HttpMethod.OPTIONS.name() // ✅ Needed for preflight!
                        )
                        .allowedHeaders("*") // ✅ Allow all headers
                        .allowCredentials(true); // ✅ Allow cookies/auth headers
            }
            @Override
            public void addResourceHandlers(ResourceHandlerRegistry registry) {
                // Maps /images/** to the images folder in static
                registry.addResourceHandler("/images/**")
                        .addResourceLocations("classpath:/static/images/");
            }
        };
    }
}
