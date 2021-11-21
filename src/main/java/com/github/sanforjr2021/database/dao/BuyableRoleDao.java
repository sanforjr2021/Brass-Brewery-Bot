package com.github.sanforjr2021.database.dao;

import com.github.sanforjr2021.database.domain.BuyableRole;

import java.sql.*;
import java.util.ArrayList;

public class BuyableRoleDao{
    /**
     * Attempts to get the BuyableRole from the database. If the user does not exist, it creates a
     * new user that is not connected to the database starting with 0 currency.
     * @param id
     * @return
     * @throws SQLException
     */
    public static BuyableRole get(String id) throws SQLException {
        Connection connection = DaoController.openDBConnection();
        BuyableRole buyableRole; //Object type : name
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM BuyableRoles WHERE ID = ?");
        statement.setString(1, id);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            buyableRole = new BuyableRole(resultSet.getString(1), resultSet.getInt(2), resultSet.getString(3));
        } else {
           buyableRole = null;
        }
        connection.close();
        return buyableRole;
    }

    /**
     * Returns a list of all BuyableRoles
     * @return
     * @throws SQLException
     */
    public static ArrayList<BuyableRole> getAll() throws SQLException {
        ArrayList<BuyableRole> buyableRoles = new ArrayList<BuyableRole>();
        Connection connection = DaoController.openDBConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM BuyableRoles ORDER BY Name ASC");
        ResultSet resultSet = statement.executeQuery();
        while(resultSet.next()){
            buyableRoles.add( new BuyableRole(resultSet.getString(1), resultSet.getInt(2), resultSet.getString(3)));
        }
        connection.close();
        return buyableRoles;
    }

    public static BuyableRole getByName(String name) throws SQLException{
        Connection connection = DaoController.openDBConnection();
        BuyableRole buyableRole; //Object type : name
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM BuyableRoles WHERE Name = ?");
        statement.setString(1, name);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            buyableRole = new BuyableRole(resultSet.getString(1), resultSet.getInt(2), resultSet.getString(3));
        } else {
            buyableRole = null;
        }
        connection.close();
        return buyableRole;
    }
}
