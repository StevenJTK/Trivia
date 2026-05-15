package com.stevenjtk.trivia.listeners;


import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.stereotype.Component;

@Component
public class ButtonListener extends ListenerAdapter {
    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {
        if(event.getComponentId().equals("hello")) {
            event.reply("welcome!").queue();
        }
    }
}
