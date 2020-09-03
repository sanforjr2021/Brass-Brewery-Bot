package sanford;

import net.dv8tion.jda.api.*;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import sanford.util.MessageListener;
import sanford.util.ReadyListener;

import javax.security.auth.login.LoginException;

public class BrassBreweryBot
{

    private static JDA jda;
    private static String serverID;
    private static Guild guild;
    private static MessageChannel verifyChannel;
    private static VoiceChannel afkChannel;

    public BrassBreweryBot(String args[]) throws LoginException
    {
         jda = JDABuilder.createDefault(args[0])
            .addEventListeners(new MessageListener())
            .addEventListeners(new ReadyListener())
            .build();
        addShutdownThreat();
        serverID = args[1];
    }
    //On Shutdown command
    public void addShutdownThreat()
    {
        Runtime.getRuntime().addShutdownHook(new Thread()
        {
            public void run()
            {
                try
                {
                    Thread.sleep(200);
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                System.out.println("Shutting Down Brass Brewery Bot");
                //on shutdown
                jda.shutdown();
            }
        });
    }
    //on startup
    public static void onStartupComplete()
    {
            guild = jda.getGuildById(serverID);
            verifyChannel = guild.getTextChannelById("737105063948058664");
            afkChannel = guild.getAfkChannel();
            System.out.println("Startup Complete!"); //Inform Pterodactyl the bot is running

    }
    public static void main(String Args[]) throws LoginException
    {
        BrassBreweryBot brassBreweryBot = new BrassBreweryBot(Args);
    }

    //Getters
    public static MessageChannel getVerifyChannel()
    {
        return verifyChannel;
    }
    public static Guild getGuild()
    {
        return guild;
    }
    public static VoiceChannel getAFKChannel()
    {
        return afkChannel;
    }
}