package com.github.sanforjr2021.database.dao;

import com.github.sanforjr2021.database.domain.DiscordInvite;

import java.sql.*;
import java.util.ArrayList;

public class DiscordInviteDAO {
    public static DiscordInvite get(String id) throws SQLException {
        Connection connection = DaoController.openDBConnection();
        DiscordInvite discordInvite;//Object type : name
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM DiscordInvites WHERE InviteID = ?");
        statement.setString(1, id);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            discordInvite = new DiscordInvite(resultSet.getString(1), resultSet.getString(2), resultSet.getInt(3));
        } else {
            discordInvite = null;
        }
        connection.close();
        return discordInvite;
    }

    public static ArrayList<DiscordInvite> getAll() throws SQLException {
        ArrayList<DiscordInvite> discordInvites = new ArrayList<DiscordInvite>();
        Connection connection = DaoController.openDBConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM DiscordInvites");
        ResultSet resultSet = statement.executeQuery();
        while(resultSet.next()){
            discordInvites.add(new DiscordInvite(resultSet.getString(1), resultSet.getString(2), resultSet.getInt(3)));
        }
        connection.close();
        return discordInvites;
    }

    public static int write(DiscordInvite discordInvite) {
        int numRowsChanged = 0;
        try {
            Connection connection = DaoController.openDBConnection();
            PreparedStatement statement = connection.prepareStatement("INSERT INTO DiscordInvites (InviteID, GameRole, InviteCount)" +
                    "VALUES( ?, ?, ?)");
            statement.setString(1, discordInvite.getInviteCode());
            statement.setString(2, discordInvite.getRoleId());
            statement.setInt(3, discordInvite.getInviteCount());
            numRowsChanged = statement.executeUpdate();
            connection.close();
        } catch(SQLIntegrityConstraintViolationException throwables) {
            System.out.println("User with ID " + discordInvite.getInviteCode() +" already exist in the Database");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return numRowsChanged;
    }

    public static int update(DiscordInvite discordInvite) {
        int numRowsChanged = 0;
        try{
            Connection connection = DaoController.openDBConnection();
            PreparedStatement statement = connection.prepareStatement("UPDATE DiscordInvites SET InviteID = ?, GameRole = ?, InviteCount = ? WHERE InviteID = ?");
            statement.setString(1, discordInvite.getInviteCode() );
            statement.setString(2, discordInvite.getRoleId());
            statement.setInt(3, discordInvite.getInviteCount());
            statement.setString(4, discordInvite.getInviteCode());
            numRowsChanged = statement.executeUpdate();
            connection.close();
        } catch (SQLException throwables1) {
            write(discordInvite);
        }
        return numRowsChanged;
    }
}
