package com.github.sanforjr2021.commands;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.sql.SQLException;

import static com.github.sanforjr2021.BrassBreweryBot.GUILD;
import static com.github.sanforjr2021.service.CurrencyService.getPoints;

public class Points extends Command {

    public Points() {
        super("points", "Returns the number of points you have.");
    }

    @Override
    public void executeCommand(SlashCommandEvent event) {
        User user;
        OptionMapping mapping = event.getOption("user");
        try {
            user = mapping.getAsUser();
            event.reply(getOtherPoints(user)).queue();
        } catch (NullPointerException e) {
             user = event.getUser();
            event.reply(getSelfPoints(user)).queue();
        }
    }

    private String getSelfPoints(User user) {
        try {
            return "You have " + getPoints(user) + " points.";
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return "Could not get your points at this time.";
        }
    }

    private String getOtherPoints(User user) {
        Member member = GUILD.getMember(user);
        try {
            return member.getEffectiveName() + " has " + getPoints(user) + " points.";
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return "Could not get " + member.getEffectiveName() + "'s points at this time.";
        }
    }

    @Override
    public CommandData getCommandData() {
        OptionData data = new OptionData(OptionType.USER, "user", "Who's points you will see.").setRequired(false);
        return new CommandData(name, helpString).addOptions(data);

    }
}
