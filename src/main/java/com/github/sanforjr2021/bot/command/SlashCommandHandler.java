package com.github.sanforjr2021.bot.command;

import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;
import org.jetbrains.annotations.NotNull;

import static com.github.sanforjr2021.BrassBreweryBot.*;

public class SlashCommandHandler extends ListenerAdapter {
    private final Ping ping = new Ping();
    private final Points points = new Points();
    private final CommandListUpdateAction commands;

    public SlashCommandHandler() {
        commands = GUILD.updateCommands();
        commands.addCommands(ping.getCommandData());
        commands.addCommands(points.getCommandData());
        commands.queue();
        System.out.println("Commands Registered");
    }

    @Override
    public void onSlashCommand(@NotNull SlashCommandEvent event) {
        Runnable runnable = () -> {
            TextChannel channel = event.getTextChannel(); //determine what type of command is being used
            if (channel == MUSIC_CHANNEL) {
                musicCommands(event);
            } else if (channel == COMMAND_CHANNEL) {
                botCommands(event);
            }
            universalCommand(event);
        };
        runnable.run();
    }

    public void universalCommand(SlashCommandEvent event) {
        switch (event.getName()) {
            case "ping":
                ping.executeCommand(event);
                break;
        }
    }

    public void musicCommands(SlashCommandEvent event) {

    }

    public void botCommands(SlashCommandEvent event) {
        switch (event.getName()) {
            case "points":
                points.executeCommand(event);
                break;
        }
    }

}
