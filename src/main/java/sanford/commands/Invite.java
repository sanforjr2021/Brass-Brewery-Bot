package sanford.commands;

import net.dv8tion.jda.api.entities.Message;

public class Invite extends  Command{
    public Invite(Message msg) {
        super(msg);
    }

    @Override
    public void executeCommand() {
        sendMessage(mention + "\nFeel free to invite your friend's: https://discord.gg/sDmd8v2");
    }

    public static String getHelpString() {
        String helpString = "`!Invite`";
        helpString += " - *Get an invite you can send to your friends.*";
        return helpString;
    }
}
