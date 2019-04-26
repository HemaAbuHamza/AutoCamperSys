package Application;

import DB.DB;
import Domain.Campers;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.*;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;


import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;


public class Controller {
    @FXML
    Label priceLabel;
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
    @FXML
    TextField startWeek;
    @FXML
    TextField endWeek;
    @FXML
    MenuButton camperClass = new MenuButton();
    @FXML
    MenuButton Insurance = new MenuButton();
    String insuranceType = "Basic";
    String setBasic = "Basic";
    String setSuperCoverPlus = "Super cover plus";
    String setStandard = "Standard";
    String setLuxury = "Luxury";
    @FXML
    TableView searchTable = new TableView();
    @FXML
    TableColumn<Campers,String> typeCol = new TableColumn<>();
    @FXML
    TableColumn<Campers,String> brandCol = new TableColumn<>();
    @FXML
    TableColumn<Campers, Date> dateCol = new TableColumn<>();
    @FXML
    TableColumn<Campers,Long> millageCol = new TableColumn<>();
    LocalDate localDate = LocalDate.now();
    public String classType;
    public int userID;
    public double camperPrice = 0;
    public double insurancePrice = 0;
    public double totalPrice = 0;
    public static ArrayList<Campers> getCampers(Integer StartWeek, Integer EndWeek, String CamperClass) {
        ArrayList<Campers> returnCampers = new ArrayList<>();

        try {
            String query = ("select * from tblCampers where fldCamperPlate not in (Select fldCamperPlate from tblCamperStatus where fldWeek BETWEEN " + StartWeek + " and " + EndWeek + ") and fldClass = '" + CamperClass +"'");


            ArrayList<Object[]> camperQuery = DB.select(query);

                for (Object[] objects : camperQuery) {
                        String camperPlate = (String) objects[0];
                        String camperType = (String) objects[1];
                        String camperBrand = (String) objects[2];
                        Date camperDate = (Date) objects[3];
                        Long millage = (Long) objects[4];

                    Campers campers = new Campers(camperPlate,camperType,camperBrand,camperDate,millage);

                    returnCampers.add(campers);
                }





            } catch (NullPointerException e) {
            e.printStackTrace();


        }
        return returnCampers;

    }

    public boolean checkLogin (boolean doneLogin) {

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
                        userID = rs.getInt("fldUserID");
                        System.out.println(userID);
                        return doneLogin == true;
                    }
                    else System.out.println("Access Denied , wrong password");
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    ArrayList<String> CamperPlate = new ArrayList();
    public void handleSearch (ActionEvent actionEvent){
        Campers camper;
        System.out.println(startWeek.getText() + " " + endWeek.getText());
        Integer startWeekToInt = Integer.parseInt(startWeek.getText());
        Integer endWeekToInt = Integer.parseInt(endWeek.getText());
        ObservableList<Campers> campers = FXCollections.observableArrayList(getCampers(startWeekToInt,endWeekToInt,camperClass.getText()) );

        for (Campers camperLines: campers) {
            CamperPlate.add(camperLines.getCamperPlate());
        }

        searchTable.setItems(campers);

        typeCol.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getType()));
        brandCol.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getBrand()));
        dateCol.setCellValueFactory(param -> new SimpleObjectProperty(param.getValue().getFactoryDate()));
        millageCol.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getMillage()));
        searchTable.getColumns().setAll(typeCol, brandCol, dateCol, millageCol);

    }
    public String camperPlates;
    public void handleSelect(ActionEvent event) {
         camperPlates = CamperPlate.get((searchTable.getSelectionModel().getSelectedIndex()));
        System.out.println(camperPlates);
        calculatePrice();


    }

    public void handleClass(ActionEvent actionEvent) {

        }

    public void handleInsurance(ActionEvent event) {

    }

    public void handlePay(ActionEvent event) {
        int reservationIDStoring = 0;
        int paymentIDStoring = 0;

        String queryInsertReservation = "Insert into tblReservations (fldUserID, fldCamperPlate, fldResDate,fldStartWeek,fldReturnWeek,fldClass,fldInsurance,fldCODriver) values( 1 , '" + camperPlates + "','" + localDate + "' ," + startWeek.getText() + "," + endWeek.getText() + ",'" + classType + "','" + insuranceType +"', null)";
        String queryGetReservationID = "Select TOP 1 fldReservationID from tblReservations group by fldReservationID order by  fldReservationID desc";
        String queryPaymentReservationID = "insert into tblPayments values("+ reservationIDStoring+ ",'" + localDate + "' ," + totalPrice + "," + (totalPrice-insurancePrice) + " , 1)";
        String queryGetPaymentID = "Select TOP 1 fldPaymentID from tblReservations group by fldPaymentID order by  fldPaymentID desc";
        String queryCreateFeesOnPayement ="insert into tblFees values(" +paymentIDStoring +",'" + localDate + "', 'Insurance' , " + insurancePrice+ ")";
        String queryCamperStatus;


        DB.execute(queryInsertReservation);
        ArrayList<Object[]> selectQuery = DB.select(queryGetReservationID);
        int selectQueryToInt = selectQuery.;
        DB.execute(queryPaymentReservationID);
        paymentIDStoring = Integer.parseInt(String.valueOf(DB.select(queryGetPaymentID)));
        DB.execute(queryCreateFeesOnPayement);

        for (int RentedWeek =Integer.parseInt(startWeek.getText()) ; RentedWeek < Integer.parseInt(endWeek.getText()); RentedWeek++) {
            queryCamperStatus = "INSERT INTO tblCamperStatus values('" + CamperPlate + "', " + RentedWeek + ", 'Rented out')";
            DB.execute(queryCamperStatus);
        }
    }

    public void calculatePrice(){
        totalPrice = (camperPrice*(Integer.parseInt(endWeek.getText())-Integer.parseInt(startWeek.getText()))) + insurancePrice;
        priceLabel.setText(String.valueOf(totalPrice));
    }

    public void HandleBasic(ActionEvent event) {
        camperClass.setText(setBasic);
        classType = setBasic;
        camperPrice = 200;
        calculatePrice();
    }

    public void HandleStandard(ActionEvent event) {
        camperClass.setText(setStandard);
        classType = setStandard;
        camperPrice = 400;
        calculatePrice();
    }

    public void HandleLuxury(ActionEvent event) {
        camperClass.setText(setLuxury);
        classType = setLuxury;
        camperPrice = 600;
        calculatePrice();
    }

    public void HandleBasicInsurance(ActionEvent event) {
        Insurance.setText(setBasic);
        insurancePrice = 50;
        calculatePrice();
        insuranceType = "Basic";
    }

    public void HandleExpensiveInsurance(ActionEvent event) {
        Insurance.setText(setSuperCoverPlus);
        insurancePrice = 100;
        calculatePrice();
        insuranceType = "SCP";
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


