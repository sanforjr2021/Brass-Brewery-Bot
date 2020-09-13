package sanford;

import net.dv8tion.jda.api.*;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import sanford.data.ConfigHandler;
import sanford.util.MessageListener;
import sanford.util.ReadyListener;

import javax.security.auth.login.LoginException;

public class BrassBreweryBot {

    private static JDA jda;
    private static Guild guild;
    private static MessageChannel verifyChannel;
    private static VoiceChannel afkChannel;
    private static ConfigHandler configHandler = new ConfigHandler();
    public static final String VERSION = "1.0"; //represents config version

    public BrassBreweryBot() throws LoginException {
        jda = JDABuilder.createDefault(configHandler.getProperty("token"))
                .addEventListeners(new MessageListener())
                .addEventListeners(new ReadyListener())
                .build();
        addShutdownThreat();
    }

    //On Shutdown command
    public void addShutdownThreat() {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Shutting Down Brass Brewery Bot");
                //on shutdown
                jda.shutdown();
            }
        });
    }

    //on startup
    public static void onStartupComplete() {
        guild = jda.getGuildById(configHandler.getProperty("serverID"));
        verifyChannel = guild.getTextChannelById("737105063948058664"); //TODO:Replace from config or Database
        afkChannel = guild.getAfkChannel();
        //Inform Pterodactyl the bot is running
        System.out.println("Startup Complete!");

    }

    public static void main(String Args[]) throws LoginException {
        BrassBreweryBot brassBreweryBot = new BrassBreweryBot();
    }

    //Getters
    public static MessageChannel getVerifyChannel() {
        return verifyChannel;
    }

    public static Guild getGuild() {
        return guild;
    }

    public static VoiceChannel getAFKChannel() {
        return afkChannel;
    }
}