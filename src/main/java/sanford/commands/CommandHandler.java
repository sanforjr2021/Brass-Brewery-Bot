package sanford.commands;

import net.dv8tion.jda.api.entities.Message;

public class CommandHandler {

    public CommandHandler(Message message) {
        if (message.getContentRaw().startsWith("!")) {
            String[] arguments = message.getContentRaw().split(" ");
            switch (arguments[0]) {
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
                    new Invite(message);
                    break;
                case "!d":
                    new DBump(message);
                    break;
                case "!source":
                    new Source(message);
                    break;
            }//end of switch
        }//end of else
    }
}//end of Command handler