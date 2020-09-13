package sanford.data;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URLEncoder;
import java.sql.*;

public class SQLServerHandler {
    private Connection conn;
    private String url;
    private MysqlDataSource sds;
    private static final Logger logger = LoggerFactory.getLogger("PropertiesLog");

    public SQLServerHandler(String host, String database, String user, String password) {
        /*
        HOST= IP:Port
        Database= Database name
        User = username
        password = password
         */
        try {
            //Added encoding due to some strings already being encoded
            user = URLEncoder.encode(user);
            password = URLEncoder.encode(password);
            url = "jdbc:mysql://" + host + "/" + database + "?user=" + user + "&password=" + password;
            conn = DriverManager.getConnection(url);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    //Demo code for now to show a select statement works with the database when testing
    public void seeRoles() {
        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("SELECT *\n" +
                    "FROM Role;");
            while (rs.next()) {
                System.out.println(rs.getString(4));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}