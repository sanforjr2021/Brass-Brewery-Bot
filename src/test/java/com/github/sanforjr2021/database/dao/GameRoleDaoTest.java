package com.github.sanforjr2021.database.dao;

import com.github.sanforjr2021.database.domain.GameRole;
import com.github.sanforjr2021.util.ConfigController;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.fail;

public class GameRoleDaoTest {
    private ConfigController configController = new ConfigController();
    private final DaoController daoController = new DaoController(configController.getProperty("host"),
            configController.getProperty("database"),
            configController.getProperty("username"),
            configController.getProperty("password"));
    @Test
    public void getGameRole(){
        String id= "759166621696393237";
        try {
           GameRole gameRole = GameRoleDao.get(id);
            assert (id.equals(gameRole.getId()));
            assert (gameRole.getName().equals("Minecraft"));
        } catch (SQLException throwables) {
            fail("An SQL Error Occured");
            throwables.printStackTrace();
        }
    }
    @Test
    public void getNonexistantGameRole(){
        String id ="00000000000000000";
        try {
            GameRole gameRole = GameRoleDao.get(id);
            assert (gameRole ==null);
        } catch (SQLException throwables) {
            fail("An SQL Error Occured");
            throwables.printStackTrace();
        }
    }
    @Test
    public void getAllGameRoles(){
        try {
            ArrayList<GameRole> gameRoles = GameRoleDao.getAll();
            assert (gameRoles.size() > 0);
        } catch (SQLException throwables) {
            fail("An SQL Error Occured");
            throwables.printStackTrace();
        }
    }
    @Test
    public void getGameRoleByAlias(){
        String alias = "Minecraft";
        try {
            GameRole gameRole = GameRoleDao.getByAlias(alias);
            assert (gameRole.getId().equals("759166621696393237"));
        } catch (SQLException throwables) {
            fail("An SQL Error Occured");
            throwables.printStackTrace();
        }
    }
    @Test
    public void getNonexistantGameRoleByAlias(){
        String alias = "NotAnAlias";
        try {
            GameRole gameRole = GameRoleDao.getByAlias(alias);
            assert (gameRole == null);
        } catch (SQLException throwables) {
            fail("An SQL Error Occured");
            throwables.printStackTrace();
        }
    }
}
