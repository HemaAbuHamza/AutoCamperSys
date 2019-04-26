package DB;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;


public class DB {
    private static String port;
    private static String databaseName;
    private static String userName;
    private static String password;

    private static Connection conn;
    private static PreparedStatement ps;
    private static ResultSet rs;

    static {
        Properties props = new Properties();
        String fileName = "src/DB/db.properties";
        InputStream input;
        try {
            input = new FileInputStream(fileName);
            props.load(input);
            port = props.getProperty("port", "1433");
            databaseName = props.getProperty("databaseName");
            userName = props.getProperty("userName", "sa");
            password = props.getProperty("password");
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Method called upon whenever a SELECT statements is sent to our DB
     *
     * @return returns ArrayList<Object[]> so we could easily store entire table and access each value
     */
    public static ArrayList<Object[]> select(String statement) {
        conn = null;
        ps = null;
        ArrayList<Object[]> returnArrayList = new ArrayList<>();
        try {
            connect();
            ps = conn.prepareStatement(statement);
            rs = ps.executeQuery();
            Integer noOfColumns = rs.getMetaData().getColumnCount();
            while (rs.next()) {
                Object[] tempArray = new Object[noOfColumns];
                for (int i = 0; i < tempArray.length; i++) {
                    tempArray[i] = rs.getObject(i + 1);
                }
                returnArrayList.add(tempArray);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cleanUp();
        }
        return returnArrayList;
    }

    /**
     * Method called upon whenever a non-SELECT statement is sent to our DB
     *
     */
    public static boolean execute(String statement) {
        conn = null;
        ps = null;
        try {
            connect();
            ps = conn.prepareStatement(statement);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            cleanUp();
        }
        return true;
    }

    /**
     * method that extablishes a connection between our app and DB
     */
    private static void connect() {
        try {
            conn = DriverManager.getConnection("jdbc:sqlserver://localhost:" + port + ";databaseName=" + databaseName, userName, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method that makes sure that no connections are kept open
     */
    private static void cleanUp() {
        try {
            if (ps != null) {
                ps.close();
            }
            if (conn != null) {
                conn.close();
            }
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}




