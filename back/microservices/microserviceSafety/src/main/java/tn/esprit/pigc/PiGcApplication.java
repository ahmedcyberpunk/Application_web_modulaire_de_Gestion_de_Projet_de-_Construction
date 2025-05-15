package tn.esprit.pigc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PiGcApplication {

    public static void main(String[] args) {
        SpringApplication.run(PiGcApplication.class, args);
    }

}
