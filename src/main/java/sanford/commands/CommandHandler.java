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
        if (BrassBreweryBot.getVerifyChannel().getId().equals(getChannel().getId())) {
            if (getArguments(0).toLowerCase().equals("!verify")) {
                new Verify(getMessage());
            } else {
                Util.directMessage(getUser(), "This channel is meant only for verifying. To be verified, please type !verify.");
            }
            getMessage().delete().queue();
        } else {
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
                }//end of switch
            }//end of else
        }//end of if
    }//end of executeCommand
}//end of Command handler