package sanford.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URLEncoder;
import java.sql.*;
import java.util.ArrayList;

public class SQLServerHandler {
    private static Connection conn;
    private static String url;
    private static final Logger logger = LoggerFactory.getLogger("DatabaseLog");

    public SQLServerHandler(String host, String database, String user, String password) {
        try {
            //Added encoding due to some strings not being encoded
            user = URLEncoder.encode(user);
            password = URLEncoder.encode(password);
            url = "jdbc:mysql://" + host + "/" + database + "?user=" + user + "&password=" + password;
            openDBConnection();
            closeDBConnection();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private static ResultSet createResultSet(String query) throws SQLException {
        openDBConnection();
        Statement statement = conn.createStatement();
        return statement.executeQuery(query);
    }

    private static int getColumnCountFromResultSet(ResultSet rs) throws SQLException {
        ResultSetMetaData metadata = rs.getMetaData();
        return metadata.getColumnCount();
    }

    private static void executeSQLStatement(String query) throws SQLException {
            Statement statement = conn.createStatement();
            statement.executeUpdate(query);
    }

    private static void openDBConnection() throws SQLException {
        conn = DriverManager.getConnection(url);
    }

    public static void closeDBConnection() throws SQLException {
        conn.close();
    }


    ////////////////////////////
    //Member Queries
    ///////////////////////////

    public static MemberDataContainer getMemberDataContainer(String memberID) throws SQLException {
        String query = "SELECT *\n" +
                "       FROM GuildMember\n" +
                "       WHERE GuildMember.ID = " + memberID;
        ResultSet rs = createResultSet(query);
        int columnCount = getColumnCountFromResultSet(rs);
        MemberDataContainer memberDataContainer = null;
        while (rs.next()) {
            StringBuilder row = new StringBuilder();
            for (int i = 1; i <= columnCount; i++) {
                row.append(rs.getString(i)).append(", ");
            }
            memberDataContainer = new MemberDataContainer(row.toString());
        }
        closeDBConnection();
        if(memberDataContainer == null){
            logger.warn("Could not find user " + memberID + " in the database. Creating a new user instead");
            memberDataContainer = new MemberDataContainer(memberID, 0);
            addMember(memberDataContainer);
        }

        return memberDataContainer;
    }

    public static void addMember(MemberDataContainer memberDataContainer) throws SQLException {
        openDBConnection();
        String query =  "INSERT INTO GuildMember (GuildMember.ID, GuildMember.Currency)\n" +
                            "VALUES (" +memberDataContainer.getId() + " ,"+ memberDataContainer.getCurrency() + ")";
        executeSQLStatement(query);
        closeDBConnection();
    }

    ////////////////////////
    //Role Queries
    ///////////////////////

    public static ArrayList<RoleDataContainer> getRoles() throws SQLException {
        String query = "SELECT * \n" +
                            "FROM Role "+
                            "ORDER BY Role.RoleTier ASC, Role.Cost DESC, Role.Name DESC";
        ResultSet rs = createResultSet(query);
        int columnCount = getColumnCountFromResultSet(rs);
        ArrayList<RoleDataContainer> roleDataContainerList = new ArrayList<RoleDataContainer>();
        while (rs.next()) {
            StringBuilder row = new StringBuilder();
            for (int i = 1; i <= columnCount; i++) {
                row.append(rs.getString(i)).append(", ");
            }//end of for
            RoleDataContainer roleDataContainer = new RoleDataContainer(row.toString());
            roleDataContainerList.add(roleDataContainer);
        }//end of while
        closeDBConnection();
        return roleDataContainerList;
    }

    public static RoleDataContainer getRoleById(String id) throws  SQLException{
        String query = "SELECT * " +
                "FROM Role " +
                " WHERE Role.ID = " + id;
        ResultSet rs = createResultSet(query);
        int columnCount = getColumnCountFromResultSet(rs);
        rs.next();
        StringBuilder row = new StringBuilder();
        for (int i = 1; i <= columnCount; i++) {
            row.append(rs.getString(i)).append(", ");
        }//end of for
        closeDBConnection();
        return new RoleDataContainer(row.toString());
    }

    public static String getRoleIDbyRoleName(String name) throws SQLException {
        String query = "SELECT RoleID " +
                " FROM RoleNames " +
                " WHERE RoleNames.RoleName = '" + name +"'";
        ResultSet rs = createResultSet(query);
        int columnCount = getColumnCountFromResultSet(rs);
        String solution;
        if(rs.next() == false){
            solution = "INVALID";
        }
        else{
            solution = rs.getString(1);
        }
        closeDBConnection();
        return solution;

    }

    //////////////////////////
    //MemberHasRole Queries
    /////////////////////////

    public static void main (String[] args) throws SQLException {
        SQLServerHandler sqlServerHandler = new SQLServerHandler("204.44.81.104:3306", "s14_distilled_spirits", "u14_2k9mqAZvcx", "wZvxjQ9@gkv6va5H+sn.11+S");
        /*
        sqlServerHandler.getRoles();

        System.out.println("Getting user");
        MemberDataContainer memberDataContainer = sqlServerHandler.getMemberDataContainer("100000000000000001");
        System.out.println(memberDataContainer.toString());
        System.out.println("Getting list of roles");
        ArrayList<RoleDataContainer> roleDataContainersList = sqlServerHandler.getRoles();
        System.out.println("Total of  "+ roleDataContainersList.size() + " roles.");
        for(RoleDataContainer roleDataContainers : roleDataContainersList){
            System.out.println(roleDataContainers.shopString());
        }

         */
        System.out.println(getRoleIDbyRoleName("drag"));
    }
}