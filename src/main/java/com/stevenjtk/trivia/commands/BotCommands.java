package com.stevenjtk.trivia.commands;


import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;


import java.util.Objects;


public class BotCommands extends ListenerAdapter {
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

    // add failsafe of when user does not input anything to avoid null + a proper user response
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if(event.getName().equals("animal")) {
            event.reply(Objects.requireNonNull(event.getOption("type")).getAsString()).queue();
        }

        if (event.getName().equals("mollie")) {
            event.reply(Objects.requireNonNull(event.getOption("type")).getAsString()).queue();
        }
    }




}

