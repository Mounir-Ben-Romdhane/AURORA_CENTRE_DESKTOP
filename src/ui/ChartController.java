/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ui;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.AnchorPane;
import util.DataSource;

/**
 * FXML Controller class
 *
 * @author MSI
 */
public class ChartController implements Initializable {

    @FXML
    private AnchorPane main_chart;
    @FXML
    private BarChart<?, ?> barChart;
    
    private String query = null;
    private Connection connection = DataSource.getInstance().getCnx();
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;
    
    private String query1 = null;
    private PreparedStatement preparedStatement1 = null;
    private ResultSet resultSet1 = null;
    
    public void chart(){
        query = "SELECT count(*) FROM `user` WHERE etat=false";
        query1 = "SELECT count(*) FROM `user` WHERE etat=true";
        try {
            
            XYChart.Series chartData = new XYChart.Series();
            
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            preparedStatement1 = connection.prepareStatement(query1);
            resultSet1 = preparedStatement1.executeQuery();
            while(resultSet.next()){
                
                chartData.getData().add(new XYChart.Data<>("Blocked", resultSet.getInt(1)));
            }
            while(resultSet1.next()){
                chartData.getData().add(new XYChart.Data<>("Deblocked", resultSet1.getInt(1)));
            }
            barChart.getData().add(chartData);
        } catch (SQLException ex) {
            Logger.getLogger(ChartController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        chart();
    }    
    
}
