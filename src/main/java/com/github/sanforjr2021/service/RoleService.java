package com.github.sanforjr2021.service;

import com.github.sanforjr2021.dao.RankDao;
import com.github.sanforjr2021.domain.Rank;

import java.sql.SQLException;

public class RoleService {
    public static Rank getNextRank(Rank rank){
        Rank nextRank;
        int nextRankTier = rank.getTier()+1;
        try {
            nextRank = RankDao.get(nextRankTier);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            nextRank = null;
        }
        return nextRank;
    }
    
}
