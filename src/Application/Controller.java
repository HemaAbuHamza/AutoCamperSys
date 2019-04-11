package Application;

import javafx.fxml.*;
import javafx.scene.Parent;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.media.*;
import javafx.scene.control.Button;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import java.io.*;
import java.net.*;
import java.util.ResourceBundle;
import javafx.application.Application;


public class Controller {
    @FXML
    private Button signUp;

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

    public void handleSignLogin(ActionEvent event) throws Exception {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/UI/sample.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch(Exception e) {
            e.printStackTrace();
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

}
