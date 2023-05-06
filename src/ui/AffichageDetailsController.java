/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import entite.Facture;
import entite.Order;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import tray.notification.TrayNotification;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.Duration;
import service.FactureService;
import service.OrderService;
import tray.animations.AnimationType;
import tray.notification.NotificationType;

/**
 * FXML Controller class
 *
 * @author Samar
 */
public class AffichageDetailsController implements Initializable {

    @FXML
    private Label nomProduit;
    @FXML
    private Label prixProduit;
    @FXML
    private TextField idOrder;
    @FXML
    private Label ProduitQuantity;
    @FXML
    private Label ProduitTotale;
    @FXML
    private Button modifierButton;
    @FXML
    private Button supprimerButton;
    @FXML
    private Label dateOrder;
    
    OrderService os=new OrderService();
    FactureService fs=new FactureService();
    @FXML
    private Button genererFacturebtn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        idOrder.setVisible(false);
    } 
    
    private Order ord=new Order();
    public void setProduit(Order p) {
        this.ord=p;
        ProduitTotale.setText(String.valueOf(p.getTotal()));
        nomProduit.setText(p.getReference());
        prixProduit.setText(String.valueOf(p.getPrix()));
        ProduitQuantity.setText(String.valueOf(p.getQuantity()));
        idOrder.setText(String.valueOf(p.getId()));
        dateOrder.setText(p.getCreatedAt());
    }
    private int idOrderr;
    public void setIdOrder(int id){
        this.idOrderr=id;
    }

    ModifierOrderController moc=new ModifierOrderController();
    @FXML
   private void modifierOrder(ActionEvent event) {
        this.ord.setQuantity(Integer.parseInt(ProduitQuantity.getText()));
        this.ord.setId(Integer.parseInt(idOrder.getText()));
        this.ord.setPrix(Double.parseDouble(prixProduit.getText()));
        System.out.println(this.ord.getPrix());
        System.out.println(this.ord.getId());
        System.out.println(this.ord.getQuantity());
        moc.recupOrder(ord);
        try {
            Parent root = FXMLLoader.load(getClass().getResource("modifierOrder.fxml"));
                // Create the new stage
                Stage newStage = new Stage();
                // Set the title of the new stage
                newStage.setTitle("modifierOrder");
                // Create the scene for the new stage
                Scene scene = new Scene(root);
                newStage.setScene(scene);
                // Show the new stage
                newStage.show();
            
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void supprimerOrder(ActionEvent event) {
        try {
            os.delete(idOrderr);
        } catch (SQLException ex) {
            Logger.getLogger(AffichageDetailsController.class.getName()).log(Level.SEVERE, null, ex);
        }
         Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information ");
        alert.setHeaderText("Order delete");
        alert.setContentText("Order deleted successfully!");
        alert.showAndWait();        
        //setProduit();  
    }

    @FXML
    private void genererFacture(ActionEvent event) {
        try {
            Facture o = new Facture();
            o.setCommande_id(idOrderr);
            o.setEst_sup(true);
            fs.insert(o);
           
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("Details.fxml"));
//            Parent root = loader.load();
//            Scene sc = new Scene(root);
//            Stage primaryStage = (Stage) prixProduit.getScene().getWindow();
//            primaryStage.setScene(sc);
//            primaryStage.setTitle("add");
//            primaryStage.show();
            //SMSSender ss = new SMSSender();
            //ss.SMSSENDER(2);
            notiff();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information ");
        alert.setHeaderText("Facture ajaoutéé");
        alert.setContentText("Facture ajoutee successfully!");
        alert.showAndWait();        
        //setProduit();  
        } catch (SQLException ex) {
            Logger.getLogger(AffichageController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
//        } catch (IOException ex) {
//            Logger.getLogger(AffichageController.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }
    
    private void notiff() {
        
        Facture RV = new Facture();
        String date_facturation = dateOrder.getText();
        RV.setDate_facturation(date_facturation);
        tray.notification.TrayNotification notification = new tray.notification.TrayNotification();
        AnimationType type = AnimationType.POPUP;
        notification.setAnimationType(type);
        notification.setTitle("Bienvenue à ");
        notification.setMessage("Date_facturation " + RV.getDate_facturation() + " a été effectué avec succès.");
        notification.setNotificationType(NotificationType.INFORMATION);
        notification.showAndDismiss(Duration.millis(2000));

    }
    
}
