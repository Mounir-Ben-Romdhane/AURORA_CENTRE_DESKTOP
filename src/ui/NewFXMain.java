/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;


import animatefx.animation.Pulse;
import java.io.IOException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.stage.StageStyle;
import service.UserService;

/**
 *
 * @author wiemhjiri
 */
public class NewFXMain extends Application {
    
    @Override
    public void start(Stage primaryStage) throws IOException, Exception {
        
        UserService ps = new UserService();
        if(ps.getRememberMe()=="trueAdmin"){
        Parent root=FXMLLoader.load(getClass().getResource("DashboardAdmin.fxml"));
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        primaryStage.setScene(scene);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.show();
        
        new Pulse(root).play();
        }else if(ps.getRememberMe()=="trueUser"){
            Parent root=FXMLLoader.load(getClass().getResource("DashboardClient.fxml"));
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        primaryStage.setScene(scene);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.show();
        
        new Pulse(root).play();
        }
        else{
            Parent root=FXMLLoader.load(getClass().getResource("Main.fxml"));
        
        
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        primaryStage.setScene(scene);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.show();
        
        new Pulse(root).play();
        }
        
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
