package sanford.commands;

import net.dv8tion.jda.api.entities.Message;
import sanford.data.MemberDataContainer;
import sanford.data.SQLServerHandler;

import java.sql.SQLException;

public class Points extends Command {
    public Points(Message msg) {
        super(msg);
    }

    @Override
    public void executeCommand() {
        String id = user.getId();
        MemberDataContainer memberDataContainer;
        try {
            memberDataContainer = SQLServerHandler.getMemberDataContainer(id);
            sendMessage(user.getAsMention() + " has a value of " + memberDataContainer.getCurrency() + " points.");
        } catch (SQLException throwables) {
            logError("Could not connect to database.");
            throwables.printStackTrace();
            sendMessage(user.getAsMention() + " Sorry, I could not retrieve your data from the database");
        }

    }

}
