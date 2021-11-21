package com.github.sanforjr2021.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

import static com.github.sanforjr2021.BrassBreweryBot.*;

public class SlashCommandHandler extends ListenerAdapter {
    private final Ping ping = new Ping();
    private final Points points = new Points();
    private final Rankup rankup = new Rankup();
    private final CommandListUpdateAction commands;

    public SlashCommandHandler() {
        commands = GUILD.updateCommands();
        commands.addCommands(ping.getCommandData());
        commands.addCommands(points.getCommandData());
        commands.addCommands(rankup.getCommandData());
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
            case "rankup":
                rankup.executeCommand(event);
                break;
            default:
                invalidChannel(event, MUSIC_CHANNEL);
                break;
        }
    }

    public void onButtonClick(ButtonClickEvent event) {
        switch (event.getButton().getId()){
            case "rankupButton":
                rankup.reactButton(event);
        }
    }

    public void invalidChannel(SlashCommandEvent event, TextChannel channel) {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setColor(Color.red);
        embedBuilder.setTitle("Invalid Channel");
        embedBuilder.setDescription(event.getMember().getAsMention() + " cannot use this message in this channel. Please try channel" + channel.getAsMention());
        MessageEmbed message = embedBuilder.build();
        event.replyEmbeds(message).queue();

    }
}
