package com.selfcare.logger.v1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class Logger {

	public static void main(String[] args) {
		SpringApplication.run(Logger.class, args);
	}

}
