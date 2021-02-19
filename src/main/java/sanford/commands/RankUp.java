package sanford.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Role;
import sanford.BrassBreweryBot;
import sanford.data.MemberDataContainer;
import sanford.data.RoleDataContainer;
import sanford.data.SQLServerHandler;
import sanford.util.Util;

import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class RankUp extends Command {
    private ArrayList<RoleDataContainer> roles;
    private RoleDataContainer nextRole;
    private String output;
    private boolean addedRole = false;
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
        attemptAddingRole();
        EmbedBuilder embedBuilder = new EmbedBuilder();
        if(addedRole){
            embedBuilder.setColor(Color.green);
            embedBuilder.setTitle("Congratulations " + user.getName() + "!");
        }
        else{
            embedBuilder.setColor(Color.red);
            embedBuilder.setTitle("Sorry  " + user.getName() + "!");
        }
        embedBuilder.setDescription(mention + " " + output);
        embedBuilder.setThumbnail(user.getAvatarUrl());
        sendMessage(embedBuilder);
    }

    private RoleDataContainer getNextRoleRankID() throws SQLException {
        roles = SQLServerHandler.getRolesForRankUp();
        int highestTier = 1;
        try { //if has no roles
            for (Role role : member.getRoles()) {
                for (RoleDataContainer roleDataContainer : roles) {
                    if (role.getId().equals(roleDataContainer.getId())) {
                        highestTier++;
                    }
                }
            }
        } catch (NullPointerException e) {
            //catch for if roles is empty
        }
        return SQLServerHandler.getRoleByTier(highestTier);
    }
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
}
