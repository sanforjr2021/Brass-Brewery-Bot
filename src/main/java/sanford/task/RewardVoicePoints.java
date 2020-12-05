package sanford.task;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.VoiceChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sanford.BrassBreweryBot;
import sanford.data.SQLServerHandler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

public class RewardVoicePoints extends TimerTask {
    private static final Logger logger = LoggerFactory.getLogger("Task Log");
    @Override
    public void run() {
        ArrayList<String> ids = new ArrayList<String>();
        List<VoiceChannel> voiceList = BrassBreweryBot.getGuild().getVoiceChannels();
        for(VoiceChannel voiceChannel : voiceList){
            List<Member> members = voiceChannel.getMembers();
            if(!voiceChannel.equals(BrassBreweryBot.getGuild().getAfkChannel())){
                for(Member member : members){
                    ids.add(member.getUser().getId());
                }
            }
        }
        try {
            SQLServerHandler.addPointsToMember(ids, 2);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        logger.info("Added " + ids.size()*2 + " points to " + ids.size() + " different users.");
    }
}
