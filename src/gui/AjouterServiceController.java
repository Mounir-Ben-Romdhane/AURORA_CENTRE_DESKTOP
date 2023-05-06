/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package gui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import service.ServiceService;
import entite.Service;
import java.nio.file.Path;
import java.sql.Timestamp;
import java.time.LocalDate;
import javafx.fxml.FXMLLoader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.nio.file.StandardCopyOption;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.swing.JOptionPane;


public class AjouterServiceController implements Initializable {

    @FXML
    private TextField titreS;
    @FXML
    private ChoiceBox<String> typeS;
    @FXML
    private Button btnAjoutS;
    @FXML
    private DatePicker dateS;
    @FXML
    private TextField dascriptionS;
    @FXML
    private TextField imageS;
        @FXML
    private Button btnImage;
        
    @FXML
    private ImageView imageView;
    
        @FXML
    private Button btnRetour;

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
    @FXML
    public void ajouter() {
    String titre = titreS.getText();
    String type = typeS.getValue();
    String description = dascriptionS.getText();
    String image = imageS.getText();

    if (titre.isEmpty() || type.isEmpty()|| description.isEmpty() || image.isEmpty()) {
        JOptionPane.showMessageDialog(null, "Please fill all the fields!");
        return;
    }

    LocalDateTime dateS = LocalDateTime.now();
    
    if (dateS == null || dateS.isBefore(dateS)) {
        JOptionPane.showMessageDialog(null, "La date doit être valide.");
        return;
    }
    
    

    Service nouveauService = new Service(0, titre, description, type, image, dateS);
    ServiceService ser = new ServiceService();
    ser.insert(nouveauService);

    JOptionPane.showMessageDialog(null, "Service added successfully!");
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
    


