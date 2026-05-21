package com.stevenjtk.trivia.listeners;

import com.stevenjtk.trivia.builders.MessageBuilder;
import com.stevenjtk.trivia.commands.GameStart;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import net.dv8tion.jda.api.components.buttons.Button;

@Component
public class MessageListener extends ListenerAdapter {

    @Autowired
    GameStart gameStart;

    @Autowired
    ButtonListener buttonListener;

    @Autowired
    MessageBuilder messageBuilder;

    @Override
    public void onGuildReady(GuildReadyEvent event) {
        Guild guild = event.getGuild();
        guild.updateCommands().addCommands(
                Commands.slash("trivia", "Get a trivia question"),
                Commands.slash("play", "Play a game")
        ).queue();
    }

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        gameStart.onGameStart(event);

        if (event.getName().equals("play")) {
            Button playButton = Button.primary("play", "Play!");

            event.reply(
                    messageBuilder.setContent("Click to play").addComponents(playButton).build()
            ).queue();
        }

    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        gameStart.onResponse(event);
    }
}



