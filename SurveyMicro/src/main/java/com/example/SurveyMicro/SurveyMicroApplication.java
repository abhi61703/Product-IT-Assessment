package com.example.SurveyMicro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Main class for the Survey Microservice application.
 *
 * This class is the entry point for the Spring Boot application.
 * It also enables Feign clients and service discovery.
 */
@SpringBootApplication
@EnableFeignClients // Enables the use of Feign clients for inter-service communication
@EnableDiscoveryClient // Registers this service with a service discovery server (e.g., Eureka)
public class SurveyMicroApplication {

	public static void main(String[] args) {
		// Launches the Spring Boot application
		SpringApplication.run(SurveyMicroApplication.class, args);
	}
}
