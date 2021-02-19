package sanford.commands;

import net.dv8tion.jda.api.entities.Message;

public class Invite extends  Command{
    public Invite(Message msg) {
        super(msg);
    }

    @Override
    public void executeCommand() {
        sendMessage("Here's my sourcecode: https://github.com/sanforjr2021/BBBot");
    }

    public static String getHelpString() {
        String helpString = "`!Invite`";
        helpString += " - *Get an invite you can send to your friends.*";
        return helpString;
    }
}
