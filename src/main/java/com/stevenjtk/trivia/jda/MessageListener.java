package com.stevenjtk.trivia.jda;

import com.stevenjtk.trivia.dto.TriviaQuestion;
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
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;


@Component
public class MessageListener extends ListenerAdapter {

    private final Map<String, TriviaQuestion> activeTrivia = new ConcurrentHashMap<>();
    private final Map<Long, GameState> activeGames = new ConcurrentHashMap<>();

    @Autowired
    TriviaService triviaService;

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

            GameState state = activeGames.get(userId);

            if(activeGames.containsKey(userId)) {
                event.getHook().sendMessage("No hijacking")
                        .setEphemeral(true).queue();
                return;
            }
            List<TriviaQuestion> questions = triviaService.getQuestions(userId);
            if(questions.isEmpty()) {
                event.getHook().sendMessage("Could not fetch questions").queue();
                return;
            }

            GameState newState = new GameState(userId, questions);
            activeGames.put(userId, newState);

            TriviaQuestion random = questions.get(new Random().nextInt(questions.size()));
            List<String> options = triviaService.shuffleAnswers(random);

            StringBuilder message = new StringBuilder();
            message.append("Question: ").append(random.getQuestion().getText()).append("\n");
            message.append("Options:\n");
            for (int i = 0; i < options.size(); i++) {
                message.append(i + 1).append(". ").append(options.get(i)).append("\n");
            }
            activeTrivia.put(event.getChannel().getId(), random);
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
        GameState game = activeGames.get(userId);

        String channelId = event.getChannel().getId();
        String message = event.getMessage().getContentRaw().trim().toLowerCase();


        TriviaQuestion active = activeTrivia.get(channelId);

        if(event.getAuthor().isBot()) return;

        if(active == null) return;

        if(message.equals(active.getCorrectAnswer().toLowerCase())) {
            event.getChannel().sendMessage(event.getAuthor().getAsMention() + "Correct!").queue();
            activeTrivia.remove(channelId);

        } else if(message.equals("!answer")) {
            event.getChannel().sendMessage(event.getAuthor().getAsMention() + "The answer is: " + active.getCorrectAnswer()).queue();
            activeTrivia.remove(channelId);

        } else  {
            event.getChannel().sendMessage(event.getAuthor().getAsMention() + "Incorrect! Try again or write !answer to give up.").queue();
        }
    }
}



