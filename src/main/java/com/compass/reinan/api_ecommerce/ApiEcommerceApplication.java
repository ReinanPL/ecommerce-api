package com.compass.reinan.api_ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class ApiEcommerceApplication {
	public static void main(String[] args) {
		SpringApplication.run(ApiEcommerceApplication.class, args);
	}
}
