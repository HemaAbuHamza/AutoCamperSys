package Application;

import DB.DB;
import Domain.Campers;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;


public class Controller {
    @FXML
    Label priceLabel;
    @FXML
    TextArea username;
    @FXML
    TextArea password;
    @FXML
    TextArea name;
    @FXML
    TextArea street;
    @FXML
    TextArea zipcode;
    @FXML
    TextArea phone;
    @FXML
    TextArea email;
    @FXML
    TextArea residence;
    @FXML
    TextArea birth;
    @FXML
    TextArea drNum;
    @FXML
    TextArea pass;
    @FXML
    TextField startWeek;
    @FXML
    TextField endWeek;

    @FXML
    MenuButton camperClass = new MenuButton();
    @FXML
    MenuButton Insurance = new MenuButton();
    @FXML
    TableView searchTable = new TableView();

    @FXML
    private
    TableColumn<Campers, String> typeCol = new TableColumn<>();
    @FXML
    private
    TableColumn<Campers, String> brandCol = new TableColumn<>();
    @FXML
    private
    TableColumn<Campers, Date> dateCol = new TableColumn<>();
    @FXML
    private
    TableColumn<Campers, Long> millageCol = new TableColumn<>();

    private LocalDate localDate = LocalDate.now();
    private String classType;
    private double camperPrice = 0;
    private double insurancePrice = 0;
    private double totalPrice = 0;
    private String insuranceType = "Basic";
    private ArrayList<String> CamperPlate = new ArrayList();
    private String camperPlates;
    private int userID;

    /**
     *Method that takes in user specified requirements and compiles a list of "Campers" to be suggested
     *Called by {@link #handleSearch(ActionEvent)} ()}
     *
     * @param StartWeek Date marking the start of the requested renting period, gained from user input in YYYYWW format (201913 for thirteenth week of year 2019)
     * @param EndWeek Date marking the end of the requested renting period, gained from user input in YYYYWW format (201905 for fifth week of year 2019)
     * @param CamperClass The tier or "Class" of Campers to be offered. Comes in three flavours: "Basic", "Standard" and "Fancy"
     *
     */
    private static ArrayList<Campers> getCampers(Integer StartWeek, Integer EndWeek, String CamperClass) {
        ArrayList<Campers> returnCampers = new ArrayList<>();

        try {
            String query = ("select * from tblCampers where fldCamperPlate not in (Select fldCamperPlate from tblCamperStatus where fldWeek BETWEEN " + StartWeek + " and " + EndWeek + ") and fldClass = '" + CamperClass + "'");


            ArrayList<Object[]> camperQuery = DB.select(query);

            for (Object[] objects : camperQuery) {
                String camperPlate = (String) objects[0];
                String camperType = (String) objects[1];
                String camperBrand = (String) objects[2];
                Date camperDate = (Date) objects[3];
                Long millage = (Long) objects[4];

                Campers campers = new Campers(camperPlate, camperType, camperBrand, camperDate, millage);

                returnCampers.add(campers);
            }


        } catch (NullPointerException e) {
            e.printStackTrace();


        }
        return returnCampers;

    }

