package sanford.commands;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import sanford.data.MemberDataContainer;
import sanford.data.SQLServerHandler;

import java.sql.SQLException;
import java.util.ArrayList;

public class Top extends Command {
    public Top(Message msg) {
        super(msg);
    }

    @Override
    public void executeCommand() {
        try {
            ArrayList<MemberDataContainer> memberDataContainers = SQLServerHandler.getTop10Members();
            StringBuilder solution = new StringBuilder();
            solution.append(mention).append("```");
            for (int i = 0; i < memberDataContainers.size(); i++) {
                solution.append((i + 1)).append(". ");
                MemberDataContainer memberDataContainer = memberDataContainers.get(i);
                User user = getUserByID(memberDataContainer.getId());
                solution.append(user.getAsTag());
                if (i <= 8) {
                    String spacer = " ".repeat(50 - user.getAsTag().length());
                    solution.append(spacer);
                } else { //must remove 1 space as 10 is 2 chars instead of 1.
                    String spacer = " ".repeat(49 - user.getAsTag().length());
                    solution.append(spacer);
                }
                solution.append(memberDataContainer.getCurrency()).append(" Points\n");
            }
            sendMessage(solution.toString() + "```");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public static String getHelpString() {
        String helpString = "`!Top`";
        helpString += " - *Returns the top 10 highest point individuals.*";
        return helpString;
    }
}
