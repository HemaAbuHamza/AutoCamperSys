package UI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

//Calling other packages
import DB.DB;
import Domain.Methods;


public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        primaryStage.setTitle("Auto Camper System");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        //just for test3
    }

    public static void main(String[] args) {
        //Calling Domain package
        //Methods test = new Methods();
        //test.testMethod();

        //Calling DB package
        //DB connect = new DB();
        //DB.connectDB();

        launch(args);
    }
}

