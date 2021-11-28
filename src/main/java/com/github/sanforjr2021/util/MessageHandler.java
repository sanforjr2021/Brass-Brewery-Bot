package com.github.sanforjr2021.util;

import com.github.sanforjr2021.service.CurrencyService;
import com.github.sanforjr2021.service.MessageService;
import com.github.sanforjr2021.task.AddPointsFromMessage;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.sql.SQLException;
import java.util.List;

public class MessageHandler extends ListenerAdapter {
    public void onMessageReceived(MessageReceivedEvent event) {
        Message msg = event.getMessage();
        if (!msg.getAuthor().isBot()) {
            //If not a bot log the messages and queue point adding
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
    public void onMessageReactionAdd(MessageReactionAddEvent event){
        Member reactorMember = event.getMember();
        TextChannel channel = event.getTextChannel();
        //From https://stackoverflow.com/questions/67023579/jda-discord-bot-read-last-message-of-textchannel
        //retrieve the past 20 messages in that channel and see if one received the reaction via msg id
        channel.getHistory().retrievePast(20).queue(messages -> {
            Message msg = getMessageFromList(event.getMessageId(), messages);
                if(msg != null){
                    Member senderMember = msg.getMember();
                    if(!reactorMember.getUser().isBot() && !senderMember.getUser().isBot()){
                        //System.out.println("EMOTE: " + event.getReaction().getReactionEmote().getName());
                        switch (event.getReaction().getReactionEmote().getName()){
                            case "Tip1":
                                tipUser(reactorMember, senderMember, msg, 1);
                                break;
                            case "Tip5":
                                tipUser(reactorMember, senderMember, msg, 5);
                                break;
                            case "Tip10":
                                tipUser(reactorMember, senderMember, msg, 10);
                                break;
                    }
                }
            }
        });
    }

    private Message getMessageFromList(String msgID, List<Message> messageList){
        for (Message msg: messageList) {
            if(msg.getId().equals(msgID)){
                return msg;
            }
        }
        return null;
    }
    private void tipUser(Member reactorMember, Member senderMember, Message msg, int points){
        try {
            if(CurrencyService.transferPoints(reactorMember.getId(), senderMember.getId(), points)){
                MessageService.sendPrivateMessage(reactorMember.getUser(), "You tipped " + senderMember.getAsMention() + " " + points + " points.");
                MessageService.sendPrivateMessage(senderMember.getUser(), "Congratulations! " + reactorMember.getAsMention() + " tipped you " + points
                        + " points for this message.\n" + msg.getJumpUrl());
                MessageService.sendAuditLog(reactorMember.getNickname() + " tipped " + senderMember.getAsMention() + points
                        + " points for the message: " + msg.getJumpUrl());
            }
            else{
                MessageService.sendPrivateMessage(reactorMember.getUser(), "You cannot afford to tip " + senderMember.getAsMention() + " " + points + "points.");
            }


        } catch (SQLException throwables) {
            throwables.printStackTrace();
            MessageService.sendPrivateMessage(reactorMember.getUser(),"I could not tip " + senderMember.getNickname() + " at this time");
        }
    }
}
