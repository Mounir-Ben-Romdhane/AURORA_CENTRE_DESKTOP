
/*
package gui;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.AnchorPane;
import api.API;
import entities.Service;

public class statServiceController implements Initializable {

    @FXML
    private AnchorPane main_form;

    @FXML
    private BarChart<?, ?> barChart;

    private API api;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        api = new API();
        Map<String, Integer> reservationCounts = api.getReservationCountByService();

        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();

        XYChart.Series series = new XYChart.Series();
        series.setName("Reservations");

        ObservableList<XYChart.Data> data = FXCollections.observableArrayList();
        for (Map.Entry<String, Integer> entry : reservationCounts.entrySet()) {
            String serviceName = entry.getKey();
            Integer count = entry.getValue();
            Service service = api.getServiceByName(serviceName);
            data.add(new XYChart.Data(service.getTitreS(), count));
        }

        series.setData(data);
        barChart.getData().add(series);
        barChart.setLegendVisible(false);

        xAxis.setLabel("Service");
        yAxis.setLabel("Number of Reservations");

        barChart.setTitle("Reservation Statistics by Service");
        barChart.setAnimated(false);
        barChart.setCategoryGap(25);
        barChart.setBarGap(0);

        barChart.setVerticalGridLinesVisible(false);
        barChart.setHorizontalGridLinesVisible(false);
        barChart.setVerticalZeroLineVisible(false);
        barChart.setHorizontalZeroLineVisible(false);

        barChart.getXAxis().setTickLabelsVisible(true);
        barChart.getYAxis().setTickLabelsVisible(true);
    }
}
*/