package com.example.cosmocatsapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class CosmoCatsApiApplication {

    public static void main(String[] args) {
        log.info("Starting CosmoCats API...");
        SpringApplication.run(CosmoCatsApiApplication.class, args);
        log.info("CosmoCats API started successfully.");
    }
}
