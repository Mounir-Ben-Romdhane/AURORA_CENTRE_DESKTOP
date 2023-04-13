/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ui;

import animatefx.animation.BounceIn;
import animatefx.animation.Pulse;
import animatefx.animation.Shake;
import animatefx.animation.Swing;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author MSI
 */
public class DashboardClientController implements Initializable {

    @FXML
    private Label lplStatus;
    @FXML
    private Label lplStatusMini;
    @FXML
    private Button btnHomeClient;
    @FXML
    private Button btnProfilClient;
    @FXML
    private Button btnLogOutClient;
    @FXML
    private Pane pnlStatusClient;
    @FXML
    private VBox vBoxDashboardClient;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        vBoxDashboardClient.getChildren().removeAll();
            vBoxDashboardClient.setVisible(false);
    }    

    @FXML
    private void handleClicksClient(ActionEvent event) throws IOException {
        if(event.getSource() == btnProfilClient){
            lplStatusMini.setText("/Home/Profil");
            lplStatus.setText("Profil Settings");
            vBoxDashboardClient.setVisible(true);
            pnlStatusClient.setBackground(new Background(new BackgroundFill(Color.rgb(227, 115, 131),CornerRadii.EMPTY,Insets.EMPTY)));
            new Swing(pnlStatusClient).play();
            Parent fxml = FXMLLoader.load(getClass().getResource("Profil.fxml"));
                vBoxDashboardClient.getChildren().removeAll();
                vBoxDashboardClient.getChildren().setAll(fxml);
        }else if(event.getSource() == btnHomeClient){
            lplStatusMini.setText("/Home");
            lplStatus.setText("HOME");
            pnlStatusClient.setBackground(new Background(new BackgroundFill(Color.rgb(252,165,165),CornerRadii.EMPTY,Insets.EMPTY)));
            new Pulse(pnlStatusClient).play();
            vBoxDashboardClient.getChildren().removeAll();
            vBoxDashboardClient.setVisible(false);
        }
    }

    @FXML
    private void handleLogOutClient(ActionEvent event) throws IOException {
        Stage currentStage = (Stage) btnLogOutClient.getScene().getWindow();
                            currentStage.close();
                
                            Stage stage = new Stage();
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("Main.fxml"));

                            Parent root = loader.load();
                           // tfEmailSignIn.getScene().setRoot(root);

                            Scene scene = new Scene(root);
                            scene.setFill(Color.TRANSPARENT);
                            stage.setTitle("Dashboard Admin");
                            stage.initStyle(StageStyle.TRANSPARENT);
                            stage.setScene(scene);
                            stage.show();
                            
                           new Pulse(root).play();
    }
    
}
