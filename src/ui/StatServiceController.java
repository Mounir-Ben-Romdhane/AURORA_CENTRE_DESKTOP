/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author mfmma
 */
public class StatServiceController implements Initializable {

    @FXML
    private AnchorPane main_form;
    @FXML
    private BarChart<String, Integer> barChart;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Service");
        yAxis.setLabel("Number of Reservations");

        // Set up the chart
        barChart.setTitle("Reservation Statistics");
        barChart.setLegendVisible(false);
        barChart.setHorizontalGridLinesVisible(false);
        barChart.setVerticalGridLinesVisible(false);
        barChart.setAnimated(false);
        barChart.getXAxis().setTickLabelsVisible(true);
        barChart.getYAxis().setTickLabelsVisible(true);
        barChart.getXAxis().setTickMarkVisible(true);
        barChart.getYAxis().setTickMarkVisible(true);
        barChart.getXAxis().setTickLabelRotation(90);
        barChart.getData().clear();

        // Add some data to the chart
        XYChart.Series<String, Integer> data = new XYChart.Series<>();
        data.getData().add(new XYChart.Data<>("Service 1", 10));
        data.getData().add(new XYChart.Data<>("Service 2", 20));
        data.getData().add(new XYChart.Data<>("Service 3", 15));
        data.getData().add(new XYChart.Data<>("Service 4", 5));
        barChart.getData().add(data);
    }
}