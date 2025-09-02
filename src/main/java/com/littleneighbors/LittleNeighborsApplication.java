package com.littleneighbors;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class LittleNeighborsApplication {

	public static void main(String[] args) {
		SpringApplication.run(LittleNeighborsApplication.class, args);
	}

	}
