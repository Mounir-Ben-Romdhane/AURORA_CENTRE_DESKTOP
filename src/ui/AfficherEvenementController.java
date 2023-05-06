/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import animatefx.animation.Pulse;
import entite.evenement;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import service.EvenementService;

/**
 * FXML Controller class
 *
 * @author asus
 */
public class AfficherEvenementController implements Initializable {

    @FXML
    private GridPane gridEvent;
    
    EvenementService Ev=new EvenementService();
    private Button btnLogOutClient;
    @FXML
    private VBox vBoxDashboardClient;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        afficherEvenement();
    }    
    
    
    public void afficherEvenement(){
         try {
            List<evenement> evenements = Ev.recupererEvenement();
            gridEvent.getChildren().clear();
            int row = 0;
            int column = 0;
            for (int i = 0; i < evenements.size(); i++) {
                //chargement dynamique d'une interface
                FXMLLoader loader = new FXMLLoader(getClass().getResource("affichage.fxml"));
                AnchorPane pane = loader.load();
               
                //passage de parametres
                AffichageController controller = loader.getController();
                controller.setEvennement(evenements.get(i));
                controller.setIdevent(evenements.get(i).getId());
                gridEvent.add(pane, column, row);
                column++;
                if (column > 1) {
                    column = 0;
                    row++;
                }
               
            }
        } catch (SQLException | IOException ex) {
            System.out.println(ex.getMessage());
        }   
    }


    private void retourA(ActionEvent event) {
        try {
              Stage sg=new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Evenement.fxml"));
            Parent root = loader.load();
            Scene sc = new Scene(root);
            sg.setTitle("eveneement");
            sg.setScene(sc);
            sg.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }


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


    private void handleEntertainment(ActionEvent event) {
        try {
              Stage sg=new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("jeu.fxml"));
            Parent root = loader.load();
            Scene sc = new Scene(root);
            sg.setTitle("eveneement");
            sg.setScene(sc);
            sg.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void handleQuiz(ActionEvent event) {
          try {
              Stage sg=new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("quiz_evenement.fxml"));
            Parent root = loader.load();
            Scene sc = new Scene(root);
            sg.setTitle("eveneement");
            sg.setScene(sc);
            sg.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void handleQuiz1(ActionEvent event) {
        try {
              Stage sg=new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("quiz2.fxml"));
            Parent root = loader.load();
            Scene sc = new Scene(root);
            sg.setTitle("eveneement");
            sg.setScene(sc);
            sg.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void handleQuiz3(ActionEvent event) {
        try {
              Stage sg=new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("quiz3.fxml"));
            Parent root = loader.load();
            Scene sc = new Scene(root);
            sg.setTitle("eveneement");
            sg.setScene(sc);
            sg.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    
    
}


