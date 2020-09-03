package sanford.util;

import net.dv8tion.jda.api.entities.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sanford.BrassBreweryBot;

public class Util
{
    private static final Logger logger = LoggerFactory.getLogger("BBBLog");
    public static Boolean deleteMessage(Message msg)
    {
        try
        {
            msg.delete().queue();
            logInfo("Deleted Message in " + msg.getChannel().getName());
            return true;
        }
        catch(Exception e)
        {
            logError("Failed to delete message in " + msg.getChannel().getName());
            return false;
        }
    }

    public static Boolean addRole(Member user ,String roleID)
    {
        if(hasRole(user, roleID))
        {
            logInfo("Cannot add role with ID  " + roleID + " to " + user.getNickname() +
                    " because they already have the role.");
            return false;
        }
        else
        {
            try
            {
                Role role = BrassBreweryBot.getGuild().getRoleById(roleID);
                BrassBreweryBot.getGuild().addRoleToMember(user, role).queue();
                logInfo("Added role " + roleID + " to " + user.getNickname());
                return true;
            }
            catch(Exception e)
            {
                logError("Failed to apply role with ID " + roleID + " to " + user.getNickname());
                e.printStackTrace();
                return false;
            }
        }
    }

    public static void directMessage(User user, String message)
    {
        user.openPrivateChannel().queue((dm) -> //creates event
        {
            dm.sendMessage(message).queue();
            dm.close();
        });
    }

    public static Boolean hasRole(Member user, String roleID)
    {
        try
        {
            Role role = BrassBreweryBot.getGuild().getRoleById(roleID);
            if(user.getRoles().contains(role)){
                return true;
            }
        } catch (NullPointerException e) {
            logError("Role with ID " + roleID + " does not exist.");
        }
        return false;
    }

    public static void logInfo(String info)
    {
        logger.info(info);
    }
    public static void logError(String info){
        logger.error(info);
    }
}
