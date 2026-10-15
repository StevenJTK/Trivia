package com.stevenjtk.trivia.listeners;


import com.stevenjtk.trivia.builders.EmbedBuilder;
import com.stevenjtk.trivia.builders.MessageBuilder;
import net.dv8tion.jda.api.components.buttons.Button;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.*;

@Component
public class ButtonListener extends ListenerAdapter {

    @Autowired
    EmbedBuilder embedBuilder;

    @Autowired
    MessageBuilder messageBuilder;

    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {
        if (event.getComponentId().equals("play")) {
            embedBuilder.getEmbed().setTitle("Welcome to the Trivia Game!").setDescription("Trivia Questions - Everyday!").setAuthor("Steven").setColor(Color.PINK);

            Button exitButton = Button.danger("no-button", "Exit");

            event.reply(
                    messageBuilder
                            .setContent(" ")
                            .setEmbeds(embedBuilder.getEmbed().setTitle("Welcome to the Trivia Game!").setDescription("Daily Trivia Questions").setAuthor("StevenBot").setColor(Color.PINK).build())
                            .addComponents(exitButton)
                            .build()
            ).queue();

        }
    }
}
