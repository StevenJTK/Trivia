package com.stevenjtk.trivia.commands;

import com.stevenjtk.trivia.dto.TriviaQuestion;
import com.stevenjtk.trivia.service.TriviaService;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Random;

@Component
public class BotCommands extends ListenerAdapter {

    @Autowired
    TriviaService triviaService;

    @Override
    public void onGuildReady(GuildReadyEvent event) {
        Guild guild = event.getGuild();
        guild.updateCommands().addCommands(
                Commands.slash("trivia", "Get a trivia question")
        ).queue();

    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if(event.getName().equals("trivia")) {
            List<TriviaQuestion> questions = triviaService.getQuestions();
            TriviaQuestion random = questions.get(new Random().nextInt(questions.size()));
            event.reply("Question: " + random.getQuestion().getText()).queue();
        }
    }




}

