/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ui;


import animatefx.animation.BounceIn;
import entite.Reclamation;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import service.ReclamationService;
import service.UserService;

/**
 * FXML Controller class
 *
 * @author malek
 */
public class AddReclamationController implements Initializable {

    @FXML
    private TextField txtNom;
    @FXML
    private TextField txtEmail;
    
    @FXML
    private TextField txtDes;
    @FXML
    private Button txtEnvoyer;
    @FXML
    private ComboBox<String> comboBox;
    
    private UserService ps = new UserService();
    private String email=ps.getCurrentUser().getEmail();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
      ObservableList<String> listTrier = FXCollections.observableArrayList("Problèmes achat en ligne","Problèmes de confidentialité et de sécurité","Problèmes de qualité de produits ou de services");
            comboBox.setItems(listTrier);
    }    

    @FXML
    private void envoyerReclamation(ActionEvent event) throws IOException {
         if (txtNom.getText().isEmpty() || txtDes.getText().isEmpty() || txtEmail.getText().isEmpty() || comboBox.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("Echec");
    alert.setHeaderText(null);
    String message = "";
    if (txtNom.getText().isEmpty()) {
        message += "Le champ Nom est vide\n";
    }
    if (comboBox.getSelectionModel().isEmpty()) {
        message += "Le champ Type est vide\n";
    }
    if (txtDes.getText().isEmpty()) {
        message += "Le champ Description est vide\n";
    }
    if (txtEmail.getText().isEmpty() ) {
        message += "Le champ Email est vide\n";
    } else if (!txtEmail.getText().matches("^[A-Za-z0-9+_-]+@[A-Za-z0-9]+\\.[A-Za-z]+$")) {
    message += "Le format de l'adresse email est invalide\n";
}

    alert.setContentText(message);
    alert.show();
} else {
         
            
                LocalDateTime datereclamation = LocalDateTime.now();
                ReclamationService ps = new ReclamationService();
                ps.insert(new Reclamation(comboBox.getSelectionModel().getSelectedItem().toString(), txtNom.getText(), txtDes.getText(), 0, datereclamation,email , txtEmail.getText()));
                
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Reclamation added successfully!");
                alert.setHeaderText(null);
                alert.setTitle("Succes");
                alert.show();
             
            FXMLLoader loader = new FXMLLoader(getClass().getResource("DashboardClient.fxml"));
        
                Parent root = loader.load();
                txtEmail.getScene().setRoot(root);
                
              //new BounceIn(root).play();
             
        
     }
    }
    
    
}
