/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ui;

import entite.Service;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableListBase;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javax.swing.SpringLayout;
import pidevdesktop.mainFx;
import service.ServiceService;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


/**
 * FXML Controller class
 *
 * @author mfmma
 */
public class ServiceFrontController implements Initializable {

    @FXML
    private VBox chosenFruitCard;

    @FXML
    private ScrollPane scroll;
    @FXML
    private GridPane grid;

   
    @FXML
    private Button btnReserver;

     @FXML
    private Label ServiceNameLabel;

    @FXML
    private Label ServiceTypeLabel;

    @FXML
    private Label serviceDesLabel;
    
    private List<Service> service = new ArrayList<>();
    private Image image;
    private itemController.MyListener myListener;
    
    
    ServiceService serviceService = new ServiceService();
    @FXML
    private TextField filterField;
    
    private ObservableList<Service> observableListServices = FXCollections.observableArrayList();
    private List<Service> allServices = new ArrayList<>();
    @FXML
    private VBox VBoxService;
    @FXML
    private ImageView SeviceImg;



    
   @Override
public void initialize(URL url, ResourceBundle rb) {

    List<Service> serviceList = serviceService.readAll();
    ObservableList<Service> observableListServices = FXCollections.observableArrayList();
    observableListServices.addAll(serviceList);

    System.out.println(observableListServices);

    System.out.println(serviceService.readAll());
    myListener = new itemController.MyListener() {
        @Override
        public void onClickListener(Service service) {
            setChosenFruit(service);
        }
    };
    if (observableListServices.size() > 0) {
        setChosenFruit(observableListServices.get(0));
        System.out.println("ee");
    }
    int column = 0;
    int row = 1;
    try {
        for (int i = 0; i < observableListServices.size(); i++) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/ui/item.fxml"));
            AnchorPane anchorPane = fxmlLoader.load();

            itemController itemController = fxmlLoader.getController();
            itemController.setData(observableListServices.get(i), myListener);

            if (column == 3) {
                column = 0;
                row++;
            }

            grid.add(anchorPane, column++, row); //(child,column,row)

            GridPane.setMargin(anchorPane, new Insets(10));
        }

        //set grid width
        grid.setMinWidth(Region.USE_COMPUTED_SIZE);
        grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
        grid.setMaxWidth(Region.USE_PREF_SIZE);

        //set grid height
        grid.setMinHeight(Region.USE_COMPUTED_SIZE);
        grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
        grid.setMaxHeight(Region.USE_PREF_SIZE);

    } catch (IOException e) {
        e.printStackTrace();
    }
}



    private void setChosenFruit(Service service) {
         

        ServiceNameLabel.setText(service.getTitreS());
        ServiceTypeLabel.setText(service.getTypeS()); // Corrected name
        image = new Image(getClass().getResourceAsStream("/image/"+service.getImage()));
        SeviceImg.setImage(image);
        
        serviceDesLabel.setText(service.getDescrptionS());
                 System.out.println("/image/"+service.getImage()); 
    }

   
      /*  FilteredList<Service> filteredData = new FilteredList<>(dataList, b -> true);
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
      
    }*/
 

    @FXML
    private void handleClick(ActionEvent event) throws IOException {
        
        Parent root = FXMLLoader.load(getClass().getResource("AjouterReservation.fxml"));
        // Create the new stage
                Stage newStage = new Stage();
                // Set the title of the new stage
                newStage.setTitle("Ajout Reservation");
                // Create the scene for the new stage
                Scene scene = new Scene(root);
                newStage.setScene(scene);
                // Show the new stage
                newStage.show();
    }

    }


