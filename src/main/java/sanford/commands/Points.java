package sanford.commands;

import net.dv8tion.jda.api.entities.Message;
import sanford.data.MemberDataContainer;
import sanford.data.SQLServerHandler;

import java.sql.SQLException;

public class Points extends Command{
    public Points(Message msg) {
        super(msg);
    }

    @Override
    public void executeCommand(){
        String id = getUser().getId();
        MemberDataContainer memberDataContainer;
        try {
            memberDataContainer = SQLServerHandler.getMemberDataContainer(id);
            getChannel().sendMessage(getUser().getAsMention() + " has a value of " + memberDataContainer.getCurrency() + " points.").queue();
        } catch (SQLException throwables) {
            logError("Could not connect to database.");
            throwables.printStackTrace();
            getChannel().sendMessage(getUser().getAsMention() + " Sorry, I could not retrieve your data from the database").queue();
        }

    }

}
