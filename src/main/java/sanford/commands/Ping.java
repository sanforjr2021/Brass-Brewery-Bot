package sanford.commands;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import sanford.util.Util;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Ping extends Command{
    public Ping(Message msg) {
       super(msg);
    }

    @Override
    public void executeCommand() {
        MessageChannel channel = getChannel();
        long time = System.currentTimeMillis();
        channel.sendMessage("Pong!")
                .queue(response -> {
                    response.editMessageFormat("Pong\n Queue Time: %d ms\nReceive To Send:%d ms\nSent from host " + Util.getHostName(),
                            System.currentTimeMillis() - time,
                            System.currentTimeMillis() - getMessage().getTimeCreated().toInstant().toEpochMilli()).queue();
                });
        getMessage().getTimeCreated();
    }
}
