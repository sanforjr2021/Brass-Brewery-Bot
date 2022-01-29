package com.github.sanforjr2021.commands;

import com.github.sanforjr2021.database.domain.Rank;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.components.Button;

import java.awt.*;
import java.sql.SQLException;

import static com.github.sanforjr2021.BrassBreweryBot.GUILD;
import static com.github.sanforjr2021.service.CurrencyService.getPoints;
import static com.github.sanforjr2021.service.RoleService.*;
import static com.github.sanforjr2021.util.RandomMessageGenerator.getNegativeMessage;
import static com.github.sanforjr2021.util.RandomMessageGenerator.getPositiveMessage;

public class Rankup extends Command {
    public Rankup() {
        super("rankup", "ranks the user up to the next valid role if having enough gold");
    }

    @Override
    public void executeCommand(SlashCommandEvent event) {
        Rank rank;
        User user = event.getUser();
        int points;
        try {
            rank = getNextRank(user);
            points = getPoints(user);
        } catch (Exception throwables) {
            System.out.println(throwables.getMessage());
            points = -1;
            rank = null;

            throwables.printStackTrace();
        }
        MessageEmbed message = generateEmbed(event.getMember(), points, rank);
        if (points != -1 && rank != null && points >= rank.getCost()) {
            event.replyEmbeds(message).addActionRow(
                    Button.primary("rankupButton", "Add " + rank.getName())
            ).queue();
        } else {
            event.replyEmbeds(message).queue();
        }
    }

    public void reactButton(ButtonClickEvent event) {
        User user = event.getUser();
        String mention = event.getMember().getAsMention();
        String roleName;
        try {
            Rank rank = getNextRank(user);
            roleName = GUILD.getRoleById(rank.getId()).getName();
            if (!userHaveRole(user, rank.getId())) {
                if (addRank(user, rank)) {
                    event.replyEmbeds(buildEmbeddedMessage(
                            "Success: Added Rank" + roleName,
                            mention + " has had the rank " + roleName + " added.",
                            Color.green,
                            event.getUser().getAvatarUrl(),
                            getPositiveMessage())
                    ).queue();
                } else {
                    event.replyEmbeds(buildEmbeddedMessage(
                            "Oops: Could not afford" + roleName,
                            mention + " does not have enough gold for the rank " + roleName + ".",
                            Color.RED,
                            event.getUser().getAvatarUrl(),
                            getNegativeMessage())
                    ).queue();
                }
            }
        } catch (SQLException | NullPointerException throwables) {
            event.replyEmbeds(buildEmbeddedMessage(
                    "Oops: Error",
                    "I Could not add rank.",
                    Color.RED,
                    event.getUser().getAvatarUrl())
            ).queue();
            throwables.printStackTrace();
        }
    }

    private MessageEmbed generateEmbed(Member member, int points, Rank rank) {
        String nameEmbed, descriptionEmbed;
        String user = member.getAsMention();
        String profileIcon = member.getUser().getAvatarUrl();
        Color color = Color.red;
        if (points == -1) {
            nameEmbed = "Rankup: Error";
            descriptionEmbed = "Sorry, I could not determine your next rank at this time.";
        } else if (rank == null) {
            color = Color.orange;
            nameEmbed = "Rankup:";
            descriptionEmbed = user + "is at the highest rank available at this time.";
        } else {
            String rankName = GUILD.getRoleById(rank.getId()).getName();
            nameEmbed = "RankUp: " + rankName;
            if (points >= rank.getCost()) {
                color = Color.green;
                descriptionEmbed = user + " can receive the rank of ***" + rankName + "***.\n" +
                        "It will cost a total of ***" + rank.getCost() + " gold***." +
                        "\nYou currently have a total of ***" + points + "gold***. Would you like to rankup?";
            } else {
                int pointDiff = rank.getCost() - points;
                descriptionEmbed = "Sorry " + user + ", you cannot afford the rank ***" + rankName + "*** currently." +
                        " \nYou currently have ***" + points + "*** out of ***" + rank.getCost() + "***." + "\nYou need ***" + pointDiff + "*** more points";
            }
        }
        return buildEmbeddedMessage(nameEmbed, descriptionEmbed, color, profileIcon);
    }

    @Override
    public CommandData getCommandData() {
        return new CommandData(name, helpString);
    }
}

