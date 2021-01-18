package sanford;

import net.dv8tion.jda.api.*;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import sanford.data.ConfigHandler;
import sanford.data.SQLServerHandler;
import sanford.task.TaskController;
import sanford.util.MessageListener;
import sanford.util.ReadyListener;

import javax.security.auth.login.LoginException;

public class BrassBreweryBot {

    private static JDA jda;
    private static Guild guild;
    private static MessageChannel verifyChannel;
    private static VoiceChannel afkChannel;
    private static ConfigHandler configHandler = new ConfigHandler();
    private static TaskController taskController = new TaskController();
    public static final String VERSION = "1.0"; //represents config version

    public BrassBreweryBot() throws LoginException {
        jda = JDABuilder.createDefault(configHandler.getProperty("token"))
                //Note: Chunk filter idea from https://stackoverflow.com/questions/61226721/discord-jda-invalid-member-list
                .setChunkingFilter(ChunkingFilter.include(Long.parseLong(configHandler.getProperty("serverID")))) // enable member chunking for all guilds
                .enableIntents(GatewayIntent.GUILD_MEMBERS)
                .addEventListeners(new MessageListener())
                .addEventListeners(new ReadyListener())
                .build();
        addShutdownThreat();
    }

    //On Shutdown command
    public void addShutdownThreat() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                Thread.sleep(200);
                System.out.println("Shutting Down Brass Brewery Bot");
                taskController.stopAllTask();
                jda.getPresence().setStatus(OnlineStatus.OFFLINE);
                jda.shutdown();
                System.out.println("Bot Stopped");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }));

    }

    //on startup
    public static void onStartupComplete() {
        guild = jda.getGuildById(configHandler.getProperty("serverID"));
        verifyChannel = guild.getTextChannelById("737105063948058664"); //TODO:Replace from config or Database
        afkChannel = guild.getAfkChannel();
        new SQLServerHandler(
                configHandler.getProperty("host"),
                configHandler.getProperty("database"),
                configHandler.getProperty("username"),
                configHandler.getProperty("password")
        );

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