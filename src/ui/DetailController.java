/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ui;

import entite.User;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import service.UserService;

/**
 * FXML Controller class
 *
 * @author MSI
 */
public class DetailController implements Initializable {

    /*
    @FXML
    private Label lNom;
    @FXML
    private ListView<User> listViewPersonne;
    
    public void setLabelNom(String nom){
        this.lNom.setText(nom);
    }
    */

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        /*
        UserService ps = new UserService();
        
        List<User> listP = ps.readAll();
        
        ObservableList<User> obs = FXCollections.observableArrayList((ArrayList)listP.stream().map(user -> user.getUserName()).collect(Collectors.toList()));
        
        listViewPersonne.setItems(obs);
*/
    }    
    
}
