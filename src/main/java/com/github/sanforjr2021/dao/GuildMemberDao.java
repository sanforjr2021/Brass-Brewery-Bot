package com.github.sanforjr2021.dao;

import com.github.sanforjr2021.domain.GuildMember;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GuildMemberDao{
    /**
     * Attempts to get the GuildMember from the database. If the user does not exist, it creates a
     * new user that is not connected to the database starting with 0 currency.
     * @param id
     * @return
     * @throws SQLException
     */
    public static GuildMember get(String id) throws SQLException {
        Connection connection = DaoController.openDBConnection();
        GuildMember guildMember; //Object type : name
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM GuildMember WHERE ID = ?");
        statement.setString(1, id);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            guildMember = new GuildMember(resultSet.getString(1), resultSet.getInt(2));
        } else {
            guildMember = new GuildMember(id);
            write(guildMember);
        }
        connection.close();
        return guildMember;
    }

    /**
     * Returns a list of all GuildMembers
     * @return
     * @throws SQLException
     */
    public static ArrayList<GuildMember> getAll() throws SQLException {
        ArrayList<GuildMember> guildMembers = new ArrayList<GuildMember>();
        Connection connection = DaoController.openDBConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM GuildMember");
        ResultSet resultSet = statement.executeQuery();
        while(resultSet.next()){
            guildMembers.add(new GuildMember(resultSet.getString(1), resultSet.getInt(2)));
        }
        connection.close();
        return guildMembers;
    }

    /**
     * Creates a Guildmember in the database. It will return a 1 if it updates and a 0 if it fails to do so.
     * @param guildMember
     * @return
     */
    public static int write(GuildMember guildMember) {
        int numRowsChanged = 0;
        try {
            Connection connection = DaoController.openDBConnection();
        PreparedStatement statement = connection.prepareStatement("INSERT INTO GuildMember (id, Currency)" +
                "VALUES( ?, ?)");
        statement.setString(1, guildMember.getId());
        statement.setInt(2, guildMember.getCurrency());
        numRowsChanged = statement.executeUpdate();
        connection.close();
        } catch(SQLIntegrityConstraintViolationException throwables) {
            System.out.println("User with ID " + guildMember.getId() +" already exist in the Database");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return numRowsChanged;
    }

    /**a
     * Updates a GuildMember with the current values It will return 1 if it updates and a 0 if fails.
     * @param guildMember
     * @return
     */
    public static int update(GuildMember guildMember) {
        int numRowsChanged = 0;
        try{
            Connection connection = DaoController.openDBConnection();
            PreparedStatement statement = connection.prepareStatement("UPDATE GuildMember SET id = ?, currency = ? WHERE id = ?");
            statement.setString(1, guildMember.getId());
            statement.setInt(2, guildMember.getCurrency());
            statement.setString(3, guildMember.getId());
            numRowsChanged = statement.executeUpdate();
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return numRowsChanged;



    }

    /**
     * Deletes a GuildMember from the databse and returns a 1. If it fails, it returns a 0.
     * @param guildMember
     * @return
     */
    public static int delete(GuildMember guildMember){
        int numRowsChanged = 0;
        try {
            Connection connection = DaoController.openDBConnection();
            PreparedStatement statement = connection.prepareStatement("DELETE FROM GuildMember WHERE id = ?");
            statement.setString(1, guildMember.getId());
            numRowsChanged = statement.executeUpdate();
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return  numRowsChanged;

    }

}