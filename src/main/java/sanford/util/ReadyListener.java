package sanford.util;

import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import sanford.BrassBreweryBot;

import javax.annotation.Nonnull;

public class ReadyListener implements EventListener {
    @Override
    public void onEvent(@Nonnull GenericEvent event) {
        if (event instanceof ReadyEvent) {
            BrassBreweryBot.onStartupComplete();
        }
    }
}