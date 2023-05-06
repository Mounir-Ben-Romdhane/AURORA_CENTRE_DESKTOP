/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import entite.Produit;
import entite.Order;

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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import service.OrderService;
import service.ProduitService;

/**
 * FXML Controller class
 *
 * @author Samar
 */
public class CommandeController implements Initializable {

    @FXML
    private VBox vBoxDashboardClient;
    @FXML
    private GridPane gridEvent;
    
    ProduitService Ev=new ProduitService();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            afficherProduit();
        } catch (SQLException ex) {
            Logger.getLogger(CommandeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
    public void afficherProduit() throws SQLException {
         try {
            List<Produit> Produits = Ev.readAll();
            gridEvent.getChildren().clear();
            int row = 0;
            int column = 0;
            for (int i = 0; i < Produits.size(); i++) {
//                chargement dynamique d'une interface
                FXMLLoader loader = new FXMLLoader(getClass().getResource("AffichageO.fxml"));
                AnchorPane pane = loader.load();
               
//                passage de parametres
                AffichageOController controller = loader.getController();
                controller.setProduit(Produits.get(i));
                controller.setIdProduit(Produits.get(i).getId());
                gridEvent.add(pane, column, row);
                column++;
                if (column > 1) {
                    column = 0;
                    row++;
                }
               
            }
        } catch (IOException ex) {
            Logger.getLogger(CommandeController.class.getName()).log(Level.SEVERE, null, ex);
        }   
    }


    
}
