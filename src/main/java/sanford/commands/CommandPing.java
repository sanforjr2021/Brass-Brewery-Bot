package sanford.commands;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;

public class CommandPing
{
    public CommandPing(Message msg)
    {
            MessageChannel channel = msg.getChannel();
            long time = System.currentTimeMillis();
                channel.sendMessage("Pong!")
                        .queue(response -> {
                        response.editMessageFormat("Pong\n Queue Time: %d ms\nReceive To Send:%d ms",
                        System.currentTimeMillis()  - time,
                        System.currentTimeMillis()- msg.getTimeCreated().toInstant().toEpochMilli()).queue();
                    });
            msg.getTimeCreated();
    }
}
