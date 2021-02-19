package sanford.commands;

import net.dv8tion.jda.api.EmbedBuilder;
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
        String pointString;
        MemberDataContainer memberDataContainer;
        try {
            memberDataContainer = SQLServerHandler.getMemberDataContainer(id);
            pointString =  "**"+ memberDataContainer.getCurrency() + " points.**";
        } catch (SQLException throwables) {
            logError("Could not connect to database.");
            throwables.printStackTrace();
            pointString = "Sorry, I could not retrieve your data from the database";
        }
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setColor(embeddedColor);
        embedBuilder.setTitle(user.getName() + "'s Points");
        embedBuilder.setThumbnail(user.getAvatarUrl());
        embedBuilder.setDescription(pointString);
        sendMessage(embedBuilder);
    }
    public static String getHelpString() {
        String helpString = "`!Points`";
        helpString += " - *Returns the number of points you have.*";
        return helpString;
    }

}
