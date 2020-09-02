package sanford;

import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import sanford.commands.Ping;
import sanford.commands.VerifyUser;
import sanford.util.Util;

import java.nio.channels.Channel;


public class MessageListener extends ListenerAdapter
{
    public MessageListener(){}
    @Override
    public void onMessageReceived(MessageReceivedEvent event)
    {
        Message msg = event.getMessage();
        if(!msg.getAuthor().isBot())
        {
            String msgString[] = msg.getContentRaw().split(" ");
            if (BrassBreweryBot.getVerifyChannel().getName().equals(msg.getChannel().getName()))
            {
                if (msgString[0].toLowerCase().equals("!verify"))
                {
                    new VerifyUser(msg);
                }
                else
                {
                    msg.delete().queue();
                    Util.directMessage(msg.getAuthor(), "This channel is meant only for verifying. To be verified, please type !verify.");
                }//end of else
            return;
            }//end of Verify Channel
            if(msg.getContentRaw().startsWith("!"))
            {
                //by command
                switch (msgString[0].toLowerCase())
                {
                    case "!ping":
                        new Ping(msg);
                        break;
                    case "!roll":
                        //new Roll(msg);
                        break;
                    case "!help":
                        break;
                }//end of switch
            }//end of starts with !
        }
        if (event.isFromType(ChannelType.PRIVATE))
        {
            System.out.printf("[PM] %s: %s\n", event.getAuthor().getName(),
                    event.getMessage().getContentDisplay());
        }
        else
        {
            System.out.printf("[%s][%s] %s: %s\n", event.getGuild().getName(),
                    event.getTextChannel().getName(), event.getMember().getEffectiveName(),
                    event.getMessage().getContentDisplay());
        }
    }
}
