/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import entite.Facture;
import entite.Order;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import service.FactureService;
import service.OrderService;
import service.ProduitService;

/**
 * FXML Controller class
 *
 * @author Samar
 */
public class FactureAffichageController implements Initializable {

    @FXML
    private Label idcommande;
    @FXML
    private Label estsup;
    @FXML
    private Label datefacture;
    @FXML
    private Button imprimerbtn;
    @FXML
    private Button annulerbtn;
    @FXML
    private Label quantity;
    @FXML
    private Label Totale;
    OrderService os=new OrderService();
    int idFacture;
    @FXML
    private Label Prix;
    
    FactureService cr =new FactureService();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }  
    
    private Facture fact=new Facture();
    public void setFacture(Facture p) {
        try {
            this.fact=p;
            Order Ord=os.readById(p.getCommande_id());
            System.out.println(Ord);
            idcommande.setText(String.valueOf(p.getId()));
            estsup.setText(String.valueOf(p.isEst_sup()));
            datefacture.setText(String.valueOf(p.getDate_facturation()));
            quantity.setText(String.valueOf(Ord.getQuantity()));
            Totale.setText(String.valueOf(Ord.getTotal()));
            Prix.setText(String.valueOf(Ord.getPrix()));
        } catch (SQLException ex) {
            Logger.getLogger(FactureAffichageController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    public void setIdFacture(int id){
        this.idFacture=id;
    }

    @FXML
    private void imprimer(ActionEvent event) {
         try {
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream("C:\\Users\\MSI\\OneDrive\\Bureau\\excel.csv"), "UTF-8"));
         

            List<Facture> factures = cr.readAll();
            writer.write("ID ,Date_facturation  , Commande_id , payment \n");
            for (Facture obj : factures) {

                int id = obj.getId();
writer.write(Integer.toString(id));

                writer.write("   :");
                writer.write(obj.getDate_facturation());
                writer.write("    :");
                writer.write(obj.getCommande_id());
                writer.write("  :");
               
               writer.write(Boolean.toString(obj.isEst_sup()));
               writer.write("   :");
               
               
            
                writer.write("\n");

            }
            writer.flush();
            writer.close();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("EXCEL ");

            alert.setHeaderText("EXCEL");
            alert.setContentText("Enregistrement effectué avec succès!");

            alert.showAndWait();
        } catch (Exception e) {
            System.out.println("Failed to send message: " + e.getMessage());
        }
         
        
    }

    @FXML
    private void supprimerfacture(ActionEvent event) {
    }
    
}
