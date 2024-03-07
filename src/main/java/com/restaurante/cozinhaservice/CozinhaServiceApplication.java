package com.restaurante.cozinhaservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CozinhaServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CozinhaServiceApplication.class, args);
	}

}
