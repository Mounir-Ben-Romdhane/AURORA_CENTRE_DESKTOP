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
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import service.ServiceReservation;



public class ReservationBackController implements Initializable {

    @FXML
    private TableView<Reservation> tvReservation;
    
     @FXML
    private TableColumn<Reservation, Integer> colNumero;
    @FXML
    private TableColumn<Reservation, String> colUserName;
    @FXML
    private TableColumn<Reservation, String> colEmail;
    @FXML
    private TableColumn<Reservation, Integer> colNumeroTel;
    @FXML
    private TableColumn<Reservation, String> colService_ID;
     @FXML
    private TableColumn<Reservation,LocalDateTime > colDate;
    
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnModif;
    @FXML
    private Button btnDel;
 @FXML
    private Button btnRetour;
    
     service.ServiceReservation serviceReservation = new ServiceReservation();
    private static ObservableList<Reservation> observableListReservation;
    int index = -1 ;
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        updateTable();
    }    
    
    public void updateTable(){
        observableListReservation = FXCollections.observableArrayList(serviceReservation.readAll());
        
        colNumero.setCellValueFactory(new PropertyValueFactory<Reservation, Integer>("numero"));
        colUserName.setCellValueFactory(new PropertyValueFactory<Reservation, String>("user_name"));
        colEmail.setCellValueFactory(new PropertyValueFactory<Reservation, String>("email"));
        colDate.setCellValueFactory(new PropertyValueFactory<Reservation, LocalDateTime>("dateR"));
        colNumeroTel.setCellValueFactory(new PropertyValueFactory<Reservation, Integer>("numeroTel"));
        colService_ID.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId_service().getTitreS()));
        tvReservation.setItems(observableListReservation);
    }
    
       
    public void handleButtonAjout(MouseEvent event) throws IOException {
        AnchorPane formService = new AnchorPane();
        formService.getChildren().add(FXMLLoader.load(getClass().getResource("/gui/AjouterReservation.fxml")));
        Scene currentScene = btnAdd.getScene();
        Stage currentStage = (Stage) currentScene.getWindow();
        Scene newScene = new Scene(formService, currentScene.getWidth(), currentScene.getHeight());
        currentStage.setScene(newScene);
    }

  public void handleButtonModif(MouseEvent event) throws IOException {

        Reservation selected = tvReservation.getSelectionModel().getSelectedItem();
        if (selected == null) {
            JOptionPane.showMessageDialog(null, "Veuillez sélectionner une reservaton à modifier.");
            return;
        }
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/ModifierReservation.fxml"));
        AnchorPane formRes = loader.load();

        ModifierReservationController controller = loader.getController();
        controller.setSelectedRes(selected);

        Scene currentScene = btnModif.getScene();
        Stage currentStage = (Stage) currentScene.getWindow();
        Scene newScene = new Scene(formRes, currentScene.getWidth(), currentScene.getHeight());
        currentStage.setScene(newScene);
    }
  
  
  
  
  
        public void handleButtonDel(MouseEvent event) throws IOException {
            if (event.getSource() == btnDel) {
                 getSelected();
                System.out.println("Selected index: " + index);
                if (index <= -1) {
                    JOptionPane.showMessageDialog(null, "Sélectionnez une reservation à supprimer.");
                    return;
                }
                Integer reservationNumero = observableListReservation.get(index).getId();
                int confirm = JOptionPane.showConfirmDialog(null, "Êtes-vous sûr de vouloir supprimer la réservation \"" + reservationNumero + "\" ?");
                if (confirm == JOptionPane.YES_OPTION) {
                    try {
                        System.out.println("Deleting reservation: " + reservationNumero);
                        serviceReservation.delete(reservationNumero);
                    } catch (SQLException ex) {
                        Logger.getLogger(ReservationBackController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                updateTable();
            }
        }

   public void getSelected() {
        index = tvReservation.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }
        updateTable();
    }
    
   
   @FXML
    public void handleBtnRetour(MouseEvent event) throws IOException {
        AnchorPane formProduit = new AnchorPane();
        formProduit.getChildren().add(FXMLLoader.load(getClass().getResource("/ui/ServiceFront.fxml")));
        Scene currentScene = btnRetour.getScene();
        Stage currentStage = (Stage) currentScene.getWindow();
        Scene newScene = new Scene(formProduit, currentScene.getWidth(), currentScene.getHeight());
        currentStage.setScene(newScene);
    }
    
}
