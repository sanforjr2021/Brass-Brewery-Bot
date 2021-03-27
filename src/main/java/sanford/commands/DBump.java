package sanford.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import sanford.BrassBreweryBot;

import java.util.concurrent.TimeUnit;

public class DBump extends Command{

    public DBump(Message msg) {
        super(msg);
    }

    @Override
    public void executeCommand() {
        if(getArguments(1).equals("bump")) {
            channel.sendMessage(buildEmbedded().build()).queueAfter(1, TimeUnit.SECONDS);
        }
    }

    public static String getHelpString() {
        String helpString = "`!D Bump`";
        helpString += "- *Bumps the server on Disboard.*";
        return helpString;
    }

    private EmbedBuilder buildEmbedded(){
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setColor(embeddedColor);
        embedBuilder.setTitle("Rate Us on Disboard", "https://disboard.org/server/654574172999254016");
        embedBuilder.setDescription("Thanks " + mention + " for bumping our server! If you want to help promote our server more, please consider rating us. https://disboard.org/server/654574172999254016");
        embedBuilder.setThumbnail(BrassBreweryBot.getGuild().getIconUrl());
        return embedBuilder;
    }
}
