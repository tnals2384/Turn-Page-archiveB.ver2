package com.example.turnpage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class TurnpageApplication {
	public static void main(String[] args) {
		SpringApplication.run(TurnpageApplication.class, args);
	}
}
