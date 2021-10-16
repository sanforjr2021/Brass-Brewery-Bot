package com.github.sanforjr2021.service;

import com.github.sanforjr2021.dao.GuildMemberDao;
import com.github.sanforjr2021.domain.BuyableRole;
import com.github.sanforjr2021.domain.GuildMember;
import com.github.sanforjr2021.domain.Rank;
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

}
