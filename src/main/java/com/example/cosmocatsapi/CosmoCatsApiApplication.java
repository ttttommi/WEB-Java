package com.example.cosmocatsapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
public class CosmoCatsApiApplication {
    private static final Logger logger = LoggerFactory.getLogger(CosmoCatsApiApplication.class);
    
    public static void main(String[] args) {
        logger.info("Starting CosmoCats API...");
        SpringApplication.run(CosmoCatsApiApplication.class, args);
        logger.info("CosmoCats API started successfully.");
    }
}
