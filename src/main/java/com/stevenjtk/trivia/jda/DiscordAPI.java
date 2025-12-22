package com.stevenjtk.trivia.jda;
import io.github.cdimascio.dotenv.Dotenv;
import jakarta.annotation.PostConstruct;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.security.auth.login.LoginException;

/*
/ Class dedicated for connecting to the Java Discord API (JDA) and handle events.
*/

@Component
public class DiscordAPI {

    private JDA jda;
    private final MessageListener messageListener;
    private final ButtonListener buttonListener;
    private final String BOT_TOKEN;
    private final Dotenv dotenv;

    @Autowired
    public DiscordAPI(MessageListener messageListener, ButtonListener buttonListener, Dotenv dotenv) throws LoginException, InterruptedException {
        this.messageListener = messageListener;
        this.buttonListener = buttonListener;
        this.BOT_TOKEN = dotenv.get("BOT_TOKEN");
        this.dotenv = dotenv;
    }

    @PostConstruct
    public void connectToAPI() throws Exception {
        String BOT_TOKEN = dotenv.get("BOT_TOKEN");
        jda = JDABuilder.createDefault(BOT_TOKEN)
                .enableIntents(GatewayIntent.MESSAGE_CONTENT)
                .addEventListeners(messageListener, buttonListener)
                .build();

        jda.awaitReady();
        System.out.println("Bot has connected!");
    }
}