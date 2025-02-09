package com.don.don;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class DonApplication {

	public static void main(String[] args) {
		SpringApplication.run(DonApplication.class, args);
	}

}
