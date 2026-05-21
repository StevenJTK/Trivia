package com.stevenjtk.trivia.jda;

import net.dv8tion.jda.api.EmbedBuilder;
import org.springframework.stereotype.Component;

import java.awt.*;

@Component
public class Embed {

   public static  EmbedBuilder getEmbed() {
       return new EmbedBuilder()

            .setDescription("Play a trivia game!")
            .setAuthor("Steve's Trivia Bot")
            .setColor(Color.PINK)
            .setFooter("Let's play a game", "https://cdn.discordapp.com/embed/avatars/1.png");
    };
}



