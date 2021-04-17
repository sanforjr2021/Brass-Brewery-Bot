package sanford.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import sanford.data.RankDataContainer;

import java.sql.SQLException;
import java.util.ArrayList;

import static sanford.data.SQLServerHandler.getRanks;

public class Rank extends Command {
    private ArrayList<RankDataContainer> ranks;

    public Rank(Message msg) {
        super(msg);
    }

    public static String getHelpString() {
        String helpString = "`!Rank";
        helpString += "- *Provides a list of ranks available on the server.*";
        return helpString;
    }

    @Override
    public void executeCommand() {
        try {
            ranks = getRanks();
            sendMessage(buildEmbedded());
        } catch (SQLException throwables) {
            sendMessage(buildErrorEmbedded());
        }
    }

    private EmbedBuilder buildEmbedded() {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setColor(embeddedColor);
        embedBuilder.setTitle("List of Ranks");
        String s = "```Tier\t Cost\t\tName``````";
        for (RankDataContainer rank : ranks) {
            String cost = String.valueOf(rank.getCost());
            s += "\n" + rank.getTier() + "\t\t" + cost;

            for(int i = 0; i < 12-cost.length(); i++){
                s+= " ";
            }
            s+= rank.getName();

        }
        embedBuilder.setDescription(s + "\n```");
        return embedBuilder;
    }
}