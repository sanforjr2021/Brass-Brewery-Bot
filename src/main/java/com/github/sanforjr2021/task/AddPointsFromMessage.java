package com.github.sanforjr2021.task;

import com.github.sanforjr2021.database.dao.DaoController;
import com.github.sanforjr2021.database.dao.GuildMemberDao;
import com.github.sanforjr2021.database.domain.GuildMember;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.TimerTask;
public class AddPointsFromMessage extends TimerTask {

    private static ArrayList<String> userIds;
    private static final Logger logger = LoggerFactory.getLogger("Task Log");

    public AddPointsFromMessage() {
        userIds = new ArrayList<String>();
    }

    public static void addUser(String id) {
        if (!userIds.contains(id)) {
            System.out.println(id);
            userIds.add(id);
        }
    }

    @Override
    public void run() {
        try {
            for (String id: userIds) {
                GuildMember member = GuildMemberDao.get(id);
                member.setCurrency(member.getCurrency()+1);
                GuildMemberDao.update(member);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        if(userIds.size() == 1){
            logger.info("Added 1 point to a user for sending messages.");
        }
        else if(userIds.size() > 1){
            logger.info("Added %s points to %s users for sending messages.",  userIds.size(), userIds.size());
        }
        userIds.clear();
    }
}
