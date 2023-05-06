package pidevdesktop;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class mainFx extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
     //   Parent root = FXMLLoader.load(getClass().getResource("/gui/AjouterService.fxml"));
        
        Parent root2 = FXMLLoader.load(getClass().getResource("/ui/ServiceFront.fxml"));

        primaryStage.setTitle("PiDev");
        primaryStage.setScene(new Scene(root2));
        primaryStage.show();
    }
}