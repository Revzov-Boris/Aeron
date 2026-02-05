package com.example.AeroN;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class AeroNApplication {

	public static void main(String[] args) {
		SpringApplication.run(AeroNApplication.class, args);
	}

}
