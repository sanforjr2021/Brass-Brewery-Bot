package com.github.sanforjr2021.task;

import com.github.sanforjr2021.database.dao.GuildMemberDao;
import com.github.sanforjr2021.database.domain.GuildMember;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.VoiceChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;
import static com.github.sanforjr2021.BrassBreweryBot.GUILD;

public class AddPointsFromVoice extends TimerTask {

    private static final Logger logger = LoggerFactory.getLogger("Task Log");

    @Override
    public void run() {
        ArrayList<String> userIds = new ArrayList<String>();
        List<VoiceChannel> voiceList = GUILD.getVoiceChannels();
        for (VoiceChannel voiceChannel : voiceList) {
            List<Member> members = voiceChannel.getMembers();
            if (!voiceChannel.equals(GUILD.getAfkChannel())) {
                for (Member member : members) {
                    userIds.add(member.getUser().getId());
                }
            }
        }
        try {
            for (String id: userIds) {
                GuildMember member = GuildMemberDao.get(id);
                member.setCurrency(member.getCurrency()+2);
                GuildMemberDao.update(member);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        if(userIds.size() == 1){
            logger.info("Added 2 points to a user for being in voice.");
        }
        else if(userIds.size() > 1){
            logger.info("Added %s points to %s users for being in voice.",  userIds.size()*2, userIds.size());
        }

    }
}
