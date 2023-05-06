/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import animatefx.animation.Pulse;
import entite.Facture;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import service.FactureService;

/**
 * FXML Controller class
 *
 * @author Samar
 */
public class FactureController implements Initializable {

    @FXML
    private GridPane gridfacture;
    FactureService Ev=new FactureService();
    private Button backCom;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        afficherFacture();
    }    

    public void afficherFacture() {
         try {
            List<Facture> Factures = Ev.readAll();
            gridfacture.getChildren().clear();
            int row = 0;
            int column = 0;
            for (int i = 0; i < Factures.size(); i++) {
                //chargement dynamique d'une interface
                FXMLLoader loader = new FXMLLoader(getClass().getResource("FactureAffichage.fxml"));
                AnchorPane pane = loader.load();
               
                //passage de parametres
                FactureAffichageController controller = loader.getController();
                controller.setFacture(Factures.get(i));
                System.out.println(Factures.get(i).getCommande_id());
                controller.setIdFacture(Factures.get(i).getId());
                gridfacture.add(pane, column, row);
                column++;
                if (column > 1) {
                    column = 0;
                    row++;
                }
               
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            Logger.getLogger(CommandeController.class.getName()).log(Level.SEVERE, null, ex);
        }   
    }

    private void Back(ActionEvent event) throws IOException {
        Stage currentStage = (Stage) backCom.getScene().getWindow();
                            currentStage.close();
                
                            Stage stage = new Stage();
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("CommandeBack.fxml"));

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
    
    

