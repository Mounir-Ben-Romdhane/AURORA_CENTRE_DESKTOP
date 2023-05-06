/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ui;

import animatefx.animation.BounceIn;
import entite.Reservation;
import entite.Service;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
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
public class AjouterReservationController implements Initializable {

    @FXML
    private TextField tfNumero;
    @FXML
    private TextField tfuserName;
    @FXML
    private ChoiceBox<String> cbService;
    @FXML
    private DatePicker dateR;
    @FXML
    private Button btnAjout;
    @FXML
    private TextField tfemail;
    @FXML
    private TextField tfnumeroTel;
    
    @FXML
    private TextField tfFullNameUser;

    
    ServiceReservation serviceRes = new ServiceReservation();
    ServiceService serviceSer = new ServiceService();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
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
        });
    } 

    

    @FXML
    private void ajouter(MouseEvent event) throws IOException {
         String numero = tfNumero.getText();
    String userName = tfuserName.getText();
    String email = tfemail.getText();
    String numeroTel = tfnumeroTel.getText();
    LocalDate date = dateR.getValue();
    String serviceName = cbService.getValue();

    // Validate the input fields
    if (numero.isEmpty() || userName.isEmpty() || email.isEmpty() || numeroTel.isEmpty() || date == null || serviceName == null) {
        // Show an error message if any field is empty
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setContentText("Veillez remplir tous les champs");
        alert.showAndWait();
        return;
    }

    //Controle de saisie pour numero
    if (numero.length() > 3) {
        JOptionPane.showMessageDialog(null, "Ce champs doit contenir au moins 3 caractères");
        return; // Return to stop further execution of the method
    } 

    //Controle de saisie pour numeroTel
    String regex = "^[0-9]{8}$";
    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(numeroTel);
    if (!matcher.matches()) {
        JOptionPane.showMessageDialog(null, "NumeroTel doit contenir 8 chiffres et seulement des chiffres");
        return;
    } 

    //Controle de saisie pour l'email
    String regex1 = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z]{2,}$";
    Pattern pattern1 = Pattern.compile(regex1, Pattern.CASE_INSENSITIVE);
    Matcher matcher1 = pattern1.matcher(email);
    if (!matcher1.matches()) {
        JOptionPane.showMessageDialog(null, "Email doit respecter le format d'un email");
        return;
    } 

    //Controle de saisie pour username
    String regex2 = "^[a-zA-Z]+$";
    Pattern pattern2 = Pattern.compile(regex2);
    Matcher matcher2 = pattern2.matcher(userName);
    if (!matcher2.matches()) {
        JOptionPane.showMessageDialog(null, "UserName ne doit contenir que des lettres.");
        return;
    }

    if (date == null || date.isBefore(LocalDate.now())) {
        JOptionPane.showMessageDialog(null, "La date doit être valide.");
        return;
    }
    
    int numeroTels = Integer.parseUnsignedInt(numeroTel);
    int numeros = Integer.parseUnsignedInt(numero);
    Service service = serviceSer.getServiceByName(serviceName);
    LocalDateTime dateR = LocalDateTime.now();

    Reservation reservation = new Reservation(numeros, numeroTels, email, userName, dateR, service, "ff");
    System.out.println(reservation);
    serviceRes.insert(reservation);
    Stage currentStage = (Stage) tfuserName.getScene().getWindow();
                            currentStage.close();
    
    FXMLLoader loader = new FXMLLoader(getClass().getResource("DashboardClient.fxml"));
        
                Parent root = loader.load();
                tfuserName.getScene().setRoot(root);
                
              new BounceIn(root).play();
    JOptionPane.showMessageDialog(null, "Reservation added successfully!");
}
}