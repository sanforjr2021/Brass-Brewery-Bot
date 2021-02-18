package sanford.commands;

import net.dv8tion.jda.api.entities.Message;
import sanford.util.Util;

public class Ping extends Command {
    public Ping(Message msg) {
        super(msg);
    }

    @Override
    public void executeCommand() {
        long time = System.currentTimeMillis();
        channel.sendMessage("Pong!")
                .queue(response -> response.editMessageFormat("Pong\n Queue Time: %d ms\nReceive To Send:%d ms\nSent" +
                                " from host " + Util.getHostName(), System.currentTimeMillis() - time,
                        System.currentTimeMillis() - message.getTimeCreated().toInstant().toEpochMilli()).queue());
        message.getTimeCreated();
    }
}
