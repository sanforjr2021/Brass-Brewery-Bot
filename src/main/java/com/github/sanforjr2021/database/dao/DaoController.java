package com.github.sanforjr2021.database.dao;

import java.sql.*;

public class DaoController {
    private static String url;
    public DaoController(String host, String database, String user, String password) {
        url = "jdbc:mariadb://" + host +"/" + database + "?user=" + user +"&password=" + password;
    }

    protected static Connection openDBConnection() throws SQLException {
        return DriverManager.getConnection(url);
    }

}