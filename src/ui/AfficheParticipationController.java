/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ui;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import entite.Participation;
import entite.Service;
import entite.User;
import entite.evenement;


import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import service.EvenementService;
import service.ParticipationService;
import service.ServiceReservation;
import service.ServiceService;
import util.DataSource;

/**
 * FXML Controller class
 *
 * @author MSI
 */
public class AfficheParticipationController implements Initializable {

    @FXML
    private VBox VBoxShowReservations;
    @FXML
    private TableView<Participation> tvReservation;
    @FXML
    private TableColumn<Participation, String> colUserName;
    @FXML
    private TableColumn<Participation, String> colEmail;
    @FXML
    private TableColumn<Participation, String> colNumeroTel;
    @FXML
    private TableColumn<Participation, String> colService_ID;
    @FXML
    private TableColumn<Participation, String> actionsCol;
    @FXML
    private TextField filterField;
    @FXML
    private ComboBox<String> txtTri;
    @FXML
    private Button btnExcel;
    @FXML
    private Button btnPDF;
    
     Participation participation = null;
     
      
    String query = null;
    Connection connection = DataSource.getInstance().getCnx();
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

  
    ObservableList<Participation> dataList=FXCollections.observableArrayList();
    
    ParticipationService participationService = new ParticipationService();
    private static ObservableList<Participation> observableListParticipation;
    int index = -1 ;
    @FXML
    private TableColumn<Participation, String> colDescription;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO

