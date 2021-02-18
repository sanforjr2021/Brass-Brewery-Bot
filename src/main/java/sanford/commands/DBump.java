package sanford.commands;

import net.dv8tion.jda.api.entities.Message;

import java.util.concurrent.TimeUnit;

public class DBump extends Command{

    public DBump(Message msg) {
        super(msg);
    }

    @Override
    public void executeCommand() {
        if(getArguments(1).equals("bump")) {
            channel.sendMessage("Thanks " + mention + "for helping keep our server active and growing." +
                    "If you want to continue to help us, please consider rating our server at https://disboard.org/server/654574172999254016").queueAfter(1, TimeUnit.SECONDS);
        }
    }
}
