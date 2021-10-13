package com.github.sanforjr2021.bot.command;

import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;

import java.net.InetAddress;
import java.net.UnknownHostException;

import static com.github.sanforjr2021.BrassBreweryBot.GUILD;

public class Ping extends Command {
    public Ping() {
        super("ping", "Pings the bot and receives a reply about the latency and host.");
        System.out.println(GUILD.getName());
    }

    @Override
    public void executeCommand(SlashCommandEvent event) {
        //code from https://discord.com/api/oauth2/authorize?client_id=772624391720534016&permissions=8&scope=bot
        long time = System.currentTimeMillis();
        event.reply("Pong!") // reply or acknowledge
                .flatMap(v ->
                        event.getHook().editOriginalFormat("Pong: %d ms \n Host: %s", System.currentTimeMillis() - time, getHostName()) // then edit original
                ).queue(); // Queue both reply and edit;
    }

    @Override
    public CommandData getCommandData() {
        return new CommandData(name, helpString);
    }

    private String getHostName() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return "Unknown Host";

        }
    }
}

