package com.stevenjtk.trivia.jda;

import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;

public interface Listener {
    void onReady(ReadyEvent event);
}
