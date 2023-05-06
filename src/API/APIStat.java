
package api;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import entite.Service;


import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import service.ServiceReservation;
import service.ServiceService;

public class APIStat extends Application {

    private Map<String, Integer> serviceCounts = new HashMap<>();
    ServiceReservation serviceReservation = new ServiceReservation();
    public void start(Stage stage) throws IOException {
        // Load the FXML file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("StatService.fxml"));
        AnchorPane root = loader.load();

        // Create a CategoryAxis for the X axis
        CategoryAxis xAxis = new CategoryAxis();

        // Create a NumberAxis for the Y axis
        NumberAxis yAxis = new NumberAxis();

        // Create a BarChart and set the axes
        BarChart<String, Number> barChart = (BarChart<String, Number>) root.lookup("#barChart");
        barChart.setTitle("STAT");
        barChart.setLegendVisible(false);
        barChart.setAnimated(false);
        barChart.getXAxis().setLabel("Service");
        barChart.getYAxis().setLabel("Number of Reservations");
        barChart.getXAxis().setTickLabelRotation(90);
        barChart.setData(getChartData());

        // Create a Scene and set it on the Stage
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    private Map<String, Integer> getReservations() {
    Map<String, Integer> reservations = new HashMap<>();

    List<Service> services = new ServiceService().readAll();
    for (Service service : services) {
      //  int count = ServiceReservation.getReservationCountByService();
       // reservations.put(service.getTitreS(), count);
    }

    return reservations;
}


public int getServiceCount() {
    List<Service> services = new ServiceService().readAll();
    return services.size();
}




    private ObservableList<XYChart.Series<String, Number>> getChartData() {
    ObservableList<XYChart.Series<String, Number>> data = FXCollections.observableArrayList();

    // Call your API to get the service statistics
    // In this example, we use some dummy data
    Map<String, Integer> reservationCounts = serviceReservation.getReservationCountByService(); 
    for (Map.Entry<String, Integer> entry : reservationCounts.entrySet()) {
        String service = entry.getKey();
        int count = entry.getValue();
        serviceCounts.put(service, count);
    }

    // Create a new XYChart.Series for each service
    XYChart.Series<String, Number> series = new XYChart.Series<>();
    for (Map.Entry<String, Integer> entry : serviceCounts.entrySet()) {
        String service = entry.getKey();
        int count = entry.getValue();
        series.getData().add(new XYChart.Data<>(service, count));
    }
    data.add(series);

    return data;
}


 
}
