package sanford.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
            usersIds.add(id);
        }
    }

    @Override
    public void run() {
        try {
            SQLServerHandler.addPointsToMember(usersIds, 1);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        if(usersIds.size() > 0){
            logger.info("Added " + usersIds.size() + " points to " + usersIds.size() + " different users.");
        }
        usersIds.clear();
    }
}
