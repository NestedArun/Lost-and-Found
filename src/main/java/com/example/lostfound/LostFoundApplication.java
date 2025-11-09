package com.example.lostfound;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class LostFoundApplication {

    public static void main(String[] args) {
        // This starts the Spring Boot application
        SpringApplication.run(LostFoundApplication.class, args);
        System.out.println("\nLost & Found System is running on http://localhost:8080\n");
    }
}
