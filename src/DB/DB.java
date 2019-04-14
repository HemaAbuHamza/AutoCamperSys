package DB;

import javafx.application.Application;
import javafx.collections.FXCollections;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;


public class DB {

    public static void connectDB(String sqlStatment , String yourSelect ,String Search) {

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
    }
}




