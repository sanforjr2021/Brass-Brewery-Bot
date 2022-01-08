package com.github.sanforjr2021.database.dao;

import com.github.sanforjr2021.database.domain.GameRole;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class GameRoleDao {

    public static GameRole get(String id) throws SQLException {
        Connection connection = DaoController.openDBConnection();
        GameRole gameRole; //Object type : name
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM Games WHERE ID = ?");
        statement.setString(1, id);
        System.out.println(statement);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            gameRole = new GameRole(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5));
        } else {
            gameRole = null;
        }
        connection.close();
        return gameRole;
    }

    public static ArrayList<GameRole> getAll() throws SQLException {
        ArrayList<GameRole> gameRoles = new ArrayList<GameRole>();
        Connection connection = DaoController.openDBConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM Games ORDER BY id ASC");
        ResultSet resultSet = statement.executeQuery();
        while(resultSet.next()){
            gameRoles.add( new GameRole(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5)));
        }
        connection.close();
        return gameRoles;
    }
}
