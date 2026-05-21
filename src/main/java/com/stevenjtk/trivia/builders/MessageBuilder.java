package com.stevenjtk.trivia.builders;

import net.dv8tion.jda.api.components.buttons.Button;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import net.dv8tion.jda.api.components.actionrow.ActionRow;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class MessageBuilder {

    private final MessageCreateBuilder messageBuilder = new MessageCreateBuilder();

    public MessageBuilder setContent(String content) {
        messageBuilder.setContent(content);
        return this;
    }

    public MessageBuilder addComponents(Button... buttons) {
        messageBuilder.addComponents(ActionRow.of(Arrays.asList(buttons)));
        return this;
    }

    public MessageBuilder setEmbeds(MessageEmbed... embeds) {
        messageBuilder.setEmbeds(embeds);
        return this;
    }

    public MessageCreateData build() {
        return messageBuilder.build();
    }
}
