/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package GUI;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author MSI
 */
public class ProductController implements Initializable {

    @FXML
    private TextField tName;
    @FXML
    private TextField tPrice;
    @FXML
    private TextField tDescription;
    @FXML
    private TextField tImage;
    @FXML
    private Label catLabel;
    @FXML
    private ChoiceBox<?> myChoiceBox;
    @FXML
    private TableView<?> table;
    @FXML
    private TableColumn<?, ?> colId;
    @FXML
    private TableColumn<?, ?> colName;
    @FXML
    private TableColumn<?, ?> colPrice;
    @FXML
    private TableColumn<?, ?> colDescription;
    @FXML
    private TableColumn<?, ?> colImage;
    @FXML
    private TableColumn<?, ?> colCategory;
    @FXML
    private Button btnSave;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnClear;
    @FXML
    private Label fluxRss;
    @FXML
    private TextField tfrecherche;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void getData(MouseEvent event) {
    }

    @FXML
    private void createProduct(ActionEvent event) {
    }

    @FXML
    private void updateProduct(ActionEvent event) {
    }

    @FXML
    private void deleteProduct(ActionEvent event) {
    }

    @FXML
    private void clearProduct(ActionEvent event) {
    }


    @FXML
    private void rechercher(ActionEvent event) {
    }

    @FXML
    private void tri(ActionEvent event) {
    }

    @FXML
    private void afficher(ActionEvent event) {
    }
    
}
