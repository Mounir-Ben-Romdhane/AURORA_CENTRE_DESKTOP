/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import Entite.Product;
import entite.Order;
import entite.Produit;
import entite.SMSSender;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import service.OrderService;

/**
 * FXML Controller class
 *
 * @author Samar
 */
public class AffichageOController implements Initializable {

    @FXML
    private Label descriptionProduit;
    @FXML
    private Label nomProduit;
    @FXML
    private Label prixProduit;
    @FXML
    private ImageView imageProduit;
    @FXML
    private Button annulerButton;
    @FXML
    private TextField idProduit;
    @FXML
    private Button commanderButton;
    
    
    int idProduitt;
    @FXML
    private TextField quantityTf;
    
    OrderService os= new OrderService();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        idProduit.setVisible(false);               
        annulerButton.setVisible(false);
        System.out.println("aaaaaaaaa");
    }    

    private Produit prod=new Produit();
    public void setProduit(Produit p) {
        this.prod=p;
        descriptionProduit.setText(p.getDescription());
        nomProduit.setText(p.getNom());
//        prixProduit.setText(p.getPrix().toString());
        
        prixProduit.setText(String.valueOf(p.getPrix()));
        
        idProduit.setText(String.valueOf(p.getId()));
        System.out.println(p.getId());
         String path = p.getImage();
         File file=new File(path);
         Image img = new Image(file.toURI().toString());
         imageProduit.setImage(img);

    }
    
    public void setIdProduit(int id){
        this.idProduitt=id;
    }
    
    @FXML
    private void commanderProduit(ActionEvent event) {
        try {
            Order o = new Order();
            o.setReference("25042023-644834b9eeb09");
            o.setPrix(Double.valueOf(prixProduit.getText()));
            o.setIs_paid(true);
            o.setQuantity(Integer.parseInt(quantityTf.getText()));
            o.setTotal(o.getPrix()*o.getQuantity());
            o.setUser_id(2);
            os.insert(o);
           Parent root = FXMLLoader.load(getClass().getResource("Details.fxml"));
                // Create the new stage
                Stage newStage = new Stage();
                // Set the title of the new stage
                newStage.setTitle("Commande");
                // Create the scene for the new stage
                Scene scene = new Scene(root);
                newStage.setScene(scene);
                // Show the new stage
                newStage.show();
                 SMSSender ss = new SMSSender();
                 ss.SMSSENDER(112);
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("Details.fxml"));
//            Parent root = loader.load();
//            Scene sc = new Scene(root);
//            Stage primaryStage = (Stage) prixProduit.getScene().getWindow();
//            primaryStage.setScene(sc);
//            primaryStage.setTitle("commande");
//            primaryStage.show();
//            SMSSender ss = new SMSSender();
//            ss.SMSSENDER(2);
////            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information ");
        alert.setHeaderText("Votr Commande a éte effectuéé");
        alert.setContentText("Commande ajoutee avec succes!");
        alert.showAndWait();        
        //setProduit();  
        } catch (SQLException ex) {
            Logger.getLogger(AffichageController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(AffichageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    private void uploadImage(ActionEvent event) throws FileNotFoundException, IOException {
        Random rand = new Random();
        int x = rand.nextInt(1000);
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Upload File Path");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        File file = fileChooser.showOpenDialog(null);
        String DBPath = "C:\\Users\\MSI\\OneDrive\\Documents\\GitHub\\PIDEV_DESKTOP\\src\\image\\"  + x + ".jpg";
        if (file != null) {
            FileInputStream Fsource = new FileInputStream(file.getAbsolutePath());
            FileOutputStream Fdestination = new FileOutputStream(DBPath);
            BufferedInputStream bin = new BufferedInputStream(Fsource);
            BufferedOutputStream bou = new BufferedOutputStream(Fdestination);
            System.out.println(file.getAbsoluteFile());
            String path=file.getAbsolutePath();
            Image img = new Image(file.toURI().toString());
            imageProduit.setImage(img);    
//            image.setText(DBPath);
            int b = 0;
            while (b != -1) {
                b = bin.read();
                bou.write(b);
            }
            bin.close();
            bou.close();          
        } else {
            System.out.println("error");
        }
    }
}
