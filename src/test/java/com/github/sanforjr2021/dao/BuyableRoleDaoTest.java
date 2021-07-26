package com.github.sanforjr2021.dao;

import com.github.sanforjr2021.domain.BuyableRole;
import com.github.sanforjr2021.util.ConfigController;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.fail;

public class BuyableRoleDaoTest {
    private ConfigController configController = new ConfigController();
    private final DaoController daoController = new DaoController(configController.getProperty("host"),
            configController.getProperty("database"),
            configController.getProperty("username"),
            configController.getProperty("password"));
    @Test
    public void getBuyableRoleById() { //write this test
        try {
            BuyableRole buyableRole  = BuyableRoleDao.get("758955004404170782");
            System.out.println(buyableRole.toString());
            assert (buyableRole.getId().equals("758955004404170782"));
            assert (buyableRole.getName().equals("Red"));
        } catch (SQLException throwables) {
            fail("Test had an unexpected SQL error");
            throwables.printStackTrace();
        }

    }
    @Test
    public void getNonexistantBuyableRolebyId() { //and write this test - check for if points is 0 or returns null based upon the code
        try {
            BuyableRole buyableRole = BuyableRoleDao.get("000000000000000001");
            assert(buyableRole == null);
        } catch (SQLException throwables) {
            fail("Test had an unexpected SQL error");
            throwables.printStackTrace();
        }
    }
    @Test
    public void getBuyableRoleByName() { //write this test
        try {
            BuyableRole buyableRole  = BuyableRoleDao.getByName("Red");
            System.out.println(buyableRole.toString());
            assert (buyableRole.getId().equals("758955004404170782"));
            assert (buyableRole.getName().equals("Red"));
        } catch (SQLException throwables) {
            fail("Test had an unexpected SQL error");
            throwables.printStackTrace();
        }

    }
    @Test
    public void getNonexistantBuyableRolebyName() { //and write this test - check for if points is 0 or returns null based upon the code
        try {
            BuyableRole buyableRole = BuyableRoleDao.getByName("PurplePinkBlackOrange");
            assert(buyableRole == null);
        } catch (SQLException throwables) {
            fail("Test had an unexpected SQL error");
            throwables.printStackTrace();
        }
    }

    //public void get
    @Test
    public void testGetAll() {
        List<BuyableRole> buyableRoleList;
        try {
            buyableRoleList = BuyableRoleDao.getAll();
            assert (buyableRoleList != null);
            assert (buyableRoleList.size() > 1);
        } catch (SQLException throwables) {
            fail("Test had an unexpected SQL error");
            throwables.printStackTrace();
        }
    }
}
