package com.don.corretora;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;


@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@EntityScan(basePackages = "com.don.corretora")
public class CorretoraApplication {

	public static void main(String[] args) {
		SpringApplication.run(CorretoraApplication.class, args);
	}

}
