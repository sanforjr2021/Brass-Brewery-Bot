package sanford.commands;

import net.dv8tion.jda.api.entities.Message;
import sanford.BrassBreweryBot;
import sanford.util.Util;

public class CommandHandler extends Command {

    public CommandHandler(Message msg) {
        super(msg);
    }

    @Override
    public void executeCommand() {
        if (getMessage().getContentRaw().startsWith("!")) {
            switch (getArguments(0)) {
                case "!ping":
                    new Ping(getMessage());
                    break;
                case "!role":
                    new Role(getMessage());
                    break;
                case "!points":
                    new Points(getMessage());
                    break;
                case "!top":
                    new Top(getMessage());
                    break;
                case "!rankup":
                    new RankUp(getMessage());
                    break;
                case "!help":
                    System.out.println("I typed help");
                    new Help(getMessage());
                    break;
            }//end of switch
        }//end of else
}   //end of executeCommand
}//end of Command handler