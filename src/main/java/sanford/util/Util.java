package sanford.util;

import net.dv8tion.jda.api.entities.*;
import sanford.BrassBreweryBot;

public class Util
{
    public static Boolean deleteMessage(Message msg)
    {
        try
        {
            msg.delete().queue();
            return true;
        }
        catch(Exception e)
        {
            System.err.println("Failed to delete message in " + msg.getChannel().getName());
            return false;
        }
    }

    public static Boolean addRole(User user ,String roleName)
    {
        try
        {
            Role role = BrassBreweryBot.getGuild().getRolesByName(roleName, true).get(0);
            BrassBreweryBot.getGuild().addRoleToMember(BrassBreweryBot.getGuild().getMember(user), role).queue();
            return true;
        }
        catch(Exception e)
        {
            System.out.println("Failed to apply role " + roleName + " to " + user.getName());
            return false;
        }
    }

    public static void directMessage(User user, String message)
    {
        user.openPrivateChannel().queue((dm) -> //creates event
        {
            dm.sendMessage(message).queue();
        });
    }

    public static Boolean hasRole(User user, String roleName)
    {
        Member member = BrassBreweryBot.getGuild().getMember(user);
        try
        {
            Role role = BrassBreweryBot.getGuild().getRolesByName(roleName, true).get(0);
            if(member.getRoles().contains(role)){
                return true;
            }
        }
        catch(Exception e){
            //when no role is found.
        }
        return false;
    }
}
