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
            solution.append(getUser().getAsMention() + "\n");
            for (int i = 0; i < memberDataContainers.size(); i++) {
                solution.append(i+ 1 +". ");
                MemberDataContainer memberDataContainer = memberDataContainers.get(i);
                User user = getUserByID(memberDataContainer.getId());
                solution.append(user.getAsTag());
                for(int x = 0; x < 60-user.getAsTag().length(); x++){
                    solution.append(" ");
                }
                solution.append(memberDataContainer.getCurrency() + " Points\n");
            }
            getChannel().sendMessage(solution.toString()).queue();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
}
