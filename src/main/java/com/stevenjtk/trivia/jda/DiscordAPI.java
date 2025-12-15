package com.stevenjtk.trivia.jda;
import com.stevenjtk.trivia.commands.BotCommands;
import io.github.cdimascio.dotenv.Dotenv;
import jakarta.annotation.PostConstruct;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.security.auth.login.LoginException;

/*
/ Class dedicated for connecting to the Java Discord API (JDA) and handle events.
*/

@Component
public class DiscordAPI {

    private JDA jda;
    private final BotCommands botCommands;
    private final String BOT_TOKEN;
    private final Dotenv dotenv;

    @Autowired
    public DiscordAPI(BotCommands botCommands, Dotenv dotenv) throws LoginException, InterruptedException {
        this.botCommands = botCommands;
        this.BOT_TOKEN = dotenv.get("BOT_TOKEN");
        this.dotenv = dotenv;
    }

    @PostConstruct
    public void connectToAPI() throws Exception {
        String BOT_TOKEN = dotenv.get("BOT_TOKEN");

        jda = JDABuilder.createDefault(BOT_TOKEN)
                .addEventListeners(botCommands)
                .build();

        jda.awaitReady();
        System.out.println("Bot has connected!");
    }
}