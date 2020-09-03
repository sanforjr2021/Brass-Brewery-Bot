package sanford.commands;


import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import sanford.BrassBreweryBot;
import sanford.util.Util;

public class CommandRole {
    private User user;
    private String args[];
    private Message msg;

    public CommandRole(Message messsage){
        msg = messsage;
        user = msg.getAuthor();
        args = msg.getContentRaw().split(" ");
        if(args.length < 2)
        {
            invalidRole();
        }
        else
        {
            switch(args[1]){
                case "tenno":
                    addRoleToUser();
                    msg.addReaction(":warframe:680293591524048902").queue(); //custom tf2 emoji
                    break;
                case "tf2":
                    addRoleToUser();
                    msg.addReaction(":tf2:680292750826143772").queue(); //custom tf2 emoji
                    break;
                default:
                    invalidRole();
                    break;
            }
        }
    }

    private void invalidRole(){
        Util.directMessage(user, "You did not specify a valid role, please type a valid role.\n\nHere is a list of roles:" +
                "\n'Tenno' - allows access to warframe related channels" +
                "\n'TF2' - allows access to Team Fortress 2 channels");
        msg.addReaction("U+274C").queue(); //Red X
    }
    private void addRoleToUser(){
        if(Util.addRole(user, args[1])) {
            Util.directMessage(user, "Added the role @" + args[1] + " to you in the " + BrassBreweryBot.getGuild().getName());
        }
        else
        {
            Util.directMessage(user, "Failed to add role @" + args[1] + " to you in the " + BrassBreweryBot.getGuild().getName());
            msg.addReaction("U+274C").queue(); //Red X
        }
    }
}
