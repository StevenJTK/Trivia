package com.stevenjtk.trivia.jda;

import net.dv8tion.jda.api.events.GenericEvent;

public interface EventListener {
    void onEvent(GenericEvent event);
}
