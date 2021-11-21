package com.github.sanforjr2021.database.dao;

import com.github.sanforjr2021.database.domain.Rank;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RankDao {
    public static Rank get(String id) throws SQLException {
        Connection connection = DaoController.openDBConnection();
        Rank rank; //Object type : name
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM Ranks WHERE ID = ?");
        statement.setString(1, id);
        System.out.println(statement);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            rank = new Rank(resultSet.getString(1),
                    resultSet.getInt(2),
                    resultSet.getString(3),
                    resultSet.getInt(4));
        } else {
            rank = null;
        }
        connection.close();
        return rank;
    }

    public static ArrayList<Rank> getAll() throws SQLException {
        ArrayList<Rank> ranks = new ArrayList<Rank>();
        Connection connection = DaoController.openDBConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM Ranks ORDER BY Name ASC");
        ResultSet resultSet = statement.executeQuery();
        while(resultSet.next()){
            ranks.add( new Rank(resultSet.getString(1),
                            resultSet.getInt(2),
                            resultSet.getString(3),
                            resultSet.getInt(4)));
        }
        connection.close();
        return ranks;
    }

    public static Rank get(int tier) throws SQLException{
        Connection connection = DaoController.openDBConnection();
        Rank rank; //Object type : name
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM Ranks WHERE tier = ?");
        statement.setInt(1, tier);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            rank = new Rank(resultSet.getString(1), resultSet.getInt(2),
                    resultSet.getString(3), resultSet.getInt(4));
        } else {
            rank = null;
        }
        connection.close();
        return rank;
    }
}
