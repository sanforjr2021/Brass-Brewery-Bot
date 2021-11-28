package com.github.sanforjr2021.service;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.exceptions.ContextException;
import net.dv8tion.jda.api.exceptions.ErrorResponseException;

import static com.github.sanforjr2021.BrassBreweryBot.AUDIT_CHANNEL;

public class MessageService {
    /**
     * Sends a message to the audit log.
     * @param message
     */
    public static void sendAuditLog(String message){
        AUDIT_CHANNEL.sendMessage(message).queue();
    }

    /**
     * Sends the user a private message
     * @param user
     * @param message
     */
    public static boolean sendPrivateMessage(User user, String message){
        try{
            user.openPrivateChannel().queue((channel) ->
            {
                channel.sendMessage(message).queue();

            });
            return true;
        } catch (ErrorResponseException e){
            sendAuditLog("Could not message " + user.getAsMention() + " message \"" + message + "\"");
            return false;
        }
    }
}
