package com.stevenjtk.trivia.commands;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;


public class BotCommands extends ListenerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(BotCommands.class);

    @Override
    public void onGuildReady(GuildReadyEvent event) {
        Guild guild = event.getGuild();

        guild.updateCommands().addCommands(
                        Commands.slash("animal", "Finds a random animal")
                                .addOptions(
                                        new OptionData(OptionType.STRING, "type", "The type of animal to find")
                                                .addChoice("Bird", "bird")
                                                .addChoice("Big Cat", "bigcat")
                                                .addChoice("Canine", "canine")
                                                .addChoice("Fish", "fish")),
                        Commands.slash("mollie", "Girls Generation stan")
                                .addOptions(
                                        new OptionData(OptionType.STRING, "type", "The type of stan")
                                                .addChoice("Fanatic", "fanatic")
                                                .addChoice("Test", "test")
                                )).

                queue();
    }

    /**
     * Method provided by JDA
     * When a user uses slash to interact with the bot, it checks
     * if the correct input is requested, they provide a type, in example a bird
     * We fetch our choices in the previous method
     * If the user does not specify a type, the method returns null and displays to the user
     * that they must return a type of animal.
     * @param event required by JDA in order to reach a user on Discord
     */
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getName().equals("animal")) {

            String type = event.getOption("type") != null ? event.getOption("type").getAsString() : null;

            if (type == null || type.isBlank()) {
                event.reply("You must insert a type of animal.").setEphemeral(true).queue();
                return;
            }
            event.reply("The answer is: " + type).queue();
        }
    }
}

