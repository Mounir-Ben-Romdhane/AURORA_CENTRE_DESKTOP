/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import entite.evenement;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import service.EvenementService;

/**
 * FXML Controller class
 *
 * @author nourh
 */

public class BarchartController implements Initializable {

    @FXML
    private PieChart pieChart;

    ObservableList<evenement> lc;
    @FXML
    private Button retourchart;
  
    /**
     * Initializes the controller class.
     */
            
    public void sendData(ObservableList<evenement> no){
        this.lc=no;
        int[]  tableNote =new int[3];
        for (int i=0;i<lc.size();i++){
            if (lc.get(i).getTypeev()=="Santé")
                tableNote[0]++;
            else if(lc.get(i).getTypeev()=="Musique")
                tableNote[1]++;
            else
                tableNote[2]++;
            System.out.println("Prix "+tableNote[0]);
            System.out.println("Prix "+tableNote[1]);
            System.out.println("Prix "+tableNote[2]);
            ObservableList<PieChart.Data> pieChartData= FXCollections.observableArrayList(new PieChart.Data("Prix entre 10 et 15",tableNote[0]),new PieChart.Data("Prix inferieur a10",tableNote[1]),new PieChart.Data("Prix 15 ou plus",tableNote[2]));
            pieChart.setData(pieChartData);
        }
    }
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void swfreturn(ActionEvent event) {
          try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Evenement.fxml"));
            Parent root = loader.load();
            Scene sc = new Scene(root);
            Stage primaryStage = (Stage) retourchart.getScene().getWindow();
            ;
            primaryStage.setScene(sc);
            primaryStage.setTitle("Login");
            primaryStage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }


    
}