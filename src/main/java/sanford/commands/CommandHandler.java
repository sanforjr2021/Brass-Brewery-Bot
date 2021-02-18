package sanford.commands;

import net.dv8tion.jda.api.entities.Message;

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
            }//end of switch
        }//end of else
    }   //end of executeCommand
}//end of Command handler