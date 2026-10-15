package com.stevenjtk.trivia.commands;

import com.stevenjtk.trivia.dto.TriviaQuestion;
import com.stevenjtk.trivia.dto.TriviaStart;
import com.stevenjtk.trivia.builders.EmbedBuilder;
import com.stevenjtk.trivia.jda.GameState;
import com.stevenjtk.trivia.service.LimitService;
import com.stevenjtk.trivia.service.TriviaService;
import net.dv8tion.jda.api.interactions.callbacks.IReplyCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class GameStart extends ListenerAdapter {

    private final Map<Long, TriviaStart> activePlayer = new ConcurrentHashMap<>();
    private final Map<Long, GameState> activeGames = new ConcurrentHashMap<>();

    @Autowired
    LimitService limitService;

    @Autowired
    TriviaService triviaService;

    /* Things to add:
    Multiple players at once causes a lot of clutter and confusion - see if you can create a seperate GUI for each player
     */


    private Long getMessageEventUserId(MessageReceivedEvent event) {
        return event.getAuthor().getIdLong();
    }

    private Long getEventUserId(IReplyCallback event) {
        return event.getUser().getIdLong();
    }

    public void onGameStart(IReplyCallback event) {
        if (event.getUser().isBot()) return;
        {

            event.deferReply().queue();

            limitService.recordGame(getEventUserId(event));
            TriviaStart result = triviaService.startGame((getEventUserId(event)), event.getChannel().getIdLong());

            if (!result.isSuccess()) {
                event.getHook().sendMessage(result.getError()).setEphemeral(true).queue();
                return;
            }

            activePlayer.put(getEventUserId(event), result);
            activeGames.put(getEventUserId(event), new GameState(getEventUserId(event), result.getQuestions()));

            // Fetch Questions
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
        if (event.getAuthor().isBot()) return;

        String message = event.getMessage().getContentRaw().trim().toLowerCase().replaceAll("[^a-zA-Z0-9 ]", " ");

        TriviaStart activeGame = activePlayer.get(getMessageEventUserId(event));
        if (activeGame == null) return;

        TriviaQuestion question = activeGame.getQuestions().get(0);
        String correctAnswer = question.getCorrectAnswer().toLowerCase().replaceAll("[^a-zA-Z0-9]", " ");


        if (message.equals(correctAnswer)) {
            event.getChannel().sendMessage(event.getAuthor().getAsMention() + " Correct!").queue();
            activePlayer.remove(getMessageEventUserId(event));
            triviaService.endGame(getMessageEventUserId(event));

        } else if (message.equals("answer")) {
            event.getChannel().sendMessage(event.getAuthor().getAsMention() + "The answer is: " + question.getCorrectAnswer()).queue();
            activePlayer.remove(getMessageEventUserId(event));
            triviaService.endGame(getMessageEventUserId(event));

        } else {
            event.getChannel().sendMessage(event.getAuthor().getAsMention() + "Incorrect! Try again or write !answer to give up.").queue();
        }
    }
}
