/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import entite.Service;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import service.ServiceService;
import util.DataSource;



public class PieChartController implements Initializable {

    @FXML
    private PieChart pieChart;
    @FXML
    private Button retourchart;

      ObservableList<Service> lc;
      private Connection conn;
       public PieChartController() {
        conn=DataSource.getInstance().getCnx();
    }
    
      
      
    



    public void sendData(ObservableList<Service> no) {
        this.lc = no;

        int maxNumber = 0;
        for (Service s : lc) {
            int number = Integer.parseInt(s.getTypeS());
            if (number > maxNumber) {
                maxNumber = number;
            }
        }

        int[] tableNote = new int[maxNumber + 1];

        for (Service s : lc) {
            int number = Integer.parseInt(s.getTypeS());
            tableNote[number]++;
        }

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        for (int i = 0; i < tableNote.length; i++) {
            if (tableNote[i] > 0) {
                String label = "Type " + i;
                PieChart.Data data = new PieChart.Data(label, tableNote[i]);
                pieChartData.add(data);
            }
        }

        pieChart.setData(pieChartData);
    }

      
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
    pieChartData.add(new PieChart.Data("Sport", Tri("Sport")));
    pieChartData.add(new PieChart.Data("Coaching", Tri("Coaching")));
    pieChartData.add(new PieChart.Data("Médecine douce", Tri("Médecine douce")));

    pieChart.setData(pieChartData); 
        
    }    

    @FXML
    private void swfreturn(ActionEvent event) {
         try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("DashboardAdmin.fxml"));
            Parent root = loader.load();
            Scene sc = new Scene(root);
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();

            stage.setScene(sc);
            stage.setTitle("Login");
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    
    public int Tri(String tritype) {
    List<Service> list = new ArrayList<>();
    String sql = "SELECT COUNT(*) AS service_count FROM service WHERE type_s = '" + tritype + "';";
    int count=0;
         
    try {
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(sql);
        if (rs.next()) {
            count = rs.getInt("service_count");
            System.err.println(count);
        }
    } catch (SQLException ex) {
        Logger.getLogger(ServiceService.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    return count;     
}

    }
    
    
    





    
