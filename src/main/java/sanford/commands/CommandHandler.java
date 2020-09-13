package sanford.commands;

import net.dv8tion.jda.api.entities.*;
import sanford.BrassBreweryBot;
import sanford.util.Util;

public class CommandHandler {

    private Member author;
    private MessageChannel guildChannel;
    private PrivateChannel privateChannel;
    private String[] messages;
    private Message msg;

    public CommandHandler(Message msg, ChannelType channel) {
        author = msg.getMember();
        this.msg = msg;
        messages = msg.getContentRaw().trim().split(" ");
        if (channel.equals(ChannelType.TEXT)) {
            guildChannel = msg.getTextChannel();
            commandTextChannel();
        } else if (channel.equals(ChannelType.PRIVATE)) {
            privateChannel = msg.getPrivateChannel();
            commandPrivateChannel();
        }

    }

    //applies to guild text channels
    private void commandTextChannel() {
        if (BrassBreweryBot.getVerifyChannel().getName().equals(guildChannel.getName())) {
            //by channel
            if (messages[0].toLowerCase().equals("!verify")) {
                new Verify(msg);

            } else {
                Util.directMessage(author.getUser(), "This channel is meant only for verifying. To be verified, please type !verify.");
            }
            msg.delete().queue();
        }
        //by command
        else {
            switch (messages[0]) {
                case "!role":
                    new Role(msg);
                    break;
            }

            commandUniversalChannel();
        }
    }

    //Applies to private text channels only(DMs)
    private void commandPrivateChannel() {
        commandUniversalChannel();
    }

    //Applies to guild and private text channels
    private void commandUniversalChannel() {
        if (msg.getContentRaw().startsWith("!")) {
            //by command
            switch (messages[0]) {
                case "!ping":
                    new Ping(msg);
                    break;
            }
        }
    }
}
