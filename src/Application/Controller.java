package Application;

import javafx.fxml.*;
import javafx.scene.Parent;
import javafx.scene.control.TextArea;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;


public class Controller {
    @FXML
    TextArea username;
    @FXML
    TextArea password;
    @FXML  TextArea name;
    @FXML TextArea street;
    @FXML TextArea zipcode;
    @FXML TextArea phone;
    @FXML TextArea email;
    @FXML TextArea resdience;
    @FXML TextArea birth;
    @FXML TextArea drNum;
    @FXML TextArea pass;



    public boolean checkLogin(boolean doneLogin) {

        try {
            // (1) load the driver into memory
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            // (2) establish Connection
            Connection con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=dbCampersRental", "sa", "123456");
            // (3) create statement
            Statement stmt = con.createStatement();
            // (4) execute SQL statement
            ResultSet rs = stmt.executeQuery("Select * from tblUsers" );

            while (rs.next()) {
                String email = rs.getString("fldEmail");
                if (email.equals(username.getText())) {
                    System.out.println("Email Found in DB " + email);
                    String pass = rs.getString("fldPassHash");
                    if(pass.equals(password.getText())){
                        System.out.println("Password Match the Email...Login Done ");
                        return doneLogin == true;
                    }
                    else System.out.println("Access Deni , wrong password");
                }
            }
            // (5) close the statement & connection
            rs.close();
            stmt.close();
            con.close();
            // (6) Done
            System.out.println("Execute Done ");
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }

    //Handel the login button , search for match customer email address and password
    public void handleSignLogin(ActionEvent event) throws Exception {
        if (checkLogin(true)){
            System.out.println("new window");
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/UI/sample.fxml"));
                Parent root = (Parent) fxmlLoader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else System.out.println("no window");
    }

    //Handel the procedures to Create Customers informations
    public void handleSignUP(ActionEvent event) throws Exception {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/UI/create_cutomer.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void handelCreate(ActionEvent actionEvent) {
        try {
            // (1) load the driver into memory
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            // (2) establish Connection
            Connection con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=dbCampersRental", "sa", "123456");
            // (3) create statement
            Statement stmt = con.createStatement();

            // (4) execute SQL statement
            //values
            String getName = name.getText();
            String getStreet = street.getText();
            String getZipcode = zipcode.getText();
            String getPhone = phone.getText();
            String getEmail = email.getText();
            String getPass = pass.getText();
            String getRes = resdience.getText();
            String getDrNum = drNum.getText();
            String getBirth = birth.getText();

            String query1 =  "INSERT INTO tblUsers (fldName, fldStreet, fldZipcode, fldPhoneNo , fldEmail , fldPassHash , fldResidence , fldDrivingLicence ,fldDateOfBirth,fldLoyalty)"
                    + "VALUES ('"+getName+"','"+getStreet+"','"+getZipcode+"','"+getPhone+"','"+getEmail+"','"+getPass+"','"+getRes+"','"+getDrNum+"','"+getBirth+"','0')";

            stmt.executeUpdate(query1);
            // (5) close the statement & connection
            stmt.close();
            con.close();
            // (6) Done
            System.out.println("Done insert to DB" );
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}

    /*

    (1)//Check if username input is in the DB
        do {
            String data = DB.getData();

            if (data.equals(checkUsername)) {
                System.out.println("* Welcome " + checkUsername + " **");
                break;
            } else {
                System.out.println("User not found");
                break;
            }
        } while (true);


        (2)// Insert into DB

        String sql = "INSERT INTO tblCustomer VALUES('" + fldAddress + "'," + fldZipCode + ",'" + fldName + "'," + fldNumber + ",'" + fldEmail + "',0,0,0,0,'" + fldOwnerType + "')";

        // Execute when we press CREATE BUTTON
        public void handleCreateCustomer(ActionEvent event) throws Exception {
            DB.insertSQL(sql);
            DB.insertSQL(paymentInsert);
        }


        (3)//Updates the DB with the new payment value
        DB.updateSQL("UPDATE tblName SET fldName = " + waterNew + " WHERE fldAddress = '" + address + "'");

        (4)//Search for only specific values that has a zipcode, that the user inputs
        System.out.println("Type in the zipcode where you want to know the water consumption");
        int zipChoice = in.nextInt();
        int allWater = 0;
        int data1 = 0;
        DB.selectSQL("SELECT fldOldWaterValue FROM tblCustomer WHERE fldZipCode = " + zipChoice + "");
        do {
            String data = DB.getData();
            if (data.equals(DB.NOMOREDATA)) {
                break;
            } else {
                //Adds together all information from only ones listed with zipcode
                data1 = Integer.parseInt(data);
                allWater = allWater + data1;
            }
        } while (true);
        System.out.printf("The sum of the water consumption in zipcode %d is: ", zipChoice);
        System.out.println(allWater);
     */


