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
            embedBuilder.getEmbed().setTitle("Hello Gamer").setDescription("This is a desc").setAuthor("Steven").setColor(Color.PINK);

            Button dangerNoodle = Button.danger("yes-button", "DANGER NOODLE");
            Button notDangerNoodle = Button.danger("no-button", "Not Danger Noodle");

            event.reply(
                    messageBuilder
                            .setContent("Hello")
                            .setEmbeds(embedBuilder.getEmbed().setTitle("Hello Gamer").setDescription("This is a desc").setAuthor("Steven").setColor(Color.PINK).build())
                            .addComponents(dangerNoodle, notDangerNoodle)
                            .build()
            ).queue();

        }
    }
}
