package com.github.sanforjr2021.bot;

import com.github.sanforjr2021.BrassBreweryBot;
import net.dv8tion.jda.api.events.Event;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import org.jetbrains.annotations.NotNull;

import static com.github.sanforjr2021.BrassBreweryBot.*;

public class OnBotReady implements EventListener {

    @Override
    public void onEvent(@NotNull GenericEvent genericEvent) {
        if(genericEvent instanceof ReadyEvent) {
            BrassBreweryBot.GUILD = DISCORD_BOT.getGuildById(CONFIG_CONTROLLER.getProperty("guildId"));
            BrassBreweryBot.AUDIT_CHANNEL = DISCORD_BOT.getTextChannelById(CONFIG_CONTROLLER.getProperty("auditChannelId"));
            BrassBreweryBot.COMMAND_CHANNEL = DISCORD_BOT.getTextChannelById(CONFIG_CONTROLLER.getProperty("commandChannelId"));
            BrassBreweryBot.MUSIC_CHANNEL = DISCORD_BOT.getTextChannelById(CONFIG_CONTROLLER.getProperty("musicChannelId"));
            System.out.println("Startup Complete");
            AUDIT_CHANNEL.sendMessage("Brass Brewery Bot (V" + BrassBreweryBot.VERSION + ") is started.").queue();
        }
    }
}
