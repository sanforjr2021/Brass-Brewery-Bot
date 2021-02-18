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
        if (argument.equals("INVALID") || argument.equals("help")) {
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
            if (roleDataContainer.getTier() == -1)
                solution.append(roleDataContainer.shopString()).append("\n");
        }
        return solution.toString() + "```";
    }

    private void helpArgument() {
        try {
            String helpString = mention + "The !role command is used to add roles to a user. Type !role <roleName> " +
                    "to add it to your account. Here's a list of roles.\n" + generateListOfRoles();
            sendMessage(helpString);
        } catch (SQLException throwables) {
            logError("Failed to receive list of roles from the database.");
            throwables.printStackTrace();
        }
        addReaction(" U+2753"); //? Emoji
    }

    private void invalidArgument() {
        try {
            String badArgumentString = mention + "You failed to specify a specific role," +
                    " here's a list of roles.\n" + generateListOfRoles();
            sendMessage(badArgumentString);
        } catch (SQLException throwables) {
            logError("Failed to receive list of roles from the database.");
            throwables.printStackTrace();
        }
        addReaction("U+274C"); //Red X Emoji
    }

    private void addRoleToUser(String roleID) {
        if (Util.hasRole(member, roleID)) {
            if (Util.removeRole(member, roleID)) {
                sendMessage(mention + "I removed the role from you.");
                addReaction("U+2705"); //Green Checkmark Emoji
            } else {
                sendMessage(mention + " I failed to remove the role to you. Please contact " +
                        "a staff member if this continues to occur.");
                addReaction("U+274C");//Red X emoji
            }
        } else {
            if (Util.addRole(member, roleID)) {
                sendMessage(mention + " You have been added the role.");
                addReaction("U+2705"); //Green Checkmark Emoji
            } else {
                sendMessage(mention + " I failed to remove the role to you. Please contact " +
                        "a staff member if this continues to occur.");
                addReaction("U+274C"); //Red X emoji
            }
        }
    }


}
