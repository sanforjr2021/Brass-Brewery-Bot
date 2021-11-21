package com.github.sanforjr2021.database.service;

import com.github.sanforjr2021.database.dao.GuildMemberDao;
import com.github.sanforjr2021.database.dao.RankDao;
import com.github.sanforjr2021.database.domain.BuyableRole;
import com.github.sanforjr2021.database.domain.GuildMember;
import com.github.sanforjr2021.database.domain.Rank;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.github.sanforjr2021.BrassBreweryBot.GUILD;

public class RoleService {
    /**
     * Get the next rank for the user. If it returns null, the user is at the highest tier possible.
     *
     * @param user
     * @return
     * @throws SQLException
     */
    public static Rank getNextRank(User user) throws SQLException {
        Member member = GUILD.getMember(user);
        List<Role> roleList = member.getRoles();
        ArrayList<Rank> rankArrayList = RankDao.getAll();
        int highestRank = 0;
        for (int i = 0; i < rankArrayList.size(); i++) {
            boolean hasFound = false;
            Rank rank = rankArrayList.get(i);
            for (int x = 0; x < roleList.size() && hasFound == false; x++) {
                if (rank.getId().equals( roleList.get(x).getId())) {
                    hasFound = true;
                    if (highestRank < rank.getTier()) {
                        highestRank = rank.getTier();
                    }
                }
            }
        }
        try {
            return RankDao.get(highestRank + 1);
        } catch (SQLException throwables) { //this means they are at the highest rank
            return null;
        }
    }

    public static boolean addBuyableRole(User user, BuyableRole role) throws SQLException {
        boolean hasAddedRole = false;
        GuildMember member = GuildMemberDao.get(user.getId());
        if (member.getCurrency() >= role.getCost()) {
            member.setCurrency(member.getCurrency() - role.getCost());
            GuildMemberDao.write(member);
            GUILD.addRoleToMember(GUILD.getMember(user), GUILD.getRoleById(role.getId()));
            hasAddedRole = true;
        }
        return hasAddedRole;
    }

    public static boolean addRank(User user, Rank rank) throws SQLException {
        boolean hasAddedRank = false;
        GuildMember member = GuildMemberDao.get(user.getId());
        if (member.getCurrency() >= rank.getCost()) {
            member.setCurrency(member.getCurrency() - rank.getCost());
            GuildMemberDao.update(member);
            GUILD.addRoleToMember(GUILD.getMember(user), GUILD.getRoleById(rank.getId())).queue();
            hasAddedRank = true;
        }
        return hasAddedRank;
    }

    public static boolean userHaveRole(User user, String roleID) {
        List<Role> roleList = GUILD.getMember(user).getRoles();
        for (Role role : roleList) {
            if (role.getId().equals(roleID)) {
                return true;
            }
        }
        return false;
    }

}
