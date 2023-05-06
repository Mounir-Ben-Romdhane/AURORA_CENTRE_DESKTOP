/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import entite.Order;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Samar
 */
public class ModifierOrderController implements Initializable {

    @FXML
    private Button ModifierBtn;
    private TextField idOrder;
    private TextField prix;
    @FXML
    private TextField qntProduit;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }   
    
    Order ord=new Order();
    void recupOrder(Order o){
        this.ord=o;
        int a=o.getQuantity();
        String s=String.valueOf(a);
        //qntProduit.setText(s);
    }


    @FXML
    private void ModifierOrder(ActionEvent event) {
        ord.setQuantity(Integer.parseInt(qntProduit.getText()));
        ord.setId(Integer.parseInt(idOrder.getText()));
        ord.setPrix(Integer.parseInt(prix.getText()));
        ord.setTotal(ord.getPrix()*ord.getQuantity());
        Stage stage = (Stage) idOrder.getScene().getWindow();
        stage.close();
    }

    private void retourModifierOrder(ActionEvent event) {
        Stage stage = (Stage) idOrder.getScene().getWindow();
        stage.close();
    }
    
}
