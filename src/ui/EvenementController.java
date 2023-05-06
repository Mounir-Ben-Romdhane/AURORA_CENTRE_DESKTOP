/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import animatefx.animation.Pulse;
import entite.evenement;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import service.EvenementService;

/**
 * FXML Controller class
 *
 * @author asus
 */
public class EvenementController implements Initializable {
    
    EvenementService Es= new EvenementService();
    MusicController music=new MusicController();

    private Button btnLogOutClient;
    @FXML
    private VBox vBoxDashboardClient;
    @FXML
    private TableView<evenement> evenementTv;
    private TableColumn<evenement, Integer> idT;
    @FXML
    private TableColumn<evenement, String> titreevT;
    @FXML
    private TableColumn<evenement, String> descriptionevT;
    @FXML
    private TableColumn<evenement, String> imageevT;
    @FXML
    private TableColumn<evenement, Date> dateevT;
    @FXML
    private TableColumn<evenement, String> typeevT;
    private TableColumn<evenement, String> colorT;
    @FXML
    private TextField titreevF;
    @FXML
    private TextField descriptionevF;
    @FXML
    private TextField imageevF;
    
    //@FXML
   // private ChoiceBox<?> typeevF;
    @FXML
    private ChoiceBox<String> typeevF;
 //private Label myLabel;
	private String[] type = {"Musique","Santé","Sport","Méditation"};
    @FXML
    private DatePicker dateevF;
    @FXML
    private TextField idmodifierField;
    @FXML
    private ImageView imageview1;
    private Button affichageTout;
    private Button BtnStat;
    private Button BtnP;
    private TextField txtsearch;
    
    ObservableList<evenement> datalist;
    @FXML
    private Button statistique;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        //
        typeevF.getItems().addAll(type);
         //typeevF.setOnAction(this::getType);
         
        getEvents();
        System.out.println("search");
        
        //    search();
        
        
    }  
