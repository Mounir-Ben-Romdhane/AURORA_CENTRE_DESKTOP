/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ui;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import entite.User;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import util.DataSource;

/**
 * FXML Controller class
 *
 * @author MSI
 */
public class AfficheUsersController implements Initializable {

    @FXML
    private VBox VBoxShowUsers;
    @FXML
    private TableView<User> tableViewUsers;
    @FXML
    private TableColumn<User, String> fullNameCol;
    @FXML
    private TableColumn<User, String> emailCol;
    @FXML
    private TableColumn<User, String> addCol;
    @FXML
    private TableColumn<User, String> numTelCol;
    @FXML
    private TableColumn<User, String> actionsCol;
    
    String query = null;
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    User user = null;
    
    ObservableList<User> userList = FXCollections.observableArrayList();
    
    
    int userId;
    @FXML
    private TableColumn<User, String> isVerifiedCol;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            loadData();
        } catch (SQLException ex) {
            Logger.getLogger(AfficheUsersController.class.getName()).log(Level.SEVERE, null, ex);
        }
    } 
    
    private void refrachListClck(){
       userList.clear();
       String res;
        try {
             
        query = "SELECT * FROM `user`";
        preparedStatement = connection.prepareStatement(query);
        resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                
                if(resultSet.getInt("is_verified")==1){
                    res="Compte Verified";
                }else{
                    res="Compte non Verified";
                }
                
                userList.add(new User(
                        resultSet.getInt("id"),
                        resultSet.getString("username"),
                        resultSet.getString("email"),
                        resultSet.getString("num_tel"),
                        resultSet.getString("full_address"),
                        res
                ));
                tableViewUsers.setItems(userList);
            }
            preparedStatement.close();
            resultSet.close();
        } catch (SQLException ex) {
            Logger.getLogger(AfficheUsersController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    

    private void loadData() throws SQLException {
        
        connection = DataSource.getInstance().getCnx();
        refrachListClck();
        
        fullNameCol.setCellValueFactory(new PropertyValueFactory<>("userName"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        addCol.setCellValueFactory(new PropertyValueFactory<>("fullAddress"));
        numTelCol.setCellValueFactory(new PropertyValueFactory<>("numTel"));
        isVerifiedCol.setCellValueFactory(new PropertyValueFactory<>("isVerified"));
        
        
        //add cell of button edit 
         Callback<TableColumn<User, String>, TableCell<User, String>> cellFoctory = (TableColumn<User, String> param) -> {
            // make cell containing buttons
            final TableCell<User, String> cell = new TableCell<User, String>() {
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
                            alert.setContentText("Are you sure to delete this user ?");
                            alert.setHeaderText(null);
                            Optional <ButtonType> action = alert.showAndWait();
                            if(action.get() == ButtonType.OK ){
                                try {
                                user = tableViewUsers.getSelectionModel().getSelectedItem();
                                query = "DELETE FROM `user` WHERE id  ="+user.getId();
                                
                                preparedStatement = connection.prepareStatement(query);
                                preparedStatement.execute();
                                refrachListClck();
                                
                            } catch (SQLException ex) {
                                Logger.getLogger(AfficheUsersController.class.getName()).log(Level.SEVERE, null, ex);
                            }
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
         actionsCol.setCellFactory(cellFoctory);
         tableViewUsers.setItems(userList);
         
        
    }
    
    
    

    
    
}
