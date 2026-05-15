package com.stevenjtk.trivia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TriviaApplication {

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(TriviaApplication.class, args);

	}
}
