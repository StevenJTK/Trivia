package com.stevenjtk.trivia;

import com.stevenjtk.trivia.jda.DiscordAPI;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TriviaApplication {

	public static void main(String[] args) throws InterruptedException {

		SpringApplication.run(TriviaApplication.class, args);

		DiscordAPI api = new DiscordAPI();

		api.connectToAPI();

	}

}
