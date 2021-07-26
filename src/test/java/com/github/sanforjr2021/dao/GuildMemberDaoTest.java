package com.github.sanforjr2021.dao;

import com.github.sanforjr2021.domain.GuildMember;
import com.github.sanforjr2021.util.ConfigController;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.fail;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GuildMemberDaoTest {
    private ConfigController configController = new ConfigController();
    private final DaoController daoController = new DaoController(configController.getProperty("host"),
            configController.getProperty("database"),
            configController.getProperty("username"),
            configController.getProperty("password"));
    @Test
    @Order(1)
    public void testWrite() {
        int expectedChangedRows = 1;
        int changedRows = GuildMemberDao.write(new GuildMember("000000000000000000"));
        assert (expectedChangedRows == changedRows);
    }

    @Test
    @Order(2)
    public void testWriteExistingUser() {
        int expectedChangedRows = 0;
        int changedRows = GuildMemberDao.write(new GuildMember("000000000000000000"));
        assert (expectedChangedRows == changedRows);
    }

    @Test
    @Order(3)
    public void testUpdateExistingUser() {
        int expectedChangedRows = 1;
        int expectedUserCurrency = 5;
        int changedRows = GuildMemberDao.update(new GuildMember("000000000000000000", expectedUserCurrency));
        assert (expectedChangedRows == changedRows);
    }

    @Test
    @Order(4)
    public void updateNonexistentUser() {
        int expectedChangedRows = 0;
        int changedRows = GuildMemberDao.update(new GuildMember("-a000000000000000000", 0));
        assert (expectedChangedRows == changedRows);
    }

    @Test
    @Order(5)
    public void getExistingUser() { //write this test
        try {
            GuildMember guildMember = GuildMemberDao.get("000000000000000000");
            assert (guildMember.getId().equals("000000000000000000"));
            assert (guildMember.getCurrency() == 5);
        } catch (SQLException throwables) {
            fail("Test had an unexpected SQL error");
            throwables.printStackTrace();
        }

    }

    @Test
    @Order(6)
    public void getNonexistentUser() { //and write this test - check for if points is 0 or returns null based upon the code
        try {
            GuildMember guildMember = GuildMemberDao.get("000000000000000001");
            assert (guildMember.getId().equals("000000000000000001"));
            assert (guildMember.getCurrency() == 0);
        } catch (SQLException throwables) {
            fail("Test had an unexpected SQL error");
            throwables.printStackTrace();
        }
    }

    @Test
    @Order(7)
    public void testGetAll() {
        List<GuildMember> memberList;
        try {
            memberList = GuildMemberDao.getAll();
            assert (memberList != null);
            assert (memberList.size() > 1);
        } catch (SQLException throwables) {
            fail("Test had an unexpected SQL error");
            throwables.printStackTrace();
        }
    }

    @Test
    @Order(8)
    public void testDeleteUsersFromCreate() {
        int expectedChangedRows = 2;
        int changedRows = GuildMemberDao.delete(new GuildMember("000000000000000001", 0));
        changedRows += GuildMemberDao.delete(new GuildMember("000000000000000000", 5));
        assert (changedRows == expectedChangedRows);
    }

    @Test
    @Order(9)
    public void testDeleteNonexistentUser() {
        int expectedChangedRows = 0;
        int changedRows = GuildMemberDao.delete(new GuildMember("000000000000000001", 0));
        assert (changedRows == expectedChangedRows);
    }
}