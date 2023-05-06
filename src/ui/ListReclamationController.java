/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ui;


import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import entite.Reclamation;
import entite.User;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import service.ReclamationService;
import util.DataSource;

/**
 * FXML Controller class
 *
 * @author malek
 */
public class ListReclamationController implements Initializable {

    @FXML
    private TableColumn<Reclamation, String> txtType;
    @FXML
    private TableColumn<Reclamation, String> txtDate;
    @FXML
    private TableColumn<Reclamation, String> txtStatus;
    @FXML
    private TableColumn<Reclamation, String> txtEtat;
    @FXML
    private TableColumn<Reclamation, String> txtEmail;
    @FXML
    private TableColumn<Reclamation, String> txtDes;
    
    Reclamation reclamation=null;
    @FXML
    private TableView<Reclamation> tableViewReclamation;
    @FXML
    private TableColumn<Reclamation, String> txtAction;
    @FXML
    private Button txttous;
    @FXML
    private Button txtencours;
    int nb_encours=0;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
         try {
            // TODO
            txtType.setCellValueFactory(new PropertyValueFactory<>("type"));
    txtDes.setCellValueFactory(new PropertyValueFactory<>("description"));
    txtEmail.setCellValueFactory(new PropertyValueFactory<>("email_reclamation"));
    txtDate.setCellValueFactory(new PropertyValueFactory<>("date_reclamation"));
  Callback<TableColumn.CellDataFeatures<Reclamation, String>, ObservableValue<String>> statusCellValueFactory =
    new Callback<TableColumn.CellDataFeatures<Reclamation, String>, ObservableValue<String>>() {
        @Override
        public ObservableValue<String> call(TableColumn.CellDataFeatures<Reclamation, String> param) {
            int status = param.getValue().getStatus();
            switch (status) {
               case 0:
                   return new SimpleStringProperty("En cours");
               case 1:
                   return new SimpleStringProperty("Accepter");
               case 2:
                   return new SimpleStringProperty("Refuser");
               default:
                   return new SimpleStringProperty("Unknown");
            }
        }
    };
txtEtat.setCellValueFactory(statusCellValueFactory);
            
            loadData();
            if(nb_encours!=0){
                 Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("Alert Dialog");
                            alert.setContentText("There are " + nb_encours + " unprocessed claims in the system. Please review them as soon as possible.");
                            alert.setHeaderText(null);
                            Optional <ButtonType> action = alert.showAndWait();
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(AfficheUsersController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }  
     private void loadData() throws SQLException { 
        //add cell of button edit 
         Callback<TableColumn<Reclamation, String>, TableCell<Reclamation, String>> cellFoctory = (TableColumn<Reclamation, String> param) -> {
            // make cell containing buttons
            final TableCell<Reclamation, String> cell = new TableCell<Reclamation, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    //that cell created only on non-empty rows
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {

                        FontAwesomeIconView deleteIcon = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
                        FontAwesomeIconView editIcon = new FontAwesomeIconView(FontAwesomeIcon.LOCK);

                        deleteIcon.setStyle(
                                " -fx-cursor: hand ;"
                                + "-glyph-size:28px;"
                                + "-fx-fill:#ff1744;"
                        );
                        editIcon.setStyle(
                                " -fx-cursor: hand ;"
                                + "-glyph-size:28px;"
                                + "-fx-fill:#00E676;"
                        );
                        deleteIcon.setOnMouseClicked((MouseEvent event) -> {
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("Confirmation Dialog");
                            alert.setContentText("Are you sure to delete this claim ?");
                            alert.setHeaderText(null);
                            Optional <ButtonType> action = alert.showAndWait();
                            if(action.get() == ButtonType.OK ){
                                reclamation = tableViewReclamation.getSelectionModel().getSelectedItem();
                                ReclamationService rs=new ReclamationService();
                                rs.delete(reclamation.getId());
                                tableViewReclamation.setItems(getReclamationData(0)); 
                                tableViewReclamation.refresh();
                            }
                        });
                        editIcon.setOnMouseClicked((MouseEvent event) -> {
                            /*
                            user = tableViewUsers.getSelectionModel().getSelectedItem();
                            FXMLLoader loader = new FXMLLoader ();
                            loader.setLocation(getClass().getResource("AddUser.fxml"));
                            try {
                                loader.load();
                            } catch (IOException ex) {
                                Logger.getLogger(AfficheUsersController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            
                            */
                        });

                        //HBox managebtn = new HBox(editIcon, deleteIcon);
                        HBox managebtn = new HBox(deleteIcon);
                        managebtn.setStyle("-fx-alignment:center");
                        HBox.setMargin(deleteIcon, new Insets(2, 2, 0, 3));
                        HBox.setMargin(editIcon, new Insets(2, 3, 0, 2));

                        setGraphic(managebtn);

                        setText(null);

                    }
                }

            };

            return cell;
        };
          Callback<TableColumn<Reclamation, String>, TableCell<Reclamation, String>> cellFoctory2 = (TableColumn<Reclamation, String> param) -> {
            // make cell containing buttons
            final TableCell<Reclamation, String> cell = new TableCell<Reclamation, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    //that cell created only on non-empty rows
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {

                        Button accepteButton = new Button("Accepter");
                        Button refuseButton = new Button("Refuser");

                        accepteButton.setStyle(
                                " -fx-cursor: hand ;"
                                + "-glyph-size:28px;"
                                + "-fx-fill:#ff1744;"
                                + "-fx-background-color:green;"
                        );
                        refuseButton.setStyle(
                                " -fx-cursor: hand ;"
                                + "-glyph-size:28px;"
                                + "-fx-fill:#00E676;"
                                + "-fx-background-color:#ff1744;"
                        );
                        accepteButton.setOnMouseClicked((MouseEvent event) -> {
                            
                            Reclamation selectedItem = tableViewReclamation.getSelectionModel().getSelectedItem();
                             if (selectedItem != null) {
                          ReclamationService ps = new ReclamationService();
                          ps.update(new Reclamation(selectedItem.getType(), selectedItem.getNom(), selectedItem.getDescription(), 1, selectedItem.getDate_reclamation(),selectedItem.getEmail_connecte(), selectedItem.getEmail_reclamation()),selectedItem.getId());
                        tableViewReclamation.setItems(getReclamationData(0));
                          tableViewReclamation.refresh();
                             }});
                        refuseButton.setOnMouseClicked((MouseEvent event) -> {
                            Reclamation selectedItem = tableViewReclamation.getSelectionModel().getSelectedItem();
                             if (selectedItem != null) {
                          ReclamationService ps = new ReclamationService();
                          ps.update(new Reclamation(selectedItem.getType(), selectedItem.getNom(), selectedItem.getDescription(), 2, selectedItem.getDate_reclamation(),selectedItem.getEmail_connecte(), selectedItem.getEmail_reclamation()),selectedItem.getId());
                          tableViewReclamation.setItems(getReclamationData(0)); 
                          tableViewReclamation.refresh();
                             }});

                        //HBox managebtn = new HBox(editIcon, deleteIcon);
                        HBox managebtn = new HBox(refuseButton, accepteButton);
                        managebtn.setStyle("-fx-alignment:center");
                        HBox.setMargin(accepteButton, new Insets(2, 2, 0, 3));
                        HBox.setMargin(refuseButton, new Insets(2, 3, 0, 2));

                        setGraphic(managebtn);

                        setText(null);

                    }
                }

            };

            return cell;
        };
         txtAction.setCellFactory(cellFoctory);
         txtStatus.setCellFactory(cellFoctory2);
         tableViewReclamation.setItems(getReclamationData(0));
         
        
    }
     private ObservableList<Reclamation> getReclamationData(int statusFilter) {
    
    ReclamationService ps = new ReclamationService();
    List<Reclamation> listp = ps.readAll();
    List<Reclamation> filteredList = new ArrayList<>();
    for (Reclamation reclamation : listp) {
        if (reclamation.getStatus() == statusFilter) {
            nb_encours++;
            filteredList.add(reclamation);
        }
    }
    return FXCollections.observableArrayList(filteredList);
}

    @FXML
    private void getallclaim(ActionEvent event) {
      ReclamationService ps = new ReclamationService();
    List<Reclamation> listp = ps.readAll();  
    tableViewReclamation.setItems(FXCollections.observableArrayList(listp));
    tableViewReclamation.refresh();
    }

    @FXML
    private void getEncours(ActionEvent event) {
        tableViewReclamation.setItems(getReclamationData(0));
    tableViewReclamation.refresh();
    }
  
    
}

