package com.stevenjtk.trivia;

import com.stevenjtk.trivia.commands.BotCommands;
import com.stevenjtk.trivia.jda.DiscordAPI;
import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.JDABuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TriviaApplication {

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(TriviaApplication.class, args);

	}
}
