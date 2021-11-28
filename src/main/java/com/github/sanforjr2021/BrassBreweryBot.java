package com.github.sanforjr2021;

import com.github.sanforjr2021.util.OnBotReady;
import com.github.sanforjr2021.database.dao.DaoController;
import com.github.sanforjr2021.task.TaskController;
import com.github.sanforjr2021.util.ConfigController;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;

import javax.security.auth.login.LoginException;

public class BrassBreweryBot {
    public static final String VERSION = "2.0.4";

    public static final ConfigController CONFIG_CONTROLLER = new ConfigController();
    public static JDA DISCORD_BOT;
    public static Guild GUILD;
    public static TextChannel AUDIT_CHANNEL;
    public static TextChannel COMMAND_CHANNEL;
    public static TextChannel MUSIC_CHANNEL;
    public static TaskController TASK_CONTROLLER;

    public BrassBreweryBot() {
        System.out.println("Startup: Brass Brewery Bot (V" + BrassBreweryBot.VERSION + ") is booting up.");
        prepareDatabaseConnection();
        launchBot();
        DISCORD_BOT.addEventListener(new OnBotReady());

    }

    public static void main(String[] args) {
        new BrassBreweryBot();

    }

    public void prepareDatabaseConnection() {
        System.out.println("Startup: Preparing Database Connection");
        new DaoController(
                CONFIG_CONTROLLER.getProperty("host"),
                CONFIG_CONTROLLER.getProperty("database"),
                CONFIG_CONTROLLER.getProperty("username"),
                CONFIG_CONTROLLER.getProperty("password"));
    }

    public void launchBot() {
        try {
            Long guildId = Long.parseLong(CONFIG_CONTROLLER.getProperty("guildId"));
            DISCORD_BOT = JDABuilder.createDefault(CONFIG_CONTROLLER.getProperty("token"))
                    .setChunkingFilter(ChunkingFilter.include(guildId))
                    .enableIntents(GatewayIntent.getIntents(GatewayIntent.ALL_INTENTS))
                    .build();
            System.out.println("Startup: JDA Launched");
        } catch (LoginException e) {
            System.err.println("Startup: Could not activate bot");
            e.printStackTrace();
            System.out.println("Startup: Shutting Down");
            System.exit(0);
        }

    }
}
