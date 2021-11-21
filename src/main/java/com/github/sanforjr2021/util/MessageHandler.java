package com.github.sanforjr2021.util;

import com.github.sanforjr2021.task.AddPointsFromMessage;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MessageHandler extends ListenerAdapter {
    public void onMessageReceived(MessageReceivedEvent event) {
        Message msg = event.getMessage();
        if (!msg.getAuthor().isBot()) {
            if (event.isFromType(ChannelType.PRIVATE)) {
                System.out.printf("[PM] %s: %s\n", event.getAuthor().getName(),
                        event.getMessage().getContentDisplay());
            } else {
                System.out.printf("[%s][%s] %s: %s\n", event.getGuild().getName(),
                        event.getTextChannel().getName(), event.getMember().getEffectiveName(),
                        event.getMessage().getContentDisplay());
            }
            AddPointsFromMessage.addUser(msg.getAuthor().getId());
        }
    }
}
