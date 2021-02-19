package sanford.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.requests.RestAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sanford.BrassBreweryBot;

import java.awt.*;

public abstract class Command {
    private static final Logger logger = LoggerFactory.getLogger("Command Logger");
    protected final User user;
    protected final MessageChannel channel;
    protected final Member member;
    protected final String[] arguments;
    protected final Message message;
    protected final JDA jda;
    protected final String mention;
    protected final Color embeddedColor;

    public Command(Message msg) {
        message = msg;
        user = msg.getAuthor();
        channel = msg.getChannel();
        member = BrassBreweryBot.getGuild().getMember(user);
        arguments = msg.getContentRaw().toLowerCase().split(" ");
        jda = msg.getJDA();
        mention = user.getAsMention();
        embeddedColor = new Color(255, 183, 0);
        executeCommand();
    }

    public abstract void executeCommand();

    public static String getHelpString() {
        return null;
    }

    public String getArguments(int value) {
        try {
            return arguments[value];
        } catch (ArrayIndexOutOfBoundsException e) {
            return "INVALID";
        }
    }

    public void sendMessage(String messageString) {
        channel.sendMessage(messageString).queue();
    }
    public void sendMessage(EmbedBuilder embedBuilder) {
        channel.sendMessage(embedBuilder.build()).queue();
    }
    public User getUserByID(String id) {
        RestAction<User> userRestAction = jda.retrieveUserById(Long.parseLong(id));
        return userRestAction.complete();
    }

    public void addReaction(String reactionID) {
        message.addReaction(reactionID).queue();
    }

    public void logError(String error) {
        logger.error(error);
    }

    public void logWarning(String warning) {
        logger.warn(warning);
    }

    public void logInfo(String info) {
        logger.info(info);
    }

}
