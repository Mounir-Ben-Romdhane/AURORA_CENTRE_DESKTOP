/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import entite.User;
import entite.Participation;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import service.EvenementService;
import service.ParticipationService;
import service.UserService;

/**
 * FXML Controller class
 *
 * @author asus
 */
public class ParticipationController implements Initializable {

    @FXML
    private Label descriptionF;
    @FXML
    private TextField descriptionT;
    //private TextField emailT;
    //private TextField numtelevT;
    @FXML
    private Button ajouterParticipation;
    @FXML
    private TextField idEvField;
    
    ParticipationService ps = new ParticipationService();
    
    UserService us = new UserService();
    
    EvenementService es=new EvenementService();
    
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        /*numtelevT.addEventFilter(KeyEvent.KEY_TYPED, event -> {
            // Filtrer pour n'accepter que les chiffres
            if (!event.getCharacter().matches("\\d")) {
                event.consume();
            }
        });*/
    }    

    @FXML
    private void ajoutetP(ActionEvent event) {
        //  Participation p=new Participation(Integer.parseInt(idEvField.getText()),descriptionT.getText(),usernameT.getText(),emailT.getText(),Integer.parseInt(numtelevF.getText()));
        /*if (!validateEmail(emailT.getText())) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information ");
            alert.setHeaderText("email non valide");
            alert.setContentText("Participation non ajoute!");
            alert.showAndWait();
        } else if (numtelevT.getText().length() != 😎 {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information ");
            alert.setHeaderText("telephone non valide");
            alert.setContentText("Participation non ajoute!");
            alert.showAndWait();
        } else {*/
            
            Participation p = new Participation();
            
            
            UserService us = new UserService();
            User currentUser = us.getCurrentUser();
            
            
            //tfEmailProfil.setText(currentUser.getEmail());
            
            //emailT.setText(currentUser.getEmail());
            
            
            
            
            //p.setEmailev(emailT.getText());
            
            p.setEmailev(currentUser.getEmail());
            p.setUsernameev(currentUser.getUserName());
            p.setNumtelev(Integer.valueOf(currentUser.getNumTel()));
            
            
            //p.setEmailev(emailT.getText());
            //p.setNumtelev(Integer.valueOf(numtelevT.getText()));
            p.setEvenement_id(es.FetchOneEvent(Integer.valueOf(idEvField.getText())));
            p.setDescription_pn(descriptionT.getText());
            //p.setUsernameev(usernameT.getText());
            ps.ajouterParticipation(p);

            Stage stage = (Stage) descriptionT.getScene().getWindow();
            stage.close();
            
        //}
    }
    
    
    public static boolean validateEmail(String email) {
        // Expression régulière pour valider un email
        String emailPattern = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

        // Créer un objet Pattern à partir de l'expression régulière
        Pattern pattern = Pattern.compile(emailPattern);

        // Créer un objet Matcher pour matcher l'email avec le pattern
        Matcher matcher = pattern.matcher(email);

        // Retourner true si l'email est valide, sinon false
        return matcher.matches();
    }

    public void setIdevent(int idevent) {
        idEvField.setText(String.valueOf(idevent));
    }

    
}