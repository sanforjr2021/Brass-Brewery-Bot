package sanford.commands;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.requests.RestAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sanford.BrassBreweryBot;

public abstract class Command {
    private static final Logger logger = LoggerFactory.getLogger("Command Logger");
    private final User user;
    private final MessageChannel channel;
    private final Member member;
    private final String[] arguments;
    private final Message message;

    public Command(Message msg) {
        message = msg;
        user = msg.getAuthor();
        channel = msg.getChannel();
        member = BrassBreweryBot.getGuild().getMember(user);
        arguments = msg.getContentRaw().toLowerCase().split(" ");
        executeCommand();
    }

    public abstract void executeCommand();

    public User getUser() {
        return user;
    }

    public MessageChannel getChannel() {
        return channel;
    }

    public Member getMember() {
        return member;
    }

    public String getArguments(int value) {
        try{
            return arguments[value];
        }
        catch( ArrayIndexOutOfBoundsException e){
            return "INVALID";
        }

    }

    public Message getMessage() {
        return message;
    }

    public JDA getJda() {
        return message.getJDA();
    }

    public User getUserByID(String id){
        RestAction<User> userRestAction = getMessage().getJDA().retrieveUserById(Long.parseLong(id));
        return userRestAction.complete();
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
