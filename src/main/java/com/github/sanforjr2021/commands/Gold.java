package com.github.sanforjr2021.commands;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.awt.*;
import java.sql.SQLException;

import static com.github.sanforjr2021.BrassBreweryBot.GUILD;
import static com.github.sanforjr2021.service.CurrencyService.getPoints;

import static com.github.sanforjr2021.util.RandomMessageGenerator.getPositiveMessage;

public class Gold extends Command {

    public Gold() {
        super("gold", "Returns the amount of gold you have.");
    }

    @Override
    public void executeCommand(SlashCommandEvent event) {
        User user;
        OptionMapping mapping = event.getOption("user");
        try {
            user = mapping.getAsUser();
            event.replyEmbeds(buildEmbeddedMessage(user.getName() + "'s gold", getOtherPoints(user), Color.orange, user.getAvatarUrl(), getPositiveMessage())).queue();
        } catch (NullPointerException e) {
            user = event.getUser();
            event.replyEmbeds(buildEmbeddedMessage(user.getName() + "'s gold", getSelfPoints(user), Color.orange, user.getAvatarUrl(), getPositiveMessage())).queue();
        }
    }

    private String getSelfPoints(User user) {
        try {
            return "You have " + getPoints(user) + " gold.";
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return "Could not get your gold at this time.";
        }
    }

    private String getOtherPoints(User user) {
        Member member = GUILD.getMember(user);
        try {
            return member.getEffectiveName() + " has " + getPoints(user) + " gold.";
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return "Could not get " + member.getEffectiveName() + "'s points at this time.";
        }
    }

    @Override
    public CommandData getCommandData() {
        OptionData data = new OptionData(OptionType.USER, "user", "See how much gold someone else has./").setRequired(false);
        return new CommandData(name, helpString).addOptions(data);

    }
}
