package com.example.microservice2.Configuration;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.core.JsonFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@Configuration
public class JacksonConfig {

    @Bean
    public ObjectMapper objectMapper() {
        // Create a custom JsonFactory
        JsonFactory factory = new JsonFactory();

        // Disable stream-write constraints
        factory.enable(JsonFactory.Feature.INTERN_FIELD_NAMES); // This is an example, you might adjust as necessary

        // Build the ObjectMapper with the custom factory
        ObjectMapper objectMapper = Jackson2ObjectMapperBuilder.json()
                .factory(factory)
                .featuresToEnable(SerializationFeature.INDENT_OUTPUT)
                .build();

        return objectMapper;
    }
}


