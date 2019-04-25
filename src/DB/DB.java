package DB;

import javafx.application.Application;
import javafx.collections.FXCollections;

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

    public static void connectDB(String sqlStatment , String yourSelect ,String Search) {
    /*
        try{
        // (1) load the driver into memory
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

        // (2) establish Connection
        Connection con= DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=dbCampersRental","sa","123456");

        // (3) create statement
        Statement stmt = con.createStatement();

        // (4) execute SQL statement
        ResultSet rs = stmt.executeQuery(sqlStatment);

        while (rs.next()){
            String name = rs.getString(yourSelect);
            //System.out.println(name);
            if (name.equals(Search)){
                System.out.println("found it = " + name);
            }
        }

        // (5) close the statement & connection
        rs.close();
        stmt.close();
        con.close();

        // (6) Done
        System.out.println( "Execute Done ");
        }

        catch(Exception e){
            e.printStackTrace();
            }
    */
    }    private DB(){

    }
    static {
        Properties props = new Properties();
        String fileName = "src/Database/db.properties";
        InputStream input;
        try {
            input = new FileInputStream(fileName);
            props.load(input);
            port = props.getProperty("port", "1433");
            databaseName = props.getProperty("databaseName");
            userName = props.getProperty("userName", "sa");
            password = props.getProperty("password");
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver"); //Step 2
        } catch (IOException | ClassNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }

    public static ArrayList<Object[]> select(String statement) {
        conn = null;
        ps = null;
        ArrayList<Object[]> returnArrayList = new ArrayList<>();
        try {
            //Step 3 open connection
            connect();
            //Step 4 Execute query
            ps = conn.prepareStatement(statement);
            rs = ps.executeQuery();
            Integer noOfColumns = rs.getMetaData().getColumnCount();
            //Structuring return data
            while (rs.next()){
                Object[] tempArray = new Object[noOfColumns];
                for (int i = 0; i < tempArray.length; i++) {
                    tempArray[i] = rs.getObject(i+1);
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

    private static void connect(){
        try {
            conn = DriverManager.getConnection("jdbc:sqlserver://localhost:"+port+";databaseName="+databaseName, userName, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private static void cleanUp(){
        try{
            if (ps != null){
                ps.close();
            }
            if(conn != null){
                conn.close();
            }
            if (rs != null){
                rs.close();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}




