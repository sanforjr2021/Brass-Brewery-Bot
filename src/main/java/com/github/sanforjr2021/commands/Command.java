package com.github.sanforjr2021.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;

import java.awt.*;

import static com.github.sanforjr2021.BrassBreweryBot.DISCORD_BOT;
import static com.github.sanforjr2021.BrassBreweryBot.GUILD;

public abstract class Command {
    protected String helpString, name;

    public Command(String name, String helpString) {
        this.helpString = helpString;
        this.name = name;
    }

    public abstract void executeCommand(SlashCommandEvent event);

    public Member getUserByID(String id) {
        return GUILD.getMember(DISCORD_BOT.getUserById(id));
    }

    public MessageEmbed buildEmbeddedMessage(String title, String description, Color color, String thumbnail) {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setColor(color);
        embedBuilder.setTitle(title);
        embedBuilder.setDescription(description);
        embedBuilder.setThumbnail(thumbnail);
        return embedBuilder.build();
    }

    public abstract CommandData getCommandData();

    public MessageEmbed buildEmbeddedMessage(String title, String description, Color color) {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setColor(color);
        embedBuilder.setTitle(title);
        embedBuilder.setDescription(description);
        return embedBuilder.build();
    }

    public MessageEmbed buildEmbeddedMessage(String title, String description, Color color, String thumbnail, String footer) {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setColor(color);
        embedBuilder.setTitle(title);
        embedBuilder.setDescription(description);
        embedBuilder.setThumbnail(thumbnail);
        embedBuilder.setFooter(footer);
        return embedBuilder.build();
    }

    public void addReaction(Message message, String reactionID) {
        message.addReaction(reactionID).queue();
    }

    public String getName() {
        return name;
    }

    public void invalidPermissions(SlashCommandEvent event) {
        event.reply("You do not have permission to use this command.");
    }
}