    /**
     * Authentication method called by {@link #handleSignLogin(ActionEvent)} ()}
     * Compares user input against data from our DB and upon successful login sets the current user (userID)
     *
     */
    private boolean checkLogin() {

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=dbCampersRental", "sa", "123456");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("Select * from tblUsers");

            while (rs.next()) {

                String email = rs.getString("fldEmail");
                if (email.equals(username.getText())) {
                    System.out.println("Email Found in DB " + email);
                    String pass = rs.getString("fldPassHash");
                    if (pass.equals(password.getText())) {
                        System.out.println("Password Match the Email...Login Done ");
                        userID = rs.getInt("fldUserID");
                        System.out.println(userID);
                        return true;
                    } else System.out.println("Access Denied , wrong password");
                }
            }
            rs.close();
            stmt.close();
            con.close();
            System.out.println("Execute Done");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Method called by the button "Login", calls {@link #checkLogin()} for authentication and upon success opens the reservation window
     *
     */
    public void handleSignLogin(ActionEvent event) throws Exception {
        if (checkLogin()) {
            System.out.println("new window");
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/UI/reservations.fxml"));
                Parent root = (Parent) fxmlLoader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else System.out.println("no window");
    }

    /**
     *Method that opens up the new user registration window upon a "Sign up" button is pressed
     */
    public void handleSignUP(ActionEvent event) throws Exception {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/UI/create_customer.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Takes in values entered by user from the TextAreas and calls {@link DB#execute(String)}()} to insert them into the database
     * Called by "Create" button in create_customer window
     */
    public void register(ActionEvent actionEvent) {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=dbCampersRental", "sa", "123456");
            Statement stmt = con.createStatement();

            String getName = name.getText();
            String getStreet = street.getText();
            String getZipcode = zipcode.getText();
            String getPhone = phone.getText();
            String getEmail = email.getText();
            String getPass = pass.getText();
            String getRes = residence.getText();
            String getDrNum = drNum.getText();
            String getBirth = birth.getText();

            String query1 = "INSERT INTO tblUsers (fldName, fldStreet, fldZipcode, fldPhoneNo , fldEmail , fldPassHash , fldResidence , fldDrivingLicence ,fldDateOfBirth,fldLoyalty)"
                    + "VALUES ('" + getName + "','" + getStreet + "','" + getZipcode + "','" + getPhone + "','" + getEmail + "','" + getPass + "','" + getRes + "','" + getDrNum + "','" + getBirth + "','0')";

            stmt.executeUpdate(query1);
            stmt.close();
            con.close();

            System.out.println("Done insert to DB");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Method called upon a "Search" button press. Calls {@link #getCampers(Integer, Integer, String)} ()} to fill an Observable list which it then displays in a TableView.
     */
    public void handleSearch(ActionEvent actionEvent) {
        Integer startWeekToInt = Integer.parseInt(startWeek.getText());
        Integer endWeekToInt = Integer.parseInt(endWeek.getText());
        ObservableList<Campers> campers = FXCollections.observableArrayList(getCampers(startWeekToInt, endWeekToInt, camperClass.getText()));

        for (Campers camperLines : campers) {
            CamperPlate.add(camperLines.getCamperPlate());
        }

        searchTable.setItems(campers);

        typeCol.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getType()));
        brandCol.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getBrand()));
        dateCol.setCellValueFactory(param -> new SimpleObjectProperty(param.getValue().getFactoryDate()));
        millageCol.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getMillage()));
        searchTable.getColumns().setAll(typeCol, brandCol, dateCol, millageCol);

    }

    /**
     * Method called upon button "Select" is pressed
     * Retrieves information about which of ours "Campers" has been selected, for this purpose we kept the select type at "Single"
     */
    public void handleSelect(ActionEvent event) {
        camperPlates = CamperPlate.get((searchTable.getSelectionModel().getSelectedIndex()));
        System.out.println(camperPlates);
        calculatePrice();
    }

    /**
     * Method called upon button "Pay" is pressed
     * This method should be able to insert data to four different tables (utilizing {@link DB#execute(String)} and {@link DB#select(String)}).
     * Sadly we haven't managed to get it to full functionality in time because of unsuitable SELECT statement used and problems integrating code from different versions of SDK
     * In current version instead of inserting data into a database we are only writing them out
     */
    public void handlePay(ActionEvent event) {
        int reservationIDStoring = 0;
        int paymentIDStoring = 0;
        int RentedWeek = 0;

        String queryInsertReservation = "Insert into tblReservations (fldUserID, fldCamperPlate, fldResDate,fldStartWeek,fldReturnWeek,fldClass,fldInsurance,fldCODriver) values( " + userID + " , '" + camperPlates + "','" + localDate + "' ," + startWeek.getText() + "," + endWeek.getText() + ",'" + classType + "','" + insuranceType + "', null)";
        String queryGetReservationID = "Select TOP 1 fldReservationID from tblReservations group by fldReservationID order by  fldReservationID desc";
        String queryInsertPayment = "insert into tblPayments values(" + reservationIDStoring + ",'" + localDate + "' ," + totalPrice + "," + (totalPrice - insurancePrice) + " , 1)";
        String queryGetPaymentID = "Select TOP 1 fldPaymentID from tblReservations group by fldPaymentID order by  fldPaymentID desc";
        String queryCreateFeesOnPayment = "insert into tblFees values(" + paymentIDStoring + ",'" + localDate + "', 'Insurance' , " + insurancePrice + ")";
        String queryCamperStatus = "INSERT INTO tblCamperStatus values('" + CamperPlate + "', " + RentedWeek + ", 'Rented out')";

        /*
        DB.execute(queryInsertReservation);
        reservationIDStoring = Integer.parseInt(String.valueOf(DB.select(queryGetReservationID)));
        DB.execute(queryInsertPayment);
        paymentIDStoring = Integer.parseInt(String.valueOf(DB.select(queryGetPaymentID)));
        DB.execute(queryCreateFeesOnPayment);

        for (RentedWeek = Integer.parseInt(startWeek.getText()); RentedWeek < Integer.parseInt(endWeek.getText()); RentedWeek++) {
            DB.execute(queryCamperStatus);
        }
        */

        System.out.println(queryInsertReservation);
        System.out.println("\n" + userID + ", " + camperPlates + "," + localDate + ", " + startWeek.getText() + ", " + endWeek.getText() + ", " + classType + ", " + insuranceType + ", null" + "\n");

        System.out.println(queryInsertPayment);
        System.out.println("\n" + reservationIDStoring + ", " + localDate + ", " + totalPrice + ", " + (totalPrice - insurancePrice) + ", 1" + "\n");

        System.out.println(queryCreateFeesOnPayment);
        System.out.println("\n" + CamperPlate + ", " + RentedWeek + ", Rented out" + "\n");

        System.out.println(queryCamperStatus);
        for (RentedWeek = Integer.parseInt(startWeek.getText()); RentedWeek < Integer.parseInt(endWeek.getText()); RentedWeek++) {
            System.out.println();
        }

    }

    /**
     * Method that calculates total price and views it as needed, called in ideal case upon every change made by user that would affect it
     */
    private void calculatePrice() {
        totalPrice = (camperPrice * (Integer.parseInt(endWeek.getText()) - Integer.parseInt(startWeek.getText()))) + insurancePrice;
        priceLabel.setText(String.valueOf(totalPrice));
    }

    /**
     *Simple method used to set MenuButton text and price accordingly to chosen tier
     */
    public void HandleBasic(ActionEvent event) {
        camperClass.setText("Basic");
        classType = "Basic";
        camperPrice = 200;
        calculatePrice();
    }

    /**
     *Simple method used to set MenuButton text and price accordingly to chosen tier
     */
    public void HandleStandard(ActionEvent event) {
        camperClass.setText("Standard");
        classType = "Standard";
        camperPrice = 400;
        calculatePrice();
    }

    /**
     *Simple method used to set MenuButton text and price accordingly to chosen tier
     */
    public void HandleLuxury(ActionEvent event) {
        camperClass.setText("Luxury");
        classType = "Luxury";
        camperPrice = 600;
        calculatePrice();
    }

    /**
     *Simple method used to set MenuButton text and price accordingly to chosen tier
     */
    public void HandleBasicInsurance(ActionEvent event) {
        Insurance.setText("Basic");
        insurancePrice = 50;
        calculatePrice();
        insuranceType = "Basic";
    }

    /**
     *Simple method used to set MenuButton text and price accordingly to chosen tier
     */
    public void HandleExpensiveInsurance(ActionEvent event) {
        Insurance.setText("Super Cover Plus");
        insurancePrice = 100;
        insuranceType = "SCP";
        calculatePrice();
    }
}