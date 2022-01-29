package com.github.sanforjr2021.commands;

import com.github.sanforjr2021.database.domain.BuyableRole;
import com.github.sanforjr2021.service.RoleService;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.components.Button;

import java.awt.*;
import java.util.ArrayList;

import static com.github.sanforjr2021.BrassBreweryBot.GUILD;
import static com.github.sanforjr2021.service.RoleService.addBuyableRole;
import static com.github.sanforjr2021.util.RandomMessageGenerator.getNegativeMessage;
import static com.github.sanforjr2021.util.RandomMessageGenerator.getPositiveMessage;

public class Buy extends Command {
    private ArrayList<BuyableRole> buyableRoles = null;

    public Buy() {
        super("buy", "Allows buying custom ranks for 1 month at a time");
        while (buyableRoles == null) { //on chance of DB error
            buyableRoles = RoleService.getAllBuyableRoles();
            System.out.println("Attempting to grab roles Buyable roles from DB");
        }
        System.out.println("Roles have been received from DB.");
    }


    @Override
    public void executeCommand(SlashCommandEvent event) {
        String name = event.getOption("color").getAsString();
        BuyableRole buyableRole = findRoleByName(name);
        event.replyEmbeds(generateEmbed(event.getMember(), buyableRole))
                .addActionRow(Button.primary("buy" + name, "Buy Color " + buyableRole.getName()))
                .queue();
    }

    @Override
    public CommandData getCommandData() {
        OptionData data = new OptionData(OptionType.STRING, "color", "The color you want to view before buying.").setRequired(true);
        for (int i = 0; i < buyableRoles.size(); i++) {
            data.addChoice(buyableRoles.get(i).getName(), buyableRoles.get(i).getName());
        }
        return new CommandData(name, helpString).addOptions(data);
    }


    public void reactButton(ButtonClickEvent event) {
        User user = event.getUser();
        BuyableRole role = findRoleByName(event.getButton().getId().split("buy")[1]);
        int success = addBuyableRole(user, role);
        if (success == 0) { //fail
            event.replyEmbeds(
                    new EmbedBuilder()
                            .setTitle("Oops")
                            .setDescription(user.getAsMention() + " cannot afford role " + role.getName() + " for " + role.getCost() + " gold.")
                            .setColor(Color.red)
                            .setThumbnail(user.getAvatarUrl())
                            .setFooter(getPositiveMessage())
                            .build())
                    .queue();
        } else if (success == 1) { //success
            event.replyEmbeds(
                    new EmbedBuilder()
                            .setTitle("Success: Role Added")
                            .setDescription(user.getAsMention() + " purchased role " + role.getName() + " for " + role.getCost() + " gold.")
                            .setColor(GUILD.getRoleById(role.getId()).getColor())
                            .setThumbnail(user.getAvatarUrl())
                            .setFooter(getNegativeMessage())
                            .build())
                    .queue();
        } else if(success == 3){ //DB Error
            event.replyEmbeds(
                    new EmbedBuilder()
                            .setTitle("Oops")
                            .setDescription("Role could not be purchased due to an error with the database. Try again later or contact a bouncer")
                            .setColor(Color.red)
                            .setThumbnail(user.getAvatarUrl())
                            .setFooter(getNegativeMessage())
                            .build())
                    .queue();
        } else if(success == 4){ //DB Error
            event.replyEmbeds(
                    new EmbedBuilder()
                            .setTitle("Oops")
                            .setDescription(  user.getAsMention() + " already has this role.")
                            .setColor(Color.red)
                            .setThumbnail(user.getAvatarUrl())
                            .setFooter(getNegativeMessage())
                            .build())
                    .queue();
        }

    }

    private MessageEmbed generateEmbed(Member member, BuyableRole buyableRole) {
        String title = "Buy Role: " + buyableRole.getName();
        String description = "The buyable role " + buyableRole.getName() + " cost " + buyableRole.getCost() + " gold for 1 month. " +
                "\nAfter 1 month the role will be removed and will need to be purchased again.";
        String footer = "Please note buying multiple roles currently will not remove the old one.";
        Role role = GUILD.getRoleById(buyableRole.getId());
        Color color = role.getColor();
        return new EmbedBuilder()
                .setTitle(title)
                .setDescription(description)
                .setColor(color)
                .setFooter(footer)
                .build();
    }

    private BuyableRole findRoleByName(String name) {
        for (int i = 0; i < buyableRoles.size(); i++) {
            if (buyableRoles.get(i).getName().equals(name)) {
                return buyableRoles.get(i);
            }
        }
        return null;
    }
}
