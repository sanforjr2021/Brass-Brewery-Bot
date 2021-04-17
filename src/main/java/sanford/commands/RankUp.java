package sanford.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Role;
import sanford.data.MemberDataContainer;
import sanford.data.RankDataContainer;
import sanford.data.SQLServerHandler;
import sanford.util.Util;

import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static sanford.data.SQLServerHandler.getRanks;
import static sanford.data.SQLServerHandler.updateMembersCurrency;

public class RankUp extends Command {
    public RankUp(Message msg) {
        super(msg);
    }

    public static String getHelpString() {
        String helpString = "`!RankUp`";
        helpString += " - *Will rank you up to the next valid rank if you have enough points.*";
        return helpString;
    }

    @Override
    public void executeCommand() {
        RankDataContainer nextRank = findNextRank();
        MemberDataContainer user;
        if (nextRank == null){ //if can't get rank
            return;
        }
        try {
            user = SQLServerHandler.getMemberDataContainer(member.getId());
        } catch (SQLException throwables) {
            buildErrorEmbedded();
            return;
        }
        if(user.getCurrency() >= nextRank.getCost()){
            try {
                if(Util.addRole(member, nextRank.getId())){
                    updateMembersCurrency(user);
                    user.setCurrency(user.getCurrency()-nextRank.getCost());
                    sendMessage(
                            buildEmbeddedMessage(
                                    true,
                                    " received the rank ***" + nextRank.getName() + "*** at " +
                            "the cost of ***" + nextRank.getCost() + ".***"
                            ));
                }
                else{
                    buildErrorEmbedded();
                }
            } catch (SQLException throwables) {
                buildErrorEmbedded();
            }
        }
        else{
            int moneyNeeded = nextRank.getCost() - user.getCurrency();
            sendMessage(
                    buildEmbeddedMessage(
                            false,
                            " could not afford the rank ***" + nextRank.getName() +
                    "***. You still need ***" + moneyNeeded + " points***, to have the total cost of ***"
                    + nextRank.getCost() + " points***."
                    ));
        }
    }

    private EmbedBuilder buildEmbeddedMessage(boolean addedRole, String output) {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        if (addedRole) {
            embedBuilder.setColor(Color.green);
            embedBuilder.setTitle("Congratulations " + user.getName() + "!");
        } else {
            embedBuilder.setColor(Color.red);
            embedBuilder.setTitle("Sorry  " + user.getName() + "!");
        }
        embedBuilder.setDescription(mention + " " + output);
        embedBuilder.setThumbnail(user.getAvatarUrl());
        return embedBuilder;
    }

    private RankDataContainer findNextRank() {
        try {
            ArrayList<RankDataContainer> ranks = getRanks();
            List<Role> rolesList = member.getRoles();
            int rankIndex = 0;
            for (RankDataContainer container : ranks) {
                for (Role role : rolesList) {
                    if (role.getId().equals(container.getId())) {
                        rankIndex++;
                    }
                }
            }

            return ranks.get(rankIndex);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            buildErrorEmbedded();
            return null;
        }
    }
    /*
    private void attemptAddingRole(){
        try {
            nextRole = getNextRoleRankID();
            if (nextRole.getId().equals("INVALID")) {
                output = "You cannot rank up, already the highest role";
            } else {
                String roleName = BrassBreweryBot.getGuild().getRoleById(nextRole.getId()).getName();
                MemberDataContainer member = SQLServerHandler.getMemberDataContainer(user.getId());
                if (member.getCurrency() >= nextRole.getCost()) {
                    if (Util.addRole(this.member, nextRole.getId())) {
                        int updatedBalance = member.getCurrency() - nextRole.getCost();
                        member.setCurrency(updatedBalance);
                        SQLServerHandler.updateMembersCurrency(member);
                        addedRole = true;
                        output= "You have ranked up to role ***" + roleName + "*** for the cost of " +
                                "**" + nextRole.getCost() + "** points.";
                        System.out.println("Added Role");
                    } else {
                        output = "I couldn't rank you up at this time, try again later.";
                    }
                } else {
                    output = "To rank up to ***" + roleName + "***, you need a total of " + nextRole.getCost() + " points. "
                            + "You still need **" + (nextRole.getCost()-member.getCurrency()) + "** points.";
                }
            }
        } catch (SQLException | NullPointerException  exception) {
            output = "I cannot rank you up at this time.";
        }
    }
     */
}
