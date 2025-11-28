package com.bricks.challenge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class BricksApplication {

    public static void main(String[] args) {
        SpringApplication.run(BricksApplication.class, args);
    }
}
