/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import animatefx.animation.Pulse;
import entite.Order;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableRow;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import service.OrderService;
import service.ProduitService;

/**
 * FXML Controller class
 *
 * @author Samar
 */
public class DetailsController implements Initializable {

    @FXML
    private VBox vBoxDashboardClient;
    @FXML
    private GridPane gridOrder;

    
    OrderService Ev=new OrderService();
    private Button front;
    private Button affichefac;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        afficherOrder();
    }   
    
    public void afficherOrder() {
         try {
            List<Order> Orders = Ev.readByIdUser(2);
            gridOrder.getChildren().clear();
            int row = 0;
            int column = 0;
            for (int i = 0; i < Orders.size(); i++) {
                //chargement dynamique d'une interface
                FXMLLoader loader = new FXMLLoader(getClass().getResource("AffichageDetails.fxml"));
                AnchorPane pane = loader.load();
               
                //passage de parametres
                AffichageDetailsController controller = loader.getController();
                controller.setProduit(Orders.get(i));
                controller.setIdOrder(Orders.get(i).getId());
                gridOrder.add(pane, column, row);
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


    private void front(ActionEvent event) throws IOException {
         Stage currentStage = (Stage) front.getScene().getWindow();
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

    private void factlisttt(ActionEvent event) throws IOException {
         Stage currentStage = (Stage) affichefac.getScene().getWindow();
                            currentStage.close();
                
                            Stage stage = new Stage();
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("facture.fxml"));

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
