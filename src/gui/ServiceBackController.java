/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package gui;

import com.mysql.jdbc.integration.c3p0.MysqlConnectionTester;
import entite.Service;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableListBase;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import service.ServiceService;

/**
 * FXML Controller class
 *
 * @author mfmma
 */
public class ServiceBackController implements Initializable {

    @FXML
    private TableView<Service> tvService;

    @FXML
    private TableColumn<Service, String> colTitre;

    @FXML
    private TableColumn<Service, String> colDes;

    @FXML
    private TableColumn<Service, String> colType;

    @FXML
    private TableColumn<Service, LocalDateTime> colDate;

    @FXML
    private TableColumn<Service, String> colImage;
    
    @FXML
    private Button btnAdd;

    @FXML
    private Button btnModif;

    @FXML
    private Button btnDel;
    @FXML
    private Button btnRetour;
    @FXML
    private TextField filterField;
    
       @FXML
    private Button btnFilter;

    
    ObservableList<Service> dataList;
    

    
    service.ServiceService serviceService = new ServiceService();
    private static ObservableList<Service> observableListService;
    int index = -1 ;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        updateTable();
        search_service();
    }    
    
    public void updateTable(){
        observableListService = FXCollections.observableArrayList(serviceService.readAll());
        colTitre.setCellValueFactory(new PropertyValueFactory<Service, String>("titreS"));
        colDes.setCellValueFactory(new PropertyValueFactory<Service, String>("descrptionS"));
        colType.setCellValueFactory(new PropertyValueFactory<Service, String>("typeS"));
        colDate.setCellValueFactory(new PropertyValueFactory<Service, LocalDateTime>("dateS"));
        colImage.setCellValueFactory(new PropertyValueFactory<Service, String>("image"));

        tvService.setItems(observableListService);
        System.out.println(observableListService);
        
        dataList = FXCollections.observableArrayList(serviceService.readAll());
        
    }
    
    
    public void handleButtonAjout(MouseEvent event) throws IOException {
        AnchorPane formService = new AnchorPane();
        formService.getChildren().add(FXMLLoader.load(getClass().getResource("/gui/AjouterService.fxml")));
        Scene currentScene = btnAdd.getScene();
        Stage currentStage = (Stage) currentScene.getWindow();
        Scene newScene = new Scene(formService, currentScene.getWidth(), currentScene.getHeight());
        currentStage.setScene(newScene);
    }
    
  
    
      public void handleButtonDel(MouseEvent event) throws IOException {
        if (event.getSource() == btnDel) {
            getSelected();
            if (index <= -1) {
                JOptionPane.showMessageDialog(null, "Sélectionnez un service à supprimer.");
                return;
            }

            String serviceName = observableListService.get(index).getTitreS();
            int confirm = JOptionPane.showConfirmDialog(null, "Êtes-vous sûr de vouloir supprimer le Service \"" + serviceName + "\" ?");
            if (confirm != JOptionPane.YES_OPTION) {
                return;
            }

            try {
                serviceService.delete(observableListService.get(index).getId());
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Impossible de supprimer le sevice sélectionnée car elle est utilisée par des Reservations.");
                return;
            }

            updateTable();
                    search_service();

        }
    }
      
      public void getSelected() {
        index = tvService.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }
        updateTable();
    }
      
     @FXML
    void handleButtonUpdate() throws IOException {
        Service selectedService = tvService.getSelectionModel().getSelectedItem();
        if (selectedService == null) {
            JOptionPane.showMessageDialog(null, "Veuillez sélectionner un service à modifier.");
            return;
        }
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/ModifierService.fxml"));
        AnchorPane formCategorie = loader.load();

        ModifierServiceController controller = loader.getController();
        controller.setSelectedCtegorie(selectedService);

        Scene currentScene = btnModif.getScene();
        Stage currentStage = (Stage) currentScene.getWindow();
        Scene newScene = new Scene(formCategorie, currentScene.getWidth(), currentScene.getHeight());
        currentStage.setScene(newScene);
    }
    
     @FXML
    public void handleBtnRetour(MouseEvent event) throws IOException {
        AnchorPane formProduit = new AnchorPane();
        formProduit.getChildren().add(FXMLLoader.load(getClass().getResource("/gui/menu.fxml")));
        Scene currentScene = btnRetour.getScene();
        Stage currentStage = (Stage) currentScene.getWindow();
        Scene newScene = new Scene(formProduit, currentScene.getWidth(), currentScene.getHeight());
        currentStage.setScene(newScene);
    }
    

   
      public void search_service() {
    FilteredList<Service> filteredData = new FilteredList<>(dataList, b -> true);
    filterField.textProperty().addListener((observable, oldValue, newValue) -> {
        filteredData.setPredicate(service -> {
            if (newValue == null || newValue.isEmpty()) {
                return true;
            }
            String lowerCaseFilter = newValue.toLowerCase();
            if (service.getTitreS().toLowerCase().contains(lowerCaseFilter)
                    || service.getDescrptionS().toLowerCase().contains(lowerCaseFilter)
                    || service.getTypeS().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            } else {
                LocalDateTime serviceDate = service.getDateS();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                String formattedDate = serviceDate.format(formatter);
                return formattedDate.contains(lowerCaseFilter);
            }
        });
        SortedList<Service> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tvService.comparatorProperty());
        tvService.setItems(sortedData);
    });
}
      
      
      
       

   


}
