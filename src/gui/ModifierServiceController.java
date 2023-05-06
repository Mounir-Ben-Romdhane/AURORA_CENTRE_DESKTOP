/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package gui;

import entite.Service;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import service.ServiceService;

/**
 * FXML Controller class
 *
 * @author mfmma
 */
public class ModifierServiceController implements Initializable {

    @FXML
    private TextField titreS;
    @FXML
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
    
       
        @FXML
    private Button btnRetour;

    private Service selectedService ;
    ServiceService service = new ServiceService();
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
                ImageS.setText(fileName);
                Image image = new Image(selectedFile.toURI().toString());
               // imageView.setImage(image);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
  @FXML
public void modifier(ActionEvent event) {
    if (titreS.getText().isEmpty() || ImageS.getText().isEmpty() || typeS.getValue() == null || dateS.getValue() == null || dascriptionS.getText().isEmpty()) {
        System.out.println("Veillez remplir tous les champs.");
        return;
    }

    Service updatedService = new Service();
    updatedService.setId(selectedService.getId());
    updatedService.setTitreS(titreS.getText());
    updatedService.setImage(ImageS.getText());
    updatedService.setTypeS(typeS.getValue());
    updatedService.setDateS(dateS.getValue().atStartOfDay()); // convert LocalDate to LocalDateTime
    updatedService.setDescrptionS(dascriptionS.getText());

        try {
            service.update(updatedService);
                        JOptionPane.showMessageDialog(null, "Le service a été modifié avec succès !");

        } catch (SQLException ex) {
            Logger.getLogger(ModifierServiceController.class.getName()).log(Level.SEVERE, null, ex);
        }
    System.out.println("Service updated successfully!");

  
}

    public void setSelectedCtegorie(Service s) {
        selectedService = s;
        titreS.setText(s.getTitreS());
        ImageS.setText(s.getImage());
        typeS.setValue(s.getTypeS());
        LocalDateTime dateTime = s.getDateS(); 
        LocalDate date = dateTime.toLocalDate(); 
        dateS.setValue(date);      
        dascriptionS.setText(s.getDescrptionS());
    }
    
    
    @FXML
    public void handleBtnRetour(MouseEvent event) throws IOException {
        AnchorPane formProduit = new AnchorPane();
        formProduit.getChildren().add(FXMLLoader.load(getClass().getResource("/gui/ServiceBack.fxml")));
        Scene currentScene = btnRetour.getScene();
        Stage currentStage = (Stage) currentScene.getWindow();
        Scene newScene = new Scene(formProduit, currentScene.getWidth(), currentScene.getHeight());
        currentStage.setScene(newScene);
    }
    
    
}
