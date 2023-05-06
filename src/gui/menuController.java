/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author mfmma
 */
public class menuController implements Initializable {
    
    @FXML
    private Button btnService;

    @FXML
    private Button btnReservation;
    
     
    @Override
    public void initialize(URL url, ResourceBundle rb) {}
 
    
        @FXML
    public void handleButtonService(MouseEvent event) throws IOException {
        AnchorPane formService = new AnchorPane();
        formService.getChildren().add(FXMLLoader.load(getClass().getResource("/gui/ServiceBack.fxml")));
        Scene currentScene = btnService.getScene();
        Stage currentStage = (Stage) currentScene.getWindow();
        Scene newScene = new Scene(formService, currentScene.getWidth(), currentScene.getHeight());
        currentStage.setScene(newScene);
    }
        @FXML
    public void handleButtonReservation(MouseEvent event) throws IOException {
        AnchorPane formService = new AnchorPane();
        formService.getChildren().add(FXMLLoader.load(getClass().getResource("/gui/ReservationBack.fxml")));
        Scene currentScene = btnReservation.getScene();
        Stage currentStage = (Stage) currentScene.getWindow();
        Scene newScene = new Scene(formService, currentScene.getWidth(), currentScene.getHeight());
        currentStage.setScene(newScene);
    }
}
