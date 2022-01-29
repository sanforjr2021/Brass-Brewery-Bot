package com.github.sanforjr2021.util;

import com.github.sanforjr2021.service.CurrencyService;
import com.github.sanforjr2021.service.MessageService;
import com.github.sanforjr2021.task.AddPointsFromMessage;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.sql.SQLException;
import java.util.List;

import static com.github.sanforjr2021.BrassBreweryBot.GUILD;
import static com.github.sanforjr2021.util.RandomMessageGenerator.getNegativeMessage;
import static com.github.sanforjr2021.util.RandomMessageGenerator.getPositiveMessage;

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
        //todo: Replace with https://ci.dv8tion.net/job/JDA/javadoc/net/dv8tion/jda/api/entities/MessageChannel.html#getHistoryAround(java.lang.String,int)
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
                MessageService.sendPrivateMessage(senderMember.getUser(), buildEmbeddedMessage(
                        "Congratulations!",
                        "User " + reactorMember.getEffectiveName() + " tipped you " + points + " gold."
                                + "See message: " + msg.getJumpUrl(),
                        Color.orange,
                        GUILD.getIconUrl(),
                        getPositiveMessage()
                ));
                MessageService.sendAuditLog(buildEmbeddedMessage(
                        "Brass Brewery Bot",
                        "User " + reactorMember.getAsMention() + " tipped " + senderMember.getAsMention() + points + " gold.\n"
                                +"See message: " + msg.getJumpUrl(),
                        Color.orange,
                        senderMember.getAvatarUrl()
                ));
            }
            else{
                MessageService.sendPrivateMessage(reactorMember.getUser(), buildEmbeddedMessage(
                        "Uh oh! You don't have enough gold",
                        "You cannot afford to tip " + senderMember.getEffectiveName() + " " + points + " gold.",
                        Color.RED,
                        GUILD.getIconUrl(),
                        getNegativeMessage()

                ));
            }


        } catch (SQLException throwables) {
            throwables.printStackTrace();
            MessageService.sendPrivateMessage(reactorMember.getUser(),"I could not tip " + senderMember.getNickname() + " at this time");
        }
    }
    private static MessageEmbed buildEmbeddedMessage(String title, String description, Color color, String thumbnail, String footer) {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setColor(color);
        embedBuilder.setTitle(title);
        embedBuilder.setDescription(description);
        embedBuilder.setThumbnail(thumbnail);
        embedBuilder.setFooter(footer);
        return embedBuilder.build();
    }
    private static MessageEmbed buildEmbeddedMessage(String title, String description, Color color, String thumbnail) {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setColor(color);
        embedBuilder.setTitle(title);
        embedBuilder.setDescription(description);
        embedBuilder.setThumbnail(thumbnail);
        return embedBuilder.build();
    }
}
