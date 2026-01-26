package com.cv_personal.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {
		System.out.println("BackendApplication is starting up!"); // Added for debugging console output
		SpringApplication.run(BackendApplication.class, args);
	}

}

