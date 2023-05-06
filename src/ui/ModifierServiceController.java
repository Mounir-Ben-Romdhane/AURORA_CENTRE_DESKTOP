/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ui;

import entite.Reclamation;
import entite.Service;
import java.io.File;
import java.io.IOException;
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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import service.ReclamationService;
import service.ServiceService;
import service.UserService;
import util.DataSource;

/**
 * FXML Controller class
 *
 * @author mfmma
 */
public class ModifierServiceController implements Initializable {

    @FXML
    private TextField titreS;
    private TextField ImageS;
    @FXML
    private ChoiceBox<String> typeS;
    @FXML
    private DatePicker dateS;
    @FXML
    private TextField dascriptionS;
    @FXML
    private Button btnModifierS;
       @FXML
    private Button btnImage;
       
    private int idservice;

    private Connection conn=DataSource.getInstance().getCnx();
    
    
       
//        @FXML
  //  private Button btnRetour;

    private Service selectedService ;
    ServiceService service = new ServiceService();
    @FXML
    private TextField imageS;
    @FXML
    private ImageView imageView;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
             typeS.getItems().add("Médecine douce");
        typeS.getItems().add("Sport");
        typeS.getItems().add("Coaching");
        typeS.getItems().add("Consultation psychologique");
    }    

    
     @FXML
    public void chooseFile() {
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(btnImage.getScene().getWindow());
        if (selectedFile != null) {
            String fileName = selectedFile.getName();
            Path sourcePath = selectedFile.toPath();
            Path destinationPath = Paths.get("src", "image", fileName);
            try {
                Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("File copied to: " + destinationPath.toAbsolutePath());
                imageS.setText(fileName);
                Image image = new Image(selectedFile.toURI().toString());
                imageView.setImage(image);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    public void setSelectedCtegorie(Service s) {
        titreS.setText(s.getTitreS());
        dascriptionS.setText(s.getDescrptionS());
        typeS.setValue(s.getTypeS());
        LocalDateTime dateTime = s.getDateS(); 
        LocalDate date = dateTime.toLocalDate(); 
        dateS.setValue(date);      
       // ImageS.setText(s.getImage());

    }

   
   public void setData(int idservice) {
       this.idservice=idservice;
    }

 

    @FXML
    private void btnMod(ActionEvent event) throws SQLException {
    
       
            
           String requete = "UPDATE service SET titre_s = ?, description_s = ?, type_s = ?, date_s = ?, image = ? WHERE id = ?";
        
        try {   
                    PreparedStatement pst=conn.prepareStatement(requete);
                    pst.setString(1, titreS.getText());
                    pst.setString(2, dascriptionS.getText());
                    pst.setString(3, typeS.getValue());
                    pst.setString(4, dateS.getValue().toString());
              
                    pst.setString(5, imageS.getText());
                      pst.setInt(6, idservice);
                      
                    pst.executeUpdate();
                    //System.out.println(titreS.getText());
                    //System.out.println(dascriptionS.getText());
                    //System.out.println(typeS.getValue());
                    //System.out.println(dateS.getValue().toString());
                    //System.out.println(imageS.getText());
                    //System.out.println(idservice);
                    //Close current stage
                            Stage currentStage = (Stage) dascriptionS.getScene().getWindow();
                            currentStage.close();
 
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
       
         
     
          
     
    }
    
}
