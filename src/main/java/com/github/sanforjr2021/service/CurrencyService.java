package com.github.sanforjr2021.service;

import com.github.sanforjr2021.database.dao.GuildMemberDao;
import com.github.sanforjr2021.database.domain.BuyableRole;
import com.github.sanforjr2021.database.domain.GuildMember;
import com.github.sanforjr2021.database.domain.Rank;
import net.dv8tion.jda.api.entities.User;

import java.sql.SQLException;

public class CurrencyService {

    public static boolean removeMemberCurrency(User user, int cost) throws SQLException {
        boolean hasRemoved = false;
        GuildMember member = GuildMemberDao.get(user.getId());
        if (member.getCurrency() >= cost) {
            member.setCurrency(member.getCurrency() - cost);
            GuildMemberDao.write(member);
            hasRemoved = true;
        }
        return hasRemoved;
    }

    public static boolean canAffordRole(User user, Rank rank) throws SQLException {
        boolean canGetRole = false;
        GuildMember member = GuildMemberDao.get(user.getId());
        if (member.getCurrency() >= rank.getCost()) {
            canGetRole = true;
        }
        return canGetRole;
    }

    public static boolean canAffordRole(User user, BuyableRole role) throws SQLException {
        boolean canGetRole = false;
        GuildMember member = GuildMemberDao.get(user.getId());
        if (member.getCurrency() >= role.getCost()) {
            canGetRole = true;
        }
        return canGetRole;
    }

    public static boolean canAffordRole(User user, int cost) throws SQLException {
        boolean canGetRole = false;
        GuildMember member = GuildMemberDao.get(user.getId());
        if (member.getCurrency() >= cost) {
            canGetRole = true;
        }
        return canGetRole;
    }

    public static int getPoints(User user) throws SQLException{
        return GuildMemberDao.get(user.getId()).getCurrency();
    }

    /**
     * Transfers points from sender user to receiver user if sender has a minimum number of points.
     * Throws an exception if has issues with DB.
     * handling if an error occurs.
     * @param senderID
     * @param receiverID
     * @param points
     * @return
     * @throws SQLException
     */
    public static boolean transferPoints(String senderID, String receiverID, int points) throws SQLException{
        if(!senderID.equals(receiverID)){
                GuildMember sender = GuildMemberDao.get(senderID);
                GuildMember receiver = GuildMemberDao.get(receiverID);
                if(sender.getCurrency() >= points){
                    sender.setCurrency(sender.getCurrency()-points);
                    receiver.setCurrency(receiver.getCurrency()+points);
                    GuildMemberDao.update(sender);
                    GuildMemberDao.update(receiver);
                    return true;
                }
        }
        return false;
    }

}
