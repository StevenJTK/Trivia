package com.stevenjtk.trivia.builders;

import org.springframework.stereotype.Component;

import java.awt.*;

@Component
public class EmbedBuilder {

    public static net.dv8tion.jda.api.EmbedBuilder getEmbed() {
        return new net.dv8tion.jda.api.EmbedBuilder();
    }
}



