package sanford.commands;

import net.dv8tion.jda.api.entities.Message;

public class Help extends Command {
    public Help(Message msg) {
        super(msg);
    }

    @Override
    public void executeCommand() {
        String string = mention + "```List of Commands:\n" +
                "!Help\t\t\t\tReturns information about commands.\n" +
                "!Ping\t\t\t\tPings the host device.\n" +
                "!Points\t\t\t  Returns the number of points you have.\n" +
                "!RankUp\t\t\t  Will rank you up to the next valid rank if you have enough points.\n" +
                "!Role <role name>\tAdds or removes a game role to allow you access to the designated table.\n" +
                "!Top\t\t\t\t Returns the top 10 highest point individuals.\n```";
        sendMessage(string);
    }
}
