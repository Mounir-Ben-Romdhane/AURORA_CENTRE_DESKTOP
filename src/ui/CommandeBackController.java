/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;


import animatefx.animation.Pulse;
import entite.Facture;
import entite.Order;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import service.OrderService;

/**
 * FXML Controller class
 *
 * @author Samar
 */
public class CommandeBackController implements Initializable {

    @FXML
    private TableView<Order> tabcom;
    private TableColumn<Order, Integer> idc;
    @FXML
    private TableColumn<Order, Integer> userc;
    @FXML
    private TableColumn<Order, String> datecc;
    @FXML
    private TableColumn<Order, Boolean> ispaidc;
    @FXML
    private TableColumn<Order, String> referencecom;
    @FXML
    private TableColumn<Order, Double> qtecom;
    @FXML
    private TableColumn<Order, Double> prixc;
    @FXML
    private Button deletebtn;
    @FXML
    private Button modifbtn;
    
    OrderService Es= new OrderService();
    @FXML
    private TableColumn<Order, Double> totalc;
    private Button factureback;
    private Button listfac;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        getCommande();
    }    

    public void getCommande() {  
    
         try {
            
            // TODO
            List<Order> orders = Es.readAll();
            ObservableList<Order> olp = FXCollections.observableArrayList(orders);
            tabcom.setItems(olp);
            //idc.setCellValueFactory(new PropertyValueFactory("id"));
            userc.setCellValueFactory(new PropertyValueFactory("user_id"));
            datecc.setCellValueFactory(new PropertyValueFactory("created_at"));
            ispaidc.setCellValueFactory(new PropertyValueFactory("is_paid"));
            referencecom.setCellValueFactory(new PropertyValueFactory("reference"));
            qtecom.setCellValueFactory(new PropertyValueFactory("quantity"));
            prixc.setCellValueFactory(new PropertyValueFactory("prix"));
             totalc.setCellValueFactory(new PropertyValueFactory("total"));
            
            
         
        } catch (SQLException ex) {
            System.out.println("error" + ex.getMessage());
        }
    }//get events

    @FXML
    private void deleteButton(ActionEvent event) {
        try {
            int myIndex = tabcom.getSelectionModel().getSelectedIndex();
            
            int id = Integer.parseInt(String.valueOf(tabcom.getItems().get(myIndex).getId()));
            System.out.println(id);
            System.out.println(myIndex);
            
            try {
                Es.delete(id);
            } catch (SQLException ex) {
                Logger.getLogger(FactureBackController.class.getName()).log(Level.SEVERE, null, ex);
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Annulation de la commande");
            alert.setHeaderText("Annulation de la Commande");
            alert.setContentText("Commande annulée!");
            alert.showAndWait();
            List<Order> orders = Es.readAll();
            ObservableList<Order> olp = FXCollections.observableArrayList(orders);
            tabcom.setItems(olp);
            tabcom.refresh();
            //tabfac();
            
        } catch (SQLException ex) {
            Logger.getLogger(FactureBackController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void modifButton(ActionEvent event) {
    }

   

    private void facliist(ActionEvent event) throws IOException {
        Stage currentStage = (Stage) listfac.getScene().getWindow();
                            currentStage.close();
                
                            Stage stage = new Stage();
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("FactureBack.fxml"));

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
