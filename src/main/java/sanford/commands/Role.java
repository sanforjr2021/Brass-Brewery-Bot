package sanford.commands;


import net.dv8tion.jda.api.entities.Message;
import sanford.data.RoleDataContainer;
import sanford.data.SQLServerHandler;
import sanford.util.Util;

import java.sql.SQLException;
import java.util.ArrayList;

public class Role extends Command {

    public Role(Message msg) {
        super(msg);
    }

    @Override
    public void executeCommand() {
        String argument = getArguments(1).trim();
        if (argument == "INVALID" || argument.equals("help")) {
            helpArgument();
        } else {
            try {
                String id = SQLServerHandler.getRoleIDbyRoleName(argument);
                if (id.equals("INVALID")) {
                    invalidArgument();
                } else {
                    addRoleToUser(id);
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    private String generateListOfRoles() throws SQLException {
        ArrayList<RoleDataContainer> roleDataContainersList = SQLServerHandler.getRoles();
        StringBuilder solution = new StringBuilder();
        solution.append("**Server Roles**\n```");
        for (RoleDataContainer roleDataContainer : roleDataContainersList) {
            if(roleDataContainer.getTier() == -1)
                solution.append(roleDataContainer.shopString() + "\n");
            }
        return solution.toString() + "```";
    }

    private void helpArgument() {
        try {
            getChannel().sendMessage(new StringBuilder()
                    .append(getUser().getAsMention())
                    .append("The !role command is used to add roles to a user.")
                    .append(" Type !role <roleName> to add it to your account. Here's a list of roles.\n")
                    .append(generateListOfRoles()).toString())
                    .queue();
        } catch (SQLException throwables) {
            logError("Failed to receive list of roles from the database.");
            throwables.printStackTrace();
        }
        getMessage().addReaction(" U+2753").queue(); //? Emoji
    }

    private void invalidArgument() {
        try {
            getChannel().sendMessage(new StringBuilder()
                    .append(getUser().getAsMention())
                    .append("You failed to specify a specific role,")
                    .append(" here's a list of roles.\n")
                    .append(generateListOfRoles()).toString())
                    .queue();
        } catch (SQLException throwables) {
            logError("Failed to receive list of roles from the database.");
            throwables.printStackTrace();
        }
        getMessage().addReaction("U+274C").queue(); //Red X Emoji
    }
    private void sendMessageWithReaction(String message, String reaction){
        getChannel().sendMessage(message).queue();
        getMessage().addReaction(reaction).queue(); //Check Mark
    }
    private void addRoleToUser(String roleID) {
        if (Util.hasRole(getMember(), roleID)) {
            if (Util.removeRole(getMember(), roleID)) {
                sendMessageWithReaction(getUser().getAsMention() + "I removed the role from you.","U+2705" );
            } else {
                sendMessageWithReaction(getUser().getAsMention() + " I failed to remove the role to you. Please contact " +
                        "a staff member if this continues to occur.","U+274C");
            }
        } else {
            if (Util.addRole(getMember(), roleID)) {
                sendMessageWithReaction(getUser().getAsMention() + " You have been added the role.","U+2705");
            } else {
                sendMessageWithReaction(getUser().getAsMention() + " I failed to remove the role to you. Please contact " +
                        "a staff member if this continues to occur.","U+274C");
            }
        }
    }


}
