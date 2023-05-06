/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ui;

import com.jfoenix.controls.JFXComboBox;
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
import javafx.stage.Stage;
import service.ReclamationService;
import service.UserService;

/**
 * FXML Controller class
 *
 * @author malek
 */
public class UpdateReclamationController implements Initializable {

    @FXML
    private TextField txtNom;
    @FXML
    private TextField txtEmail;
    @FXML
    private ComboBox<String> txtType;
    @FXML
    private Button txtEnvoyer;
    @FXML
    private TextField txtDes;
     private int id_reclamation,status;
     
       private UserService ps = new UserService();
    private String email=ps.getCurrentUser().getEmail();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
         ObservableList<String> listTrier = FXCollections.observableArrayList("Problèmes achat en ligne","Problèmes de confidentialité et de sécurité","Problèmes de qualité de produits ou de services");
            txtType.setItems(listTrier);
    }    
    public void setData(int id_reclamation,int status) {
       this.id_reclamation=id_reclamation;
       this.status=status;
    }

    @FXML
    private void envoyerReclamation(ActionEvent event) {
        if (txtNom.getText().isEmpty() || txtDes.getText().isEmpty() || txtEmail.getText().isEmpty() || txtType.getSelectionModel().isEmpty()) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("Echec");
    alert.setHeaderText(null);
    String message = "";
    if (txtNom.getText().isEmpty()) {
        message += "Le champ Nom est vide\n";
    }
    if (txtType.getSelectionModel().isEmpty()) {
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
         try {
             LocalDateTime datereclamation = LocalDateTime.now();
             ReclamationService ps = new ReclamationService();
             ps.update(new Reclamation(txtType.getSelectionModel().getSelectedItem().toString(), txtNom.getText(), txtDes.getText(), status, datereclamation, email, txtEmail.getText()),id_reclamation);
             Stage currentStage = (Stage) txtEmail.getScene().getWindow();
                            currentStage.close();
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ListFrontReclamation.fxml"));
        
                Parent root = loader.load();
                txtEmail.getScene().setRoot(root);
             ListReclamationController dc = loader.getController();
             
         } catch (IOException ex) {
             Logger.getLogger(AddReclamationController.class.getName()).log(Level.SEVERE, null, ex);
         }
     }
    }
    
     public void setReclamation(Reclamation r){
        this.txtNom.setText(r.getNom());
        this.txtEmail.setText(r.getEmail_reclamation());
        this.txtDes.setText(r.getDescription());
        this.txtType.setValue(r.getType());
    }
    
}
