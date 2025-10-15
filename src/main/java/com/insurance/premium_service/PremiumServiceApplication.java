package com.insurance.premium_service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main application class for the Insurance Premium Service.
 * 
 * This Spring Boot application provides REST API endpoints for calculating
 * insurance premiums based on various factors including:
 *   1. Vehicle type and associated risk factors
 *   2. Yearly mileage ranges and corresponding multipliers
 *   3. Geographic region based on postcode
 *   4. Base premium rates
 * 
 * The application uses PostgreSQL database to store reference data
 * for premium calculation factors and Spring Data JPA for data access.
 * 
 * @author Aylin Yilmaz
 */
@SpringBootApplication
public class PremiumServiceApplication {

	private static final Logger log = LoggerFactory.getLogger(PremiumServiceApplication.class);

	/**
	 * Main entry point for the Premium Service application.
	 * 
	 * Initializes the Spring Boot application context and starts the embedded
	 * web server on the configured port (default: 8082).
	 * 
	 */
	public static void main(String[] args) {
		log.info("Starting Premium Service Application...");
		SpringApplication.run(PremiumServiceApplication.class, args);
		log.info("Premium Service Application started successfully.");

	}
}
