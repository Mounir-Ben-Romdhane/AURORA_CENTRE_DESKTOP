/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ui;

import animatefx.animation.BounceIn;
import animatefx.animation.Pulse;
import animatefx.animation.Shake;
import animatefx.animation.Swing;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
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
import service.UserService;

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
    @FXML
    private Button btnEvent;

    private Button btnAddReservationClient;
    @FXML
    private Button btnService;
    @FXML
    private Button btnListReservationsClient;
    @FXML
    private Button btnAjoutRec;
    @FXML
    private Button btnListRec;
    @FXML
    private Button btnProduit;
    @FXML
    private Button btncommandee;
    @FXML
    private Button btnfacture;
    @FXML
    private Button btnParticiper;

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
            pnlStatusClient.setBackground(new Background(new BackgroundFill(Color.rgb(201, 179, 150),CornerRadii.EMPTY,Insets.EMPTY)));
            new Swing(pnlStatusClient).play();
            Parent fxml = FXMLLoader.load(getClass().getResource("Profil.fxml"));
                vBoxDashboardClient.getChildren().removeAll();
                vBoxDashboardClient.getChildren().setAll(fxml);
        }else if(event.getSource() == btnHomeClient){
            lplStatusMini.setText("/Home");
            lplStatus.setText("HOME");
            pnlStatusClient.setBackground(new Background(new BackgroundFill(Color.rgb(174, 127, 66),CornerRadii.EMPTY,Insets.EMPTY)));
            new Pulse(pnlStatusClient).play();
            vBoxDashboardClient.getChildren().removeAll();
            vBoxDashboardClient.setVisible(false);
        }else if(event.getSource() == btnEvent){
           lplStatusMini.setText("/Home/Event");
            lplStatus.setText("Evenements");
            vBoxDashboardClient.setVisible(true);
            pnlStatusClient.setBackground(new Background(new BackgroundFill(Color.rgb(201, 179, 150),CornerRadii.EMPTY,Insets.EMPTY)));
            new Swing(pnlStatusClient).play();
            Parent fxml = FXMLLoader.load(getClass().getResource("afficherEvenement.fxml"));
                vBoxDashboardClient.getChildren().removeAll();
                vBoxDashboardClient.getChildren().setAll(fxml);
        }else if(event.getSource() == btnAddReservationClient){
            lplStatusMini.setText("/Home/ Ajouter Réservations");
            lplStatus.setText("Ajouter Réservation");
            vBoxDashboardClient.setVisible(true);
            pnlStatusClient.setBackground(new Background(new BackgroundFill(Color.rgb(234, 102, 94),CornerRadii.EMPTY,Insets.EMPTY)));
            new BounceIn(pnlStatusClient).play();
            Parent fxml = FXMLLoader.load(getClass().getResource("AjouterReservation.fxml"));
                vBoxDashboardClient.getChildren().removeAll();
                vBoxDashboardClient.getChildren().setAll(fxml);
        }else if(event.getSource() == btnService){
            lplStatusMini.setText("/Home/Services");
            lplStatus.setText("List Services");
            vBoxDashboardClient.setVisible(true);
            pnlStatusClient.setBackground(new Background(new BackgroundFill(Color.rgb(197, 168, 131),CornerRadii.EMPTY,Insets.EMPTY)));
            new Shake(pnlStatusClient).play();
            Parent fxml = FXMLLoader.load(getClass().getResource("ServiceFront.fxml"));
                vBoxDashboardClient.getChildren().removeAll();
                vBoxDashboardClient.getChildren().setAll(fxml);
        }else if(event.getSource() == btnListReservationsClient){
            lplStatusMini.setText("/Home/List Reservations");
            lplStatus.setText("Reservations");
            vBoxDashboardClient.setVisible(true);
            pnlStatusClient.setBackground(new Background(new BackgroundFill(Color.rgb(211, 174, 132),CornerRadii.EMPTY,Insets.EMPTY)));
            new BounceIn(pnlStatusClient).play();
            Parent fxml = FXMLLoader.load(getClass().getResource("AfficheReservations.fxml"));
                vBoxDashboardClient.getChildren().removeAll();
                vBoxDashboardClient.getChildren().setAll(fxml);
        }else if(event.getSource() == btnAjoutRec){
            lplStatusMini.setText("/Home/Reclamations");
            lplStatus.setText("Add Reclamation");
            vBoxDashboardClient.setVisible(true);
            pnlStatusClient.setBackground(new Background(new BackgroundFill(Color.rgb(197, 168, 131),CornerRadii.EMPTY,Insets.EMPTY)));
            new Shake(pnlStatusClient).play();
            Parent fxml = FXMLLoader.load(getClass().getResource("AddReclamation.fxml"));
                vBoxDashboardClient.getChildren().removeAll();
                vBoxDashboardClient.getChildren().setAll(fxml);
        }else if(event.getSource() == btnListRec){
            lplStatusMini.setText("/Home/Reclamations");
            lplStatus.setText("List Reclamation");
            vBoxDashboardClient.setVisible(true);
            pnlStatusClient.setBackground(new Background(new BackgroundFill(Color.rgb(201, 179, 150),CornerRadii.EMPTY,Insets.EMPTY)));
            new BounceIn(pnlStatusClient).play();
            Parent fxml = FXMLLoader.load(getClass().getResource("ListFrontReclamation.fxml"));
                vBoxDashboardClient.getChildren().removeAll();
                vBoxDashboardClient.getChildren().setAll(fxml);
        }else if(event.getSource() == btnProduit){
           lplStatusMini.setText("/Home/Products");
            lplStatus.setText("Products");
            vBoxDashboardClient.setVisible(true);
            pnlStatusClient.setBackground(new Background(new BackgroundFill(Color.rgb(201, 179, 150),CornerRadii.EMPTY,Insets.EMPTY)));
            new Swing(pnlStatusClient).play();
            Parent fxml = FXMLLoader.load(getClass().getResource("Commande.fxml"));
                vBoxDashboardClient.getChildren().removeAll();
                vBoxDashboardClient.getChildren().setAll(fxml);
        }else if(event.getSource() == btncommandee){
           lplStatusMini.setText("/Home/Commande");
            lplStatus.setText("Commande");
            vBoxDashboardClient.setVisible(true);
            pnlStatusClient.setBackground(new Background(new BackgroundFill(Color.rgb(197, 168, 131),CornerRadii.EMPTY,Insets.EMPTY)));
            new Swing(pnlStatusClient).play();
            Parent fxml = FXMLLoader.load(getClass().getResource("Details.fxml"));
                vBoxDashboardClient.getChildren().removeAll();
                vBoxDashboardClient.getChildren().setAll(fxml);
        }else if(event.getSource() == btnfacture ){
           lplStatusMini.setText("/Facture");
            lplStatus.setText("Facture");
            vBoxDashboardClient.setVisible(true);
            pnlStatusClient.setBackground(new Background(new BackgroundFill(Color.rgb(197, 168, 131),CornerRadii.EMPTY,Insets.EMPTY)));
            new Shake(pnlStatusClient).play();
            Parent fxml = FXMLLoader.load(getClass().getResource("facture.fxml"));
                vBoxDashboardClient.getChildren().removeAll();
                vBoxDashboardClient.getChildren().setAll(fxml);
        }else if(event.getSource() == btnParticiper ){
           lplStatusMini.setText("/Participation");
            lplStatus.setText("Participation");
            vBoxDashboardClient.setVisible(true);
            pnlStatusClient.setBackground(new Background(new BackgroundFill(Color.rgb(174, 127, 66),CornerRadii.EMPTY,Insets.EMPTY)));
            new Swing(pnlStatusClient).play();
            Parent fxml = FXMLLoader.load(getClass().getResource("AfficheParticipation.fxml"));
                vBoxDashboardClient.getChildren().removeAll();
                vBoxDashboardClient.getChildren().setAll(fxml);
        }
    }

    @FXML
    private void handleLogOutClient(ActionEvent event) throws IOException, Exception {
        Stage currentStage = (Stage) btnLogOutClient.getScene().getWindow();
                            currentStage.close();
                            UserService ps = new UserService();
                            ps.rememberMe("", 0);
                
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
