package com.stevenjtk.trivia.commands;

import com.stevenjtk.trivia.dto.TriviaQuestion;
import com.stevenjtk.trivia.service.TriviaService;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;


@Component
public class MessageListener extends ListenerAdapter {

    private final Map<String, TriviaQuestion> activeTrivia = new ConcurrentHashMap<>();

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
        if (event.getName().equals("trivia")) {
            List<TriviaQuestion> questions = triviaService.getQuestions();
            if(questions.isEmpty()) {
                event.reply("Could not fetch questions").queue();
                return;

            }
            TriviaQuestion random = questions.get(new Random().nextInt(questions.size()));
            activeTrivia.put(event.getChannel().getId(), random);
            event.reply("Question: " + random.getQuestion().getText() + "\nDifficulty: " + random.getDifficulty()).queue();
        }
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if(event.getAuthor().isBot()) return;

        String channelId = event.getChannel().getId();
        String message = event.getMessage().getContentRaw().trim().toLowerCase();

        TriviaQuestion active = activeTrivia.get(channelId);

        if(active == null) return;

        if(message.equals(active.getCorrectAnswer().toLowerCase())) {
            event.getChannel().sendMessage(event.getAuthor().getAsMention() + "Correct!").queue();
            activeTrivia.remove(channelId);

        } else if(message.equals("test")) {
            event.getChannel().sendMessage(event.getAuthor().getAsMention() + "The answer is: " + active.getCorrectAnswer()).queue();
            activeTrivia.remove(channelId);

        } else  {
            event.getChannel().sendMessage(event.getAuthor().getAsMention() + "Incorrect! Run it back.").queue();
        }
    }
}



