package sanford.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;

import java.awt.*;
import java.util.ArrayList;

public class Help extends Command {
    private static final ArrayList<String> list = generateArrayOfCommands();

    public Help(Message msg) {
        super(msg);
    }

    @Override
    public void executeCommand() {
        int pageNum = 1;
        try {
            pageNum = Integer.parseInt(getArguments(1));
            if ((0 < pageNum && pageNum < list.size() / 10 + 1)) {
                pageNum = 1;
            }
        } catch (Exception e) {
            //generic catch for if the argument is null or if it is invalid.
            //If the input is invalid, 1 is automatically assumed as the value would not have changed.
        }

        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle("List Of Commands");
        embedBuilder.setDescription(getCommandsPage(pageNum));
        embedBuilder.setFooter("Page " + pageNum + " out of " + (list.size() / 10 + 1));
        embedBuilder.setColor(new Color(255, 200, 0));
        sendMessage(embedBuilder);
    }

    private String getCommandsPage(int page) {
        StringBuilder pageString = new StringBuilder();
        for (int i = (page - 1) * 10; i < page * 10 && i < list.size(); i++) {
            pageString.append(list.get(i)).append("\n");
        }
        return pageString.toString();
    }

    public static String getHelpString() {
        String helpString = "`!Help <Page Number>`";
        helpString += " - *If you're too drunk to remember, this might find you the solution.*";
        return helpString;
    }

    private static ArrayList<String> generateArrayOfCommands() {
        ArrayList<String> list = new ArrayList<>();
        list.add(DBump.getHelpString());
        list.add(Help.getHelpString());
        list.add(Invite.getHelpString());
        list.add(Ping.getHelpString());
        list.add(Points.getHelpString());
        list.add(RankUp.getHelpString());
        list.add(Role.getHelpString());
        list.add(Source.getHelpString());
        list.add(Top.getHelpString());
        list.add(Rank.getHelpString());
        return list;
    }
}
