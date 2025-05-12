package com.IT3180;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class It3180Application {

	public static void main(String[] args) {
		SpringApplication.run(It3180Application.class, args);
	}

}
