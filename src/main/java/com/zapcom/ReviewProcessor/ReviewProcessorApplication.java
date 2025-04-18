package com.zapcom.ReviewProcessor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ReviewProcessorApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReviewProcessorApplication.class, args);
	}

}
