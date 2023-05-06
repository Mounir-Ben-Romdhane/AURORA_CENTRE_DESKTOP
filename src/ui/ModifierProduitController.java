/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ui;

import entite.Produit;
import java.io.File;
import java.io.IOException;
import static java.lang.Integer.parseInt;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import service.UserService;
import util.DataSource;

/**
 * FXML Controller class
 *
 * @author MSI
 */
public class ModifierProduitController implements Initializable {

    /**
     * Initializes the controller class.
     */
    int id;
    private TextField titreS;
    @FXML
    private Button btnImage;
    @FXML
    private ImageView imageView;
    @FXML
    private Button btnModifierS;
    @FXML
    private TextField nomP;
    @FXML
    private TextField descriptionP;
    @FXML
    private TextField prixP;
    @FXML
    private TextField imageP;
    
     private Connection conn=DataSource.getInstance().getCnx();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    
     public void setData(int id) {
       this.id=id;
    }
     
     public void setSelectedCtegorie(Produit s) {
        nomP.setText(s.getNom());
        descriptionP.setText(s.getDescription());
        
        prixP.setText(""+s.getPrix());
        
        imageP.setText(s.getImage());

    }

   

    @FXML
    private void modifier(MouseEvent event) {
        
            
           String requete = "UPDATE produit SET nom = ?, description = ?, prix = ? , image = ? WHERE id = ?";
        
        try {   
                    PreparedStatement pst=conn.prepareStatement(requete);
                    pst.setString(1, nomP.getText());
                    pst.setString(2, descriptionP.getText());
                    pst.setInt(3, parseInt(prixP.getText()));
                    pst.setString(4, imageP.getText());
                      pst.setInt(5, id);
                      
                    pst.executeUpdate();
                    //System.out.println(titreS.getText());
                    //System.out.println(dascriptionS.getText());
                    //System.out.println(typeS.getValue());
                    //System.out.println(dateS.getValue().toString());
                    //System.out.println(imageS.getText());
                    //System.out.println(idservice);
                    //Close current stage
                            Stage currentStage = (Stage) descriptionP.getScene().getWindow();
                            currentStage.close();
 
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        
    }

    @FXML
    private void chooseFile(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(btnImage.getScene().getWindow());
        if (selectedFile != null) {
            String fileName = selectedFile.getName();
            Path sourcePath = selectedFile.toPath();
            Path destinationPath = Paths.get("src", "image", fileName);
            try {
                Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("File copied to: " + destinationPath.toAbsolutePath());
                imageP.setText(fileName);
                Image image = new Image(selectedFile.toURI().toString());
                imageView.setImage(image);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

   
   
    
}
