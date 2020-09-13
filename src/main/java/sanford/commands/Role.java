package sanford.commands;


import net.dv8tion.jda.api.entities.*;
import sanford.BrassBreweryBot;
import sanford.util.Util;

public class Role {
    private Member user;
    private String args[];
    private Message msg;

    public Role(Message messsage) {
        msg = messsage;
        user = msg.getMember();
        args = msg.getContentRaw().toLowerCase().split(" ");
        if (args.length < 2) {
            invalidRole();
        } else {
            //TODO: Use database to generate needed roles and role names
            switch (args[1]) {
                case "tenno":
                case "warframe":
                    addRoleToUser("748724194698592336"); //tenno rank
                    msg.addReaction(":warframe:680293591524048902").queue(); //custom warframe emoji
                    break;
                case "merc":
                case "tf2":
                case "mann-co":
                    addRoleToUser("748724231646216235"); //tf2
                    msg.addReaction(":tf2:680292750826143772").queue(); //custom tf2 emoji
                    break;
                default:
                    invalidRole();
                    break;
            }
        }
    }

    private void invalidRole() {
        Util.directMessage(user.getUser(), "You did not specify a valid role, please type a valid role.\n\nHere is a list of roles:" +
                "\n'Warframe' - allows access to warframe related channels" +
                "\n'TF2' - allows access to Team Fortress 2 channels");
        msg.addReaction("U+274C").queue(); //Red X
    }

    private void addRoleToUser(String roleID) {
        if (Util.hasRole(user, roleID)) {
            Util.directMessage(user.getUser(), "I could not add the role as you already have it.");
        } else {
            if (Util.addRole(user, roleID)) {
                Util.directMessage(user.getUser(), "I added the role to you in the " + BrassBreweryBot.getGuild().getName());
            } else {
                Util.directMessage(user.getUser(), "I failed to add the role to you in the "
                        + BrassBreweryBot.getGuild().getName() + "\nIf you believe this is an error and continue to have problems," +
                        " please contact an administrator");
                msg.addReaction("U+274C").queue(); //Red X
            }
        }
    }

}
