package com.github.sanforjr2021.util;

import com.github.sanforjr2021.BrassBreweryBot;
import com.github.sanforjr2021.commands.SlashCommandHandler;
import com.github.sanforjr2021.task.TaskController;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

import static com.github.sanforjr2021.BrassBreweryBot.*;

public class OnBotReady implements EventListener {

    @Override
    public void onEvent(@NotNull GenericEvent genericEvent) {
        if(genericEvent instanceof ReadyEvent) {
            GUILD = DISCORD_BOT.getGuildById(CONFIG_CONTROLLER.getProperty("guildId"));
            AUDIT_CHANNEL = DISCORD_BOT.getTextChannelById(CONFIG_CONTROLLER.getProperty("auditChannelId"));
            COMMAND_CHANNEL = DISCORD_BOT.getTextChannelById(CONFIG_CONTROLLER.getProperty("commandChannelId"));
            MUSIC_CHANNEL = DISCORD_BOT.getTextChannelById(CONFIG_CONTROLLER.getProperty("musicChannelId"));
            DISCORD_BOT.addEventListener(new SlashCommandHandler());
            AUDIT_CHANNEL.sendMessageEmbeds(buildLaunchString()).queue();
            DISCORD_BOT.addEventListener(new MessageHandler());
            TASK_CONTROLLER = new TaskController();
            System.out.println("Startup Complete");
        }
    }
    private static MessageEmbed buildLaunchString(){
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setColor(Color.green);
        embedBuilder.setTitle("Booting Up");
        embedBuilder.setDescription("Brass Brewery Bot is starting up.");
        embedBuilder.setFooter("Version " + VERSION);
        embedBuilder.setThumbnail(GUILD.getIconUrl());
        return embedBuilder.build();
    }
}