//    public void getType(ActionEvent event) {
//		
//		String myFood = typeevF.getValue();
//		//myLabel.setText(myFood);
//	}
    
    
    
    public void getEvents() {  
    
         try {
            // TODO
            List<evenement> evenements = Es.recupererEvenement();
            ObservableList<evenement> olp = FXCollections.observableArrayList(evenements);
            evenementTv.setItems(olp);
            //idT.setCellValueFactory(new PropertyValueFactory("id"));
            titreevT.setCellValueFactory(new PropertyValueFactory("titreev"));
            descriptionevT.setCellValueFactory(new PropertyValueFactory("descriptionev"));
            imageevT.setCellValueFactory(new PropertyValueFactory("imageev"));
            dateevT.setCellValueFactory(new PropertyValueFactory("dateev"));
            typeevT.setCellValueFactory(new PropertyValueFactory("typeev"));
           // colorT.setCellValueFactory(new PropertyValueFactory("color"));
           // this.delete();
            
         
        } catch (SQLException ex) {
            System.out.println("error" + ex.getMessage());
        }
    }//get events
    
    
    @FXML
    private void choisirEvent(MouseEvent event) {
        evenement e = evenementTv.getItems().get(evenementTv.getSelectionModel().getSelectedIndex());   
        //idLabel.setText(String.valueOf(e.getId_event()));
        idmodifierField.setText(String.valueOf(e.getId()));
        titreevF.setText(e.getTitreev());
        descriptionevF.setText(e.getDescriptionev());
        imageevF.setText(e.getImageev());
        //typeevF.set(e.getTypeev());
        //dateevF.setValue((e.getDateev()));    
        //lel image
        String path = e.getImageev();
               File file=new File(path);
              Image img = new Image(file.toURI().toString());
                imageview1.setImage(img);
        //////////////      
           // String path1="C:\\xampp2\\htdocs\\PI\\Evenement\\imgQr\\qrcode"+filename;
            // File file1=new File(path1);
              //Image img1 = new Image(file1.toURI().toString());
              //Image image = new Image(getClass().getResourceAsStream("src/utils/img/" + filename));
            //QrCode.setImage(img1); 
    }

    
    
    
    
    
    
    
    
    


    private void handleLogOutClient(ActionEvent event) throws IOException {
        Stage currentStage = (Stage) btnLogOutClient.getScene().getWindow();
                            currentStage.close();
                
                            Stage stage = new Stage();
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("Main.fxml"));

                            Parent root = loader.load();
                           // tfEmailSignIn.getScene().setRoot(root);

                            Scene scene = new Scene(root);
                            scene.setFill(Color.TRANSPARENT);
                            stage.setTitle("Dashboard Admin");
                            stage.initStyle(StageStyle.TRANSPARENT);
                            stage.setScene(scene);
                            stage.show();
                            
                           new Pulse(root).play();
    }

    
    
    
    
    
    
    private boolean NoDate() {
         LocalDate currentDate = LocalDate.now();     
         LocalDate myDate = dateevF.getValue(); 
         int comparisonResult = myDate.compareTo(currentDate);      
         boolean test = true;
        if (comparisonResult < 0) {
        // myDate est antérieure à currentDate
        test = true;
        } else if (comparisonResult > 0) {
         // myDate est postérieure à currentDate
         test = false;
        }
        return test;
    }
    
    @FXML
    private void ajouterev(ActionEvent event) {
        int part=0;
        if ((titreevF.getText().length() == 0) || (typeevF.getValue().toString().length() == 0) || (imageevF.getText().length() == 0) || (descriptionevF.getText().length() == 0)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error ");
            alert.setHeaderText("Error!");
            alert.setContentText("Fields cannot be empty");
            alert.showAndWait();
        }
       else if (NoDate() == true) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error ");
            alert.setHeaderText("Error!");
            alert.setContentText("la date date doit être aprés la date d'aujourd'hui");
            alert.showAndWait();
        }
       
            else
            {
        evenement e = new evenement();
        e.setTitreev(titreevF.getText());
        e.setDescriptionev(descriptionevF.getText());
        e.setImageev(imageevF.getText());
        e.setTypeev(typeevF.getValue().toString());
        //e.setColor("color auto");
        
        java.util.Date date_debut=java.util.Date.from(dateevF.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date sqlDate = new Date(date_debut.getTime());
        
        
        e.setDateev(sqlDate);
        
        
        //lel image
        
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information ");
            alert.setHeaderText("Event add");
            alert.setContentText("Event added successfully!");
            alert.showAndWait();      
            try {
                Es.ajouterEvenement(e);
                reset();
                getEvents();
            } catch (SQLException ex) {
                Logger.getLogger(EvenementController.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    }

    @FXML
    private void modifierev(ActionEvent event) throws SQLException {
        evenement e = new evenement();
        e.setId(Integer.valueOf(idmodifierField.getText()));
        e.setTitreev(titreevF.getText());
        e.setDescriptionev(descriptionevF.getText());
        e.setImageev(imageevF.getText());
        e.setTypeev(typeevF.getValue().toString());
        
        Date d=Date.valueOf(dateevF.getValue());
        e.setDateev(d);       
        Es.modifierEvenement(e);
        reset();
        getEvents(); 
    }

    @FXML
    private void supprimerev(ActionEvent event) {
        evenement e = evenementTv.getItems().get(evenementTv.getSelectionModel().getSelectedIndex());
        try {
            Es.supprimerEvenement(e);
        } catch (SQLException ex) {
            Logger.getLogger(EvenementController.class.getName()).log(Level.SEVERE, null, ex);
        }   
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information ");
        alert.setHeaderText("Event delete");
        alert.setContentText("Event deleted successfully!");
        alert.showAndWait();        
        getEvents();    
    }

    @FXML
    private void uploadImage(ActionEvent event) throws FileNotFoundException, IOException {
        Random rand = new Random();
        int x = rand.nextInt(1000);
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Upload File Path");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        File file = fileChooser.showOpenDialog(null);
        String DBPath = "C:\\Users\\MSI\\OneDrive\\Documents\\GitHub\\PIDEV_DESKTOP\\src\\image\\"  + x + ".jpg";
        if (file != null) {
            FileInputStream Fsource = new FileInputStream(file.getAbsolutePath());
            FileOutputStream Fdestination = new FileOutputStream(DBPath);
            BufferedInputStream bin = new BufferedInputStream(Fsource);
            BufferedOutputStream bou = new BufferedOutputStream(Fdestination);
            System.out.println(file.getAbsoluteFile());
            String path=file.getAbsolutePath();
            Image img = new Image(file.toURI().toString());
            imageview1.setImage(img);    
            imageevF.setText(DBPath);
            int b = 0;
            while (b != -1) {
                b = bin.read();
                bou.write(b);
            }
            bin.close();
            bou.close();          
        } else {
            System.out.println("error");
        }
    }

    
    private void reset() {
        titreevF.setText("");
        descriptionevF.setText("");
        typeevF.setValue("");
        imageevF.setText("");
        dateevF.setValue(null);  
    }
    
    
    
    
    private void afficherpart(ActionEvent event) {
       try {
           System.out.println("aaaaaaaa");
            Parent reclamationParent = FXMLLoader.load(getClass().getResource("afficherParticipation.fxml"));

            Scene reclamationScene = new Scene(reclamationParent);
            Stage window = (Stage) (BtnP.getScene().getWindow());
            window.setScene(reclamationScene);
            window.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void affichageTout(ActionEvent event) {
        try {
            Parent reclamationParent = FXMLLoader.load(getClass().getResource("afficherEvenement.fxml"));

            Scene reclamationScene = new Scene(reclamationParent);
            Stage window = (Stage) (affichageTout.getScene().getWindow());
            window.setScene(reclamationScene);
            window.show();
        } catch (IOException e) {
        }
    }

    private void StopButtonaa(ActionEvent event) {
        music.stopMusic();
    }

    private void StatAction(ActionEvent event) {
         try {
            Parent reclamationParent = FXMLLoader.load(getClass().getResource("Barchart.fxml"));

            Scene reclamationScene = new Scene(reclamationParent);
            Stage window = (Stage) (BtnStat.getScene().getWindow());
            window.setScene(reclamationScene);
            window.show();
        } catch (IOException e) {
        }
    }
    
    
    
    
    public void search(){
        
          FilteredList<evenement> filtreddata=new FilteredList<>(datalist,b->true);
          txtsearch.textProperty().addListener(((observable,oldvalue,newvalue) -> {
              filtreddata.setPredicate(evenement->{
                  if(newvalue==null || newvalue.isEmpty()){
                      return true;
                  }
                  String lowerCaseFilter=newvalue.toLowerCase();
                  if(evenement.getTitreev().toLowerCase().indexOf(lowerCaseFilter) !=-1 
                          || evenement.getDescriptionev().toLowerCase().indexOf(lowerCaseFilter)!=-1
                          || evenement.getTypeev().toLowerCase().indexOf(lowerCaseFilter)!=-1
                          ){
                      return true;
                  }else
                      return false;
              });
          }));
          SortedList<evenement> sorteddata=new SortedList<>(filtreddata);
          sorteddata.comparatorProperty().bind(evenementTv.comparatorProperty());
          evenementTv.setItems(sorteddata);
}

    @FXML
    private void statistique(ActionEvent event) {
             // Create a map to store the frequency of each type
    Map<String, Integer> typeFrequency = new HashMap<>();

    // Loop through the items in the TableView
    for (evenement evenement : evenementTv.getItems()) {
        String type = evenement.getTypeev();
        if (typeFrequency.containsKey(type)) {
            typeFrequency.put(type, typeFrequency.get(type) + 1);
        } else {
            typeFrequency.put(type, 1);
        }
    }

    // Create a PieChart data set
    ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
    for (String type : typeFrequency.keySet()) {
        int frequency = typeFrequency.get(type);
        double percentage = (double) frequency / evenementTv.getItems().size() * 100;
        String percentageText = String.format("%.2f%%", percentage);
        PieChart.Data slice = new PieChart.Data(type + " " + percentageText, frequency);
        pieChartData.add(slice);
    }

    // Create a PieChart with the data set
    PieChart chart = new PieChart(pieChartData);

    // Show percentage values in the chart's tooltip
    for (final PieChart.Data data : chart.getData()) {
        Tooltip tooltip = new Tooltip();
        tooltip.setText(String.format("%.2f%%", (data.getPieValue() / evenementTv.getItems().size() * 100)));
        Tooltip.install(data.getNode(), tooltip);
    }

    // Show the chart in a new window
    Scene scene = new Scene(chart);
    Stage stage = new Stage();
    stage.setScene(scene);
    stage.show();
    }
    
    
    
    
    
    
    
    
}
