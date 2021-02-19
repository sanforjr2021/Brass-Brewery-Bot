package sanford.commands;

import net.dv8tion.jda.api.entities.Message;

public class Source extends Command{
    public Source(Message msg) {
        super(msg);
    }

    @Override
    public void executeCommand() {
        sendMessage(mention + "\nFeel free to invite your friend's: https://discord.gg/sDmd8v2");
    }

    public static String getHelpString() {
        String helpString = "`!Source`";
        helpString += " - *View my source code on Github.*";
        return helpString;
    }
}
