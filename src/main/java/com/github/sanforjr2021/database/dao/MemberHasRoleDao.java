package com.github.sanforjr2021.database.dao;

import com.github.sanforjr2021.database.domain.MemberHasRole;

import java.sql.*;
import java.util.ArrayList;

public class MemberHasRoleDao {
    /**
     * Get if a member has a certain role
     *
     * @param member
     * @param roleId
     * @return
     * @throws SQLException
     */
    public static MemberHasRole get(String member, String roleId) throws SQLException {
        Connection connection = DaoController.openDBConnection();
        MemberHasRole memberHasRole; //Object type : name
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM MemberHasRole WHERE Member = ? AND Role = ?");
        statement.setString(1, member);
        statement.setString(2, roleId);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            memberHasRole = new MemberHasRole(resultSet.getString(1), resultSet.getString(2), resultSet.getLong(3));
            connection.close();
            return memberHasRole;
        } else {
            connection.close();
            return null;
        }
    }

    /**
     * Get all memberHasRoles past the current date
     *
     * @param epochTime
     * @return
     * @throws SQLException
     */
    public static ArrayList<MemberHasRole> getAllOld(long epochTime) throws SQLException {
        ArrayList<MemberHasRole> membersHasRoles = new ArrayList<MemberHasRole>();
        Connection connection = DaoController.openDBConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM MemberHasRole WHERE ExpirationDate < ?");
        statement.setLong(1, epochTime);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            membersHasRoles.add(new MemberHasRole(resultSet.getString(1), resultSet.getString(2), resultSet.getLong(3)));
        }
        connection.close();
        return membersHasRoles;
    }

    /**
     *  Write a memberHasRole to the database
     * @param memberHasRole
     * @return
     */
    public static int write(MemberHasRole memberHasRole) {
        int numRowsChanged = 0;
        try {
            Connection connection = DaoController.openDBConnection();
            PreparedStatement statement = connection.prepareStatement("INSERT INTO MemberHasRole (Member, Role, ExpirationDate)" +
                    "VALUES(?, ?, ?)");
            statement.setString(1, memberHasRole.getGuildMemberId());
            statement.setString(2, memberHasRole.getRoleId());
            statement.setLong(3, memberHasRole.getDate());
            numRowsChanged = statement.executeUpdate();
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return numRowsChanged;
    }

    /**
     *  Deletes a memberHasRole from the database
     * @param memberHasRole
     * @return
     */
    public static int delete(MemberHasRole memberHasRole) {
        int numRowsChanged = 0;
        try {
            Connection connection = DaoController.openDBConnection();
            PreparedStatement statement = connection.prepareStatement("DELETE FROM MemberHasRole WHERE Member = ? AND Role = ?");
            statement.setString(1, memberHasRole.getGuildMemberId());
            statement.setString(2, memberHasRole.getRoleId());
            numRowsChanged = statement.executeUpdate();
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return numRowsChanged;
    }
}
