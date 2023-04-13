/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ui;

import animatefx.animation.BounceIn;
import animatefx.animation.FadeIn;
import animatefx.animation.Flash;
import animatefx.animation.Hinge;
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
public class DashboardAdminController implements Initializable {

    @FXML
    private Label hello;
    @FXML
    private Button btnAddUser;
    @FXML
    private Button btnListUsers;
    @FXML
    private Button btnProfil;
    @FXML
    private Pane pnlStatus;
    @FXML
    private Label lplStatus;
    @FXML
    private Label lplStatusMini;
    @FXML
    private Button btnLogOut;
    @FXML
    private Button btnHome;
    @FXML
    private VBox vBoxDashboardAdmin;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        vBoxDashboardAdmin.getChildren().removeAll();
            vBoxDashboardAdmin.setVisible(false);
    }   
    
    @FXML
    private void handleClicks(ActionEvent event) throws IOException{
        if(event.getSource() == btnAddUser){
            lplStatusMini.setText("/Home/Users");
            lplStatus.setText("USERS");
            pnlStatus.setBackground(new Background(new BackgroundFill(Color.rgb(250, 128, 114),CornerRadii.EMPTY,Insets.EMPTY)));
            vBoxDashboardAdmin.setVisible(true);
            new Shake(pnlStatus).play();
            Parent fxml = FXMLLoader.load(getClass().getResource("AddUser.fxml"));
                vBoxDashboardAdmin.getChildren().removeAll();
                vBoxDashboardAdmin.getChildren().setAll(fxml);
        }else if(event.getSource() == btnListUsers){
            lplStatusMini.setText("/Home/List Users");
            lplStatus.setText("List Users");
            vBoxDashboardAdmin.setVisible(true);
            pnlStatus.setBackground(new Background(new BackgroundFill(Color.rgb(234, 102, 94),CornerRadii.EMPTY,Insets.EMPTY)));
            new BounceIn(pnlStatus).play();
            Parent fxml = FXMLLoader.load(getClass().getResource("afficheUsers.fxml"));
                vBoxDashboardAdmin.getChildren().removeAll();
                vBoxDashboardAdmin.getChildren().setAll(fxml);
        }else if(event.getSource() == btnProfil){
            lplStatusMini.setText("/Home/Profil");
            lplStatus.setText("Profil Settings");
            vBoxDashboardAdmin.setVisible(true);
            pnlStatus.setBackground(new Background(new BackgroundFill(Color.rgb(227, 115, 131),CornerRadii.EMPTY,Insets.EMPTY)));
            new Swing(pnlStatus).play();
            Parent fxml = FXMLLoader.load(getClass().getResource("Profil.fxml"));
                vBoxDashboardAdmin.getChildren().removeAll();
                vBoxDashboardAdmin.getChildren().setAll(fxml);
        }else if(event.getSource() == btnHome){
            lplStatusMini.setText("/Home");
            lplStatus.setText("HOME");
            pnlStatus.setBackground(new Background(new BackgroundFill(Color.rgb(252,165,165),CornerRadii.EMPTY,Insets.EMPTY)));
            new Pulse(pnlStatus).play();
            vBoxDashboardAdmin.getChildren().removeAll();
            vBoxDashboardAdmin.setVisible(false);
        }
    }

    @FXML
    private void handleLogOut(ActionEvent event) throws IOException {
                             Stage currentStage = (Stage) btnLogOut.getScene().getWindow();
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
