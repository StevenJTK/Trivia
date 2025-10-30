package com.stevenjtk.trivia.jda;


import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;


/*
/ Class dedicated for connecting to the Java Discord API (JDA) and handle events.
*/
public class DiscordAPI implements EventListener {

    // We instance JDA here so that other methods can have access.
    private JDA jda;
    private final String BOT_TOKEN = System.getenv("BOT_TOKEN");

    /**
     * Method handling for API connection.
     * @throws InterruptedException - we throw this in case awaitReady is interrupted.
     * @implNote LoginException - Sought to implement LoginException, however this is not needed as modern JDA versions handle token errors
     * internally.
     * JDA is called to initialize JDA instance via token. Then is registers this as an event listener so that
     * it can react to Discord related events. Build() is when the bot is actually connected to Discord.
     * Lastly, awaitReady() purposely blocks the thread until the bot is fully connected to JDA - only after
     * the connection is established can other blocks of code run.
     */

    public void connectToAPI() throws InterruptedException {
        jda = JDABuilder.createDefault(BOT_TOKEN + System.getenv("BOT_TOKEN"))
                .addEventListeners(this)
                .build();

        jda.awaitReady();
        System.out.println("Bot has connected!");
    }

    @Override
    public void onEvent(GenericEvent event) {
        if(event instanceof ReadyEvent)
            System.out.println("API is connected.");
    }
}
