package sanford.commands;

import net.dv8tion.jda.api.entities.Message;

import java.util.concurrent.TimeUnit;

public class CommandHandler extends Command {

    public CommandHandler(Message msg) {
        super(msg);
    }

    @Override
    public void executeCommand() {
        if (message.getContentRaw().startsWith("!")) {
            switch (getArguments(0)) {
                case "!ping":
                    new Ping(message);
                    break;
                case "!role":
                    new Role(message);
                    break;
                case "!points":
                    new Points(message);
                    break;
                case "!top":
                    new Top(message);
                    break;
                case "!rankup":
                    new RankUp(message);
                    break;
                case "!help":
                    new Help(message);
                    break;
                case "!invite":
                    //TODO: make into a class and stop the link from being hardcoded
                    sendMessage(mention + "\nFeel free to invite your friend's: https://discord.gg/sDmd8v2");
                    break;
                case "!d":
                    new DBump(message);
                    break;
                case "!source":
                    sendMessage("Here's my sourcecode: https://github.com/sanforjr2021/BBBot");
                    break;
            }//end of switch
        }//end of else
    }   //end of executeCommand
}//end of Command handler