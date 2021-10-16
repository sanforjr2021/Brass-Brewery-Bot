package com.github.sanforjr2021.dao;

import java.net.URLEncoder;
import java.sql.*;

public class DaoController {
    private static String url;
    public DaoController(String host, String database, String user, String password) {
        url = "jdbc:mariadb://" + host +"/" + database + "?user=" + user +"&password=" + password;
        System.out.println(url);
    }

    protected static Connection openDBConnection() throws SQLException {
        return DriverManager.getConnection(url);
    }

}