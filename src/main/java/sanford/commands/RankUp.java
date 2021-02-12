package sanford.commands;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Role;
import sanford.data.MemberDataContainer;
import sanford.data.RoleDataContainer;
import sanford.data.SQLServerHandler;
import sanford.util.Util;

import java.sql.SQLException;
import java.util.ArrayList;

public class RankUp extends Command{
    private ArrayList<RoleDataContainer> roles;
    public RankUp(Message msg) {
        super(msg);
    }

    @Override
    public void executeCommand() {
        try {
            RoleDataContainer nextRole = getNextRoleRankID();
            if(nextRole.getId().equals("INVALID")){
                getChannel().sendMessage(getUser().getAsMention() + " You are already at the highest role.").queue();
            }
            else{
                MemberDataContainer member = SQLServerHandler.getMemberDataContainer(getUser().getId());
                if(member.getCurrency() >= nextRole.getCost()){
                    if(Util.addRole(getMember(), nextRole.getId())){
                        int updatedBalance = member.getCurrency() - nextRole.getCost();
                        member.setCurrency(updatedBalance);
                        SQLServerHandler.updateMembersCurrency(member);
                        getChannel().sendMessage(getUser().getAsTag() + " Congratulations! You have ranked up!").queue();
                    }
                    else{
                        getChannel().sendMessage(getUser().getAsTag() + "Sorry, I couldn't rank you up at this time, try again later.").queue();
                    }
                }
                else{
                    getChannel().sendMessage(getUser().getAsMention() + " Sorry, for the next rankUp, you need a total of " + nextRole.getCost() + " points. "
                        + "You only have " + member.getCurrency() + " points.").queue();
                }
            }
        } catch (SQLException throwables) {
            getChannel().sendMessage(getUser().getAsMention() + "Sorry. I cannot rank you up at this time.");
        }
    }

    private RoleDataContainer getNextRoleRankID() throws SQLException {
        roles = SQLServerHandler.getRolesForRankUp();
        int highestTier = 1;
        try{ //if has no roles
            for(Role role : getMember().getRoles()) {
                for (RoleDataContainer roleDataContainer : roles) {
                    if (role.getId().equals(roleDataContainer.getId())) {
                        highestTier++;
                    }
                }
            }
        }catch (NullPointerException e){
            //catch for if roles is empty
            }
        return SQLServerHandler.getRoleByTier(highestTier);
    }
}
