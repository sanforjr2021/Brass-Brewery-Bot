package com.github.sanforjr2021.dao;

import com.github.sanforjr2021.domain.Rank;
import com.github.sanforjr2021.util.ConfigController;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.fail;

public class RankDaoTest {
    private ConfigController configController = new ConfigController();
    private final DaoController daoController = new DaoController(configController.getProperty("host"),
                configController.getProperty("database"),
                        configController.getProperty("username"),
                        configController.getProperty("password"));
    @Test
    public void getRankById(){
        String id = "758956924766126090";
        try {
            Rank rank = RankDao.get(id);
            System.out.println(rank.toString());
            assert (rank.getId().equals(id));
            assert (rank.getTier() == 1);
        } catch (SQLException throwables) {
            fail("A SQL Exception has occurred");
            throwables.printStackTrace();
        }
    }
    @Test
    public void getNonexistantRankbyId(){
        try {
            Rank rank = RankDao.get("00000000000000000");
            assert (rank == null);
        } catch (SQLException throwables) {
            fail("A SQL EXception has occured");
            throwables.printStackTrace();
        }
    }
    @Test
    public void getAllRanks(){
        try {
            ArrayList<Rank> ranks = RankDao.getAll();
            System.out.println(ranks.size());
            assert (ranks.size() > 0);
        } catch (SQLException throwables) {
            fail("A SQL EXception has occured");
            throwables.printStackTrace();
        }
    }
    @Test
    public void getRankByTier(){
        try {
            Rank rank = RankDao.get(1);
            System.out.println(rank.toString());
            assert (rank.getTier() == 1);
        } catch (SQLException throwables) {
            fail("A SQL EXception has occured");
            throwables.printStackTrace();
        }
    }
    @Test
    public void getNonexistantRankbyTier(){
        try {
            Rank rank = RankDao.get(-1);
            assert (rank == null);
        } catch (SQLException throwables) {
            fail("A SQL EXception has occured");
            throwables.printStackTrace();
        }
    }
}
