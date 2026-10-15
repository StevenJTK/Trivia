package com.stevenjtk.trivia.listeners;

import com.stevenjtk.trivia.builders.MessageBuilder;
import com.stevenjtk.trivia.commands.GameStart;
import net.dv8tion.jda.api.components.actionrow.ActionRow;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import net.dv8tion.jda.api.components.buttons.Button;

import java.awt.*;

@Component
public class MessageListener extends ListenerAdapter {

    @Autowired
    GameStart gameStart;

    @Autowired
    MessageBuilder messageBuilder;

    @Override
    public void onGuildReady(GuildReadyEvent event) {
        Guild guild = event.getGuild();
        guild.updateCommands().addCommands(
                Commands.slash("play", "Trivia Time!")
        ).queue();
    }

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if (event.getName().equals("play")) {
            event.reply("Ready to test your trivia knowledge?")
                    .addComponents(ActionRow.of(Button.primary("play", "Play now!")))
                    .queue();
        }
    }

    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {
        if (event.getComponentId().equals("play")) {
            gameStart.onGameStart(event);
        }
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        gameStart.onResponse(event);
    }
}



