package sanford;

import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import sanford.util.ReadyListener;

import javax.annotation.Nonnull;
import javax.security.auth.login.LoginException;

public class BrassBreweryBot {
    private static JDA JDA;
    private static String serverID;
    private static Guild guild;
    private static MessageChannel verifyChannel;
    private static VoiceChannel afkChannel;
    public BrassBreweryBot(String args[]){
        try {
            JDA = new JDABuilder(AccountType.BOT).setToken(args[0])
                //register event listeners
                .addEventListeners(new MessageListener())
                .addEventListeners(new ReadyListener())
                .build();
            addShutdownThreat();
            serverID = args[1];
        }
        catch(LoginException e) {
            System.out.println(e.getMessage());
        }

    }
    //On Shutdown
    public void addShutdownThreat(){
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                try {
                    Thread.sleep(200);
                    System.out.println("Shutting Down Brass Brewery Bot");
                    //on shutdown
                    JDA.shutdown();

                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    e.printStackTrace();
                }
            }
        });
    }
    //on startup
    public static void onStartupComplete(){
            guild = JDA.getGuildById(serverID);
            verifyChannel = guild.getTextChannelById("737105063948058664");
            afkChannel = guild.getAfkChannel();
            System.out.println("Startup Complete!"); //Inform Pterodactyl the bot is running

    }
/*
Getters
*/

    public static MessageChannel getVerifyChannel(){
        return verifyChannel;
    }
    public static Guild getGuild(){
        return guild;
    }
    public static VoiceChannel getAFKChannel(){
        return afkChannel;
    }
    public static void main(String Args[]) {
        System.out.println("Args[0] = " + Args[0]);
        System.out.println("Args[1] = " + Args[1]);
        BrassBreweryBot brassBreweryBot = new BrassBreweryBot(Args);
    }
}


/*
Ready Listener
 */
