package UI;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Auto Camper System");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        //just for test2
    }


    public static void main(String[] args) {
        launch(args);
    }
}

