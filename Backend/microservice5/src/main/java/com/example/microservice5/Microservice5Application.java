package com.example.microservice5;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Microservice5Application {

	public static void main(String[] args) {
		SpringApplication.run(Microservice5Application.class, args);
	}

}
