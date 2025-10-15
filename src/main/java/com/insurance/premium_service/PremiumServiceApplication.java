package com.insurance.premium_service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PremiumServiceApplication {

	private static final Logger log = LoggerFactory.getLogger(PremiumServiceApplication.class);

	public static void main(String[] args) {
		log.info("Starting Premium Service Application...");
		SpringApplication.run(PremiumServiceApplication.class, args);
		log.info("Premium Service Application started successfully.");

	}
}
