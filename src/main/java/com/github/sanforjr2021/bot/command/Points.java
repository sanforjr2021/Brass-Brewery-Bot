package com.github.sanforjr2021.bot.command;

import com.github.sanforjr2021.dao.GuildMemberDao;
import com.github.sanforjr2021.domain.GuildMember;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.sql.SQLException;

import static com.github.sanforjr2021.BrassBreweryBot.GUILD;

public class Points extends Command {

    public Points() {
        super("points", "Returns the number of points you have.");
    }

    @Override
    public void executeCommand(SlashCommandEvent event) {
        Member member;
        OptionMapping mapping = event.getOption("user");
        try {
            member = GUILD.getMember(mapping.getAsUser());
            event.reply(getOtherPoints(member)).queue();
        } catch (NullPointerException e) {
            member = event.getMember();
            event.reply(getSelfPoints(member)).queue();
        }
    }

    private String getSelfPoints(Member member) {
        try {
            GuildMember guildMember = GuildMemberDao.get(member.getId());
            return "You have " + guildMember.getCurrency() + " points.";
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return "Could not get your points at this time.";
        }
    }

    private String getOtherPoints(Member member) {
        try {
            GuildMember guildMember = GuildMemberDao.get(member.getUser().getId());
            return member.getEffectiveName() + " has " + guildMember.getCurrency() + " points.";
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
