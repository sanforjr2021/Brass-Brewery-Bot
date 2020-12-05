package sanford.task;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.requests.RestAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sanford.BrassBreweryBot;
import sanford.data.SQLServerHandler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.TimerTask;

public class RewardTextPoints extends TimerTask {
    private static ArrayList<String> usersIds;
    private static final Logger logger = LoggerFactory.getLogger("Task Log");

    public RewardTextPoints() {
        usersIds = new ArrayList<String>();
    }


    public static void addUser(String id) {
        if (!usersIds.contains(id)) {
            System.out.println("Added User with id of " + id);
            usersIds.add(id);
        }
    }

    @Override
    public void run() {
        try {
            System.out.println("Adding the following user points:");
            for (String id : usersIds) {
                RestAction<User> userRestAction = BrassBreweryBot.getGuild().getJDA().retrieveUserById(Long.parseLong(id));
                System.out.println(userRestAction.complete().getAsTag());
            }
            SQLServerHandler.addPointsToMember(usersIds, 1);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        logger.info("Added " + usersIds.size() + " points to " + usersIds.size() + " different users.");
        usersIds.clear();
    }
}
