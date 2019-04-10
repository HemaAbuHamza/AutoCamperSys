package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;

public class DB {

    public static void connectDB() {
        try{
        // (1) load the driver into memory
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        // (2) establish Connection
        Connection con= DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=Campers","sa","123456");
        // (3) create statement
        Statement stmt = con.createStatement();
        // (4) execute SQL statement
        //stmt.execute("Select * from tblCampers");
        // (5) close the statement
        stmt.close();
        // (6) close the connection
        con.close();
        //Done
        System.out.println("We are Connected to the DB");
        }

        catch(Exception e){
            e.printStackTrace();
            }
    }


}