            loadData();
//        search_service();
        } catch (SQLException ex) {
            Logger.getLogger(AfficheParticipationController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
    
    private void refrachListClck(){
       dataList.clear();
      
       
        try {
             
        query = "SELECT * FROM `participationns` ";
        preparedStatement = connection.prepareStatement(query);
        resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                
               
                 int service_id = resultSet.getInt("evenement_id");
                EvenementService es = new EvenementService();
                evenement ev = es.FetchOneEvent(service_id);
                
                dataList.add(new Participation(
                        resultSet.getInt("id"),
                        resultSet.getString("description_pn"),
                        resultSet.getString("usernameev"),
                        resultSet.getString("emailev"),
                        resultSet.getInt("numtelev"),
                        ev
                ));
                tvReservation.setItems(dataList);
            }
            preparedStatement.close();
            resultSet.close();
        } catch (SQLException ex) {
            Logger.getLogger(AfficheUsersController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(AfficheUsersController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
   private void loadData() throws SQLException {
        
        
        
        refrachListClck();
        
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description_pn"));
        colUserName.setCellValueFactory(new PropertyValueFactory<>("usernameev"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("emailev"));
        colNumeroTel.setCellValueFactory(new PropertyValueFactory<>("numtelev"));
        colService_ID.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEvenement_id().getTitreev()));
        
        
        
        
        
        //add cell of button edit 
         Callback<TableColumn<Participation, String>, TableCell<Participation, String>> cellFoctory = (TableColumn<Participation, String> param) -> {
            // make cell containing buttons
            final TableCell<Participation, String> cell = new TableCell<Participation, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    //that cell created only on non-empty rows
                    if (empty) {
                        setGraphic(null);
                        setText(null);

                    } else {

                        FontAwesomeIconView deleteIcon = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
                       // FontAwesomeIconView blockIcon = new FontAwesomeIconView(FontAwesomeIcon.BAN);
                           
                        deleteIcon.setStyle(
                                " -fx-cursor: hand ;"
                                + "-glyph-size:28px;"
                                + "-fx-fill:#ff1744;"
                        );
                        
                       
                        
                        deleteIcon.setOnMouseClicked((MouseEvent event) -> {
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("Confirmation Dialog");
                            alert.setContentText("Are you sure to delete this user ?");
                            alert.setHeaderText(null);
                            Optional <ButtonType> action = alert.showAndWait();
                            if(action.get() == ButtonType.OK ){
                               try {
                                participation = tvReservation.getSelectionModel().getSelectedItem();
                                query = "DELETE FROM `participationns` WHERE id  ="+participation.getId();
                                
                                preparedStatement = connection.prepareStatement(query);
                                preparedStatement.execute();
                                refrachListClck();
                                
                            } catch (SQLException ex) {
                                Logger.getLogger(AfficheUsersController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            }
                        });
                        
                       
                        
                       
                        HBox managebtn = new HBox( deleteIcon);
                       // HBox managebtn = new HBox(deleteIcon);
                        managebtn.setStyle("-fx-alignment:center");
                        HBox.setMargin(deleteIcon, new Insets(2, 2, 0, 3));
                        //HBox.setMargin(blockIcon, new Insets(2, 3, 0, 2));

                        setGraphic(managebtn);

                        setText(null);

                    }
                }

            };

            return cell;
        };
         actionsCol.setCellFactory(cellFoctory);
         tvReservation.setItems(dataList);
         
        
    }
       
     public void updateTable(){
        dataList = FXCollections.observableArrayList(participationService.readAll());
        
        //colDescription.setCellValueFactory(new PropertyValueFactory<participation, Integer>("id"));
        colDescription.setCellValueFactory(new PropertyValueFactory<Participation, String>("description_pn"));
        colUserName.setCellValueFactory(new PropertyValueFactory<Participation, String>("usernameev"));
        colEmail.setCellValueFactory(new PropertyValueFactory<Participation, String>("emailev"));
        colNumeroTel.setCellValueFactory(new PropertyValueFactory<Participation, String>("numtelev"));
        
        
//        
//        colService_ID.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEvenement_id().getTitreev()));
//        tvReservation.setItems(observableListParticipation);
        
         System.out.println(observableListParticipation);
//        
//         Callback<TableColumn<Participation, String>, TableCell<Participation, String>> cellFoctory = (TableColumn<Participation, String> param) -> {
//            // make cell containing buttons
//            final TableCell<Participation, String> cell = new TableCell<Participation, String>() {
//                @Override
//                public void updateItem(String item, boolean empty) {
//                    super.updateItem(item, empty);
//                    //that cell created only on non-empty rows
//                    if (empty) {
//                        setGraphic(null);
//                        setText(null);
//                    } else {
//
//                        FontAwesomeIconView deleteIcon = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
//                        FontAwesomeIconView editIcon = new FontAwesomeIconView(FontAwesomeIcon.EDIT);
//
//                        deleteIcon.setStyle(
//                                " -fx-cursor: hand ;"
//                                + "-glyph-size:28px;"
//                                + "-fx-fill:#ff1744;"
//                        );
//                        editIcon.setStyle(
//                                " -fx-cursor: hand ;"
//                                + "-glyph-size:28px;"
//                                + "-fx-fill:#00E676;"
//                        );
//                        deleteIcon.setOnMouseClicked((MouseEvent event) -> {
//                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
//                            alert.setTitle("Confirmation Dialog");
//                            alert.setContentText("Are you sure to delete this claim ?");
//                            alert.setHeaderText(null);
//                            Optional <ButtonType> action = alert.showAndWait();
//                            if(action.get() == ButtonType.OK ){
//                                participation = tvReservation.getSelectionModel().getSelectedItem();
//                                ParticipationService rs=new ParticipationService();
//                                rs.delete(participation.getId());
//                                tvReservation.setItems(getParticipationData());
//                                tvReservation.refresh();
//                            }
//                        });
//                        editIcon.setOnMouseClicked((MouseEvent event) -> {
//                            try {
//                                FXMLLoader loader = new FXMLLoader(getClass().getResource("ModifierReservation.fxml"));
//                                Parent root = loader.load();
//                                Stage newStage = new Stage();
//                                // Set the title of the new stage
//                                newStage.setTitle("Modif Reservation");
//                                // Create the scene for the new stage
//                                Scene scene = new Scene(root);
//                                newStage.setScene(scene);
//                                // Show the new stage
//                                newStage.show();
//                                //ModifierReservationController dc = loader.getController();
////                                participation = tvReservation.getSelectionModel().getSelectedItem();
////                                ServiceReservation rs=new ServiceReservation();
////                                dc.setSelectedRes(participation);
////                                dc.setData(participation.getId());
//                            } catch (IOException ex) {
//                                Logger.getLogger(AfficheServicesController.class.getName()).log(Level.SEVERE, null, ex);
//                            }
//                        });
//
//                        //HBox managebtn = new HBox(editIcon, deleteIcon);
//                        HBox managebtn = new HBox(deleteIcon,editIcon);
//                        managebtn.setStyle("-fx-alignment:center");
//                        HBox.setMargin(deleteIcon, new Insets(2, 2, 0, 3));
//                        HBox.setMargin(editIcon, new Insets(2, 3, 0, 2));
//
//                        setGraphic(managebtn);
//
//                        setText(null);
//
//                    }
//                }
//
//            };
//
//            return cell;
//        };
//        actionsCol.setCellFactory(cellFoctory);
//        tvReservation.setItems(this.getParticipationData());
//        dataList = FXCollections.observableArrayList(participationService.readAll());
} 
    
    
//    
    private ObservableList<Participation> getParticipationData() {
    
    ParticipationService ps = new ParticipationService();
    List<Participation> listp = ps.readAll();
////    List<Partcipation> filteredList = new ArrayList<>();
//    for (Partcipation rec : listp) {
//        if (rec.getEmail_connecte().equals(email)) {
//            filteredList.add(rec);
//        }
//    }

    return FXCollections.observableArrayList(listp);
}
    
    
    
    

    @FXML
    private void search_service(MouseEvent event) {
    }

    @FXML
    private void excelfile(ActionEvent event) {
    }

    @FXML
    private void PDFGeneration(ActionEvent event) {
    }
    
}
