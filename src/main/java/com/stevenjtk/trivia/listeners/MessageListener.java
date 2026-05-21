package com.stevenjtk.trivia.listeners;

import com.stevenjtk.trivia.dto.TriviaQuestion;
import com.stevenjtk.trivia.dto.TriviaStart;
import com.stevenjtk.trivia.jda.GameState;
import com.stevenjtk.trivia.service.LimitService;
import com.stevenjtk.trivia.service.TriviaService;
import com.stevenjtk.trivia.commands.GameStart;
import net.dv8tion.jda.api.components.actionrow.ActionRow;
import net.dv8tion.jda.api.components.buttons.Button;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class MessageListener extends ListenerAdapter {

    @Autowired
    GameStart gameStart;

    @Override
    public void onGuildReady(GuildReadyEvent event) {
        Guild guild = event.getGuild();
        guild.updateCommands().addCommands(
                Commands.slash("trivia", "Get a trivia question"),
                Commands.slash("play", "Play now!")
        ).queue();

    }

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        gameStart.onGameStart(event);

        if (event.getName().equals("play")) {
            event.reply("")
                    .setComponents(ActionRow.of(Button.primary("play", "Click here"))).queue();
        }
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        gameStart.onResponse(event);
    }
}



