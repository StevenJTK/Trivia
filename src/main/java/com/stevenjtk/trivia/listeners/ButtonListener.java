package com.stevenjtk.trivia.listeners;


import com.stevenjtk.trivia.jda.Embed;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ButtonListener extends ListenerAdapter {

    @Autowired
    Embed embed;

    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {
        if(event.getComponentId().equals("play")) {
            event.replyEmbeds(Embed.getEmbed().build()).queue();
        }
    }
}
