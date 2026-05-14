package com.trust.amanat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class AmanatApplication {

	public static void main(String[] args) {
		SpringApplication.run(AmanatApplication.class, args);
	}

}
