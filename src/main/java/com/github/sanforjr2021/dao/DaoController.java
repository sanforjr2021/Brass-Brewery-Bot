package com.github.sanforjr2021.dao;

import java.net.URLEncoder;
import java.sql.*;

public class DaoController {
    private static String url;
    public DaoController(String host, String database, String user, String password) {
        //Added encoding due to some strings not being encoded
        user = URLEncoder.encode(user);
        password = URLEncoder.encode(password);
        url = "jdbc:mysql://" + host + "/" + database + "?user=" + user + "&password=" + password;
    }

    protected static Connection openDBConnection() throws SQLException {
        return DriverManager.getConnection(url);
    }

}