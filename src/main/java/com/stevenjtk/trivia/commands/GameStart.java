package com.stevenjtk.trivia.commands;

import com.stevenjtk.trivia.dto.TriviaQuestion;
import com.stevenjtk.trivia.dto.TriviaStart;
import com.stevenjtk.trivia.jda.GameState;
import com.stevenjtk.trivia.service.LimitService;
import com.stevenjtk.trivia.service.TriviaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import net.dv8tion.jda.api.components.actionrow.ActionRow;
import net.dv8tion.jda.api.components.buttons.Button;
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
import java.util.concurrent.ConcurrentHashMap;

import java.util.List;

@Component
public class GameStart extends ListenerAdapter{

    private final Map<Long, TriviaStart> activeTrivia = new ConcurrentHashMap<>();
    private final Map<Long, GameState> activeGames = new ConcurrentHashMap<>();

    @Autowired
    LimitService limitService;

    @Autowired
    TriviaService triviaService;


    public void onGameStart(SlashCommandInteractionEvent event) {
        if (event.getName().equals("trivia")) {
            long userId = event.getUser().getIdLong();
            event.deferReply().queue();

            if(limitService.limitReached(userId)) {
                event.getChannel().sendMessage(event.getUser().getAsMention() + "You have reached your daily limit. Come back tomorrow!").queue();
                return;
            }

            limitService.recordGame(userId);

            TriviaStart result = triviaService.startGame(userId, event.getChannel().getIdLong());

            if(!result.isSuccess()) {
                event.getHook().sendMessage(result.getError()).setEphemeral(true).queue();
                return;
            }

            activeTrivia.put(userId, result);
            activeGames.put(userId, new GameState(userId, result.getQuestions()));

            TriviaQuestion question = result.getQuestions().get(0);
            List<String> options = result.getOptions().get(question);

            StringBuilder message = new StringBuilder();
            message.append("Question: ").append(question.getQuestion().getText()).append("\n");
            message.append("Options:\n");
            for (int i = 0; i < options.size(); i++) {
                message.append(i + 1).append(". ").append(options.get(i)).append("\n");
            }
            event.getHook().sendMessage(message.toString()).queue();
        }
    }


    public void onResponse(MessageReceivedEvent event) {
        long userId = event.getAuthor().getIdLong();
        if(event.getAuthor().isBot()) return;

        // Required to find the channel
        String channelId = event.getChannel().getId();
        String message = event.getMessage().getContentRaw().trim().toLowerCase().replaceAll("[^a-zA-Z0-9 ]", "");

        TriviaStart activeGame = activeTrivia.get(userId);
        if(activeGame == null) return;

        TriviaQuestion question = activeGame.getQuestions().get(0);
        String correctAnswer = question.getCorrectAnswer().toLowerCase();


        if(message.equals(correctAnswer)) {
            event.getChannel().sendMessage(event.getAuthor().getAsMention() + " Correct!").queue();
            activeTrivia.remove(userId);
            triviaService.endGame(userId);

        } else if(message.equals("!answer")) {
            event.getChannel().sendMessage(event.getAuthor().getAsMention() + "The answer is: " + question.getCorrectAnswer()).queue();
            activeTrivia.remove(userId);
            triviaService.endGame(userId);

        } else  {
            event.getChannel().sendMessage(event.getAuthor().getAsMention() + "Incorrect! Try again or write !answer to give up.").queue();
        }
    }
}
