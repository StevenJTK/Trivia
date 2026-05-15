package com.stevenjtk.trivia.listeners;

import com.stevenjtk.trivia.dto.TriviaQuestion;
import com.stevenjtk.trivia.dto.TriviaStart;
import com.stevenjtk.trivia.api.GameState;
import com.stevenjtk.trivia.schedulers.DailyGamesReset;
import com.stevenjtk.trivia.service.LimitService;
import com.stevenjtk.trivia.service.TriviaService;
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


@Component
public class MessageListener extends ListenerAdapter {

    private final Map<Long, TriviaStart> activeTrivia = new ConcurrentHashMap<>();
    private final Map<Long, GameState> activeGames = new ConcurrentHashMap<>();

    @Autowired
    TriviaService triviaService;

    @Autowired
    LimitService limitService;

    @Override
    public void onGuildReady(GuildReadyEvent event) {
        Guild guild = event.getGuild();
        guild.updateCommands().addCommands(
                Commands.slash("trivia", "Get a trivia question"),
                Commands.slash("hello", "Button Test")
        ).queue();

    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
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

        if (event.getName().equals("hello")) {
            event.reply("Click here")
                    .setComponents(ActionRow.of(Button.primary("hello", "Click here"))).queue();
        }
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        long userId = event.getAuthor().getIdLong();
        if(event.getAuthor().isBot()) return;

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



