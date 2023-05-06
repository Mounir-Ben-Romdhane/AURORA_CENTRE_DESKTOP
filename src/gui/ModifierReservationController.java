/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package gui;

import entite.Reservation;
import entite.Service;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javax.swing.JOptionPane;
import service.ServiceReservation;
import service.ServiceService;

/**
 * FXML Controller class
 *
 * @author mfmma
 */
public class ModifierReservationController implements Initializable {

    @FXML
    private Button btnModifierR;
    @FXML
    private TextField numero;
    @FXML
    private TextField userName;
    @FXML
    private TextField email;
    @FXML
    private TextField numeroTel;
    @FXML
    private DatePicker dateR;
    
      @FXML
    private ComboBox<String> cbService;
      
       @FXML
    private Button btnRetour;
    
    
    private Reservation selectedRes ;
    
    ServiceReservation serviceRes = new ServiceReservation();
    ServiceService serviceSer = new ServiceService();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
            /*
        List<Service> service = serviceSer.readAll();
        ObservableList<String> serviceLabels = FXCollections.observableArrayList(
                service.stream().map(Service::getTitreS).collect(Collectors.toList()));
        cbService.setItems(serviceLabels);
        cbService.setConverter(new StringConverter<String>() {
            
            @Override
            public String toString(String label) {
                return label;
            }

            @Override
            public String fromString(String label) {
                return label;
            }
        });*/
    }    

      @FXML
public void modifier(ActionEvent event) {
    if (selectedRes != null) {
        // Validate the input fields
        if (numero.getText().isEmpty() || userName.getText().isEmpty() || email.getText().isEmpty() || numeroTel.getText().isEmpty() || dateR.getValue() == null ) {
            // Show an error message if any field is empty
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Veillez remplir tous les champs");
            alert.showAndWait();
            return;
        }

        //Controle de saisie pour numero
        if (numero.getText().length() > 3) {
            JOptionPane.showMessageDialog(null, "Ce champs doit contenir au moins 3 caractères");
            return; // Return to stop further execution of the method
        } 

        //Controle de saisie pour numeroTel
        String regex = "^[0-9]{8}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(numeroTel.getText());
        if (!matcher.matches()) {
            JOptionPane.showMessageDialog(null, "NumeroTel doit contenir 8 chiffres et seulement des chiffres");
            return;
        } 

        //Controle de saisie pour l'email
        String regex1 = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z]{2,}$";
        Pattern pattern1 = Pattern.compile(regex1, Pattern.CASE_INSENSITIVE);
        Matcher matcher1 = pattern1.matcher(email.getText());
        if (!matcher1.matches()) {
            JOptionPane.showMessageDialog(null, "Email doit respecter le format d'un email");
            return;
        } 

        //Controle de saisie pour username
        String regex2 = "^[a-zA-Z]+$";
        Pattern pattern2 = Pattern.compile(regex2);
        Matcher matcher2 = pattern2.matcher(userName.getText());
        if (!matcher2.matches()) {
            JOptionPane.showMessageDialog(null, "UserName ne doit contenir que des lettres.");
            return;
        }

        LocalDate date = dateR.getValue();
        if (date == null || date.isBefore(LocalDate.now())) {
            JOptionPane.showMessageDialog(null, "La date doit être valide.");
            return;
        }

        try {
            selectedRes.setUser_name(userName.getText());
            selectedRes.setId_service(serviceSer.getServiceByName(cbService.getValue()));
            LocalTime time = LocalTime.now(); // you may want to use a TimePicker control here
            LocalDateTime dateTime = LocalDateTime.of(date, time);
            int numeroTels = Integer.parseUnsignedInt(numeroTel.getText());
            int numeros = Integer.parseInt(numero.getText());
            selectedRes.setNumero(numeros);
            selectedRes.setNumeroTel(numeroTels);
            selectedRes.setDateR(dateTime);
            selectedRes.setEmail(email.getText());

            System.out.println(selectedRes);

            serviceRes.update(selectedRes);

            JOptionPane.showMessageDialog(null, "La réservation a été modifiée avec succès !");
        } catch (SQLException ex) {
            Logger.getLogger(ReservationBackController.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Impossible de modifier la réservation. Veuillez réessayer plus tard.");
        }
    } else {
        JOptionPane.showMessageDialog(null, "Sélectionnez une réservation à modifier.");
    }
}


        public void setSelectedRes(Reservation r) {
        selectedRes = r;
        numero.setText(String.valueOf(r.getNumero()));
        numeroTel.setText(String.valueOf(r.getNumeroTel()));
        userName.setText(r.getUser_name());
        cbService.setValue(r.getId_service().getTitreS());
        LocalDateTime dateTime = r.getDateR(); 
        LocalDate date = dateTime.toLocalDate(); 
        dateR.setValue(date);      
        email.setText(r.getEmail());
    }
    @FXML
    public void handleBtnRetour(MouseEvent event) throws IOException {
        AnchorPane formProduit = new AnchorPane();
        formProduit.getChildren().add(FXMLLoader.load(getClass().getResource("/gui/ReservationBack.fxml")));
        Scene currentScene = btnRetour.getScene();
        Stage currentStage = (Stage) currentScene.getWindow();
        Scene newScene = new Scene(formProduit, currentScene.getWidth(), currentScene.getHeight());
        currentStage.setScene(newScene);
    }
    
}
