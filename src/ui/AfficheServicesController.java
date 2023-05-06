/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ui;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import entite.Service;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import service.ServiceService;
import javafx.util.Callback;
import javafx.scene.Parent;
import javafx.scene.layout.HBox;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;
import java.sql.Statement;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import util.DataSource;




/**
 * FXML Controller class
 *
 * @author mfmma
 */
public class AfficheServicesController implements Initializable {

      @FXML
    private VBox VBoxShowServices;
  
    @FXML
    private TableColumn<Service, String> actionsCol;
    @FXML
    private TextField filterField;
    
    
     @FXML
    private TableView<Service> tvService;

    @FXML
    private TableColumn<Service, String> colTitre;

    @FXML
    private TableColumn<Service, String> colDes;

    @FXML
    private TableColumn<Service, String> colType;

    @FXML
    private TableColumn<Service, LocalDateTime> colDate;

    @FXML
    private TableColumn<Service, String> colImage;

    ObservableList<Service> dataList;
    
    service.ServiceService serviceService = new ServiceService();
    private static ObservableList<Service> observableListService;
    int index = -1 ;
  
    Service service = null;
    @FXML
    private ComboBox<String> txtTri;
    
        private Connection conn;
    @FXML
    private Button btnExcel;
    @FXML
    private Button btnPDF;
    @FXML
    private Button btnPie;
        public AfficheServicesController() {
        conn=DataSource.getInstance().getCnx();
    }

       
@Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<String> list = FXCollections.observableArrayList("Tri par date", "Tri par type", "Tri par Titre");
        txtTri.setItems(list);
        updateTable();
        search_service();
        txtTri.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String selectedValue = txtTri.getValue();
                if (selectedValue.equals("Tri par date")) {
                    String tritype = "date_s";
                    tvService.setItems(Tri(tritype));
                    tvService.refresh();
                } else if (selectedValue.equals("Tri par type")) {
                    String tritype = "type_s";
                    tvService.setItems(Tri(tritype));
                    tvService.refresh();
                } else if (selectedValue.equals("Tri par Titre")) {
                    String tritype = "titre_s";
                    tvService.setItems(Tri(tritype));
                    tvService.refresh();
                }
            }
            
        });

    }    
    
    public void updateTable(){
        observableListService = FXCollections.observableArrayList(serviceService.readAll());
        colTitre.setCellValueFactory(new PropertyValueFactory<Service, String>("titreS"));
        colDes.setCellValueFactory(new PropertyValueFactory<Service, String>("descrptionS"));
        colType.setCellValueFactory(new PropertyValueFactory<Service, String>("typeS"));
        colDate.setCellValueFactory(new PropertyValueFactory<Service, LocalDateTime>("dateS"));
        colImage.setCellValueFactory(new PropertyValueFactory<Service, String>("image"));

        tvService.setItems(observableListService);
        System.out.println(observableListService);
        
         Callback<TableColumn<Service, String>, TableCell<Service, String>> cellFoctory = (TableColumn<Service, String> param) -> {
            // make cell containing buttons
            final TableCell<Service, String> cell = new TableCell<Service, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    //that cell created only on non-empty rows
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {

                        FontAwesomeIconView deleteIcon = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
                        FontAwesomeIconView editIcon = new FontAwesomeIconView(FontAwesomeIcon.EDIT);

                        deleteIcon.setStyle(
                                " -fx-cursor: hand ;"
                                + "-glyph-size:28px;"
                                + "-fx-fill:#ff1744;"
                        );
                        editIcon.setStyle(
                                " -fx-cursor: hand ;"
                                + "-glyph-size:28px;"
                                + "-fx-fill:#00E676;"
                        );
                        deleteIcon.setOnMouseClicked((MouseEvent event) -> {
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("Confirmation Dialog");
                            alert.setContentText("Are you sure to delete this claim ?");
                            alert.setHeaderText(null);
                            Optional <ButtonType> action = alert.showAndWait();
                            if(action.get() == ButtonType.OK ){
                                try {
                                    service = tvService.getSelectionModel().getSelectedItem();
                                    ServiceService rs=new ServiceService();
                                    rs.delete(service.getId());
                                    tvService.setItems(getServiceData());
                                    tvService.refresh();
                                } catch (SQLException ex) {
                                    Logger.getLogger(AfficheServicesController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        });
                        editIcon.setOnMouseClicked((MouseEvent event) -> {
                        try {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("ModifierService.fxml"));
                            Parent root = loader.load();
                            Stage newStage = new Stage();
                            // Set the title of the new stage
                            newStage.setTitle("ModifierService");
                            // Create the scene for the new stage
                            Scene scene = new Scene(root);
                            newStage.setScene(scene);
                            // Show the new stage
                            newStage.show();
                            ModifierServiceController dc = loader.getController();
                            service = tvService.getSelectionModel().getSelectedItem();
                            ServiceService rs=new ServiceService();
                            int id_service = service.getId(); // get the id_service from the selected Service object
                            dc.setData(id_service); // pass the id_service to the constructor of ModifierServiceController
                            dc.setSelectedCtegorie(service);
                        } catch (IOException ex) {
                            Logger.getLogger(AfficheServicesController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    });

                        //HBox managebtn = new HBox(editIcon, deleteIcon);
                        HBox managebtn = new HBox(deleteIcon,editIcon);
                        managebtn.setStyle("-fx-alignment:center");
                        HBox.setMargin(deleteIcon, new Insets(2, 2, 0, 3));
                        HBox.setMargin(editIcon, new Insets(2, 3, 0, 2));

                        setGraphic(managebtn);

                        setText(null);

                    }
                }

            };

            return cell;
        };
        actionsCol.setCellFactory(cellFoctory);
        tvService.setItems(getServiceData());
        dataList = FXCollections.observableArrayList(serviceService.readAll());
        
    }
    
    
    
   
      
      public void getSelected() {
        index = tvService.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }
        updateTable();
    }
      
     


   
    @FXML
      public void search_service() {
    FilteredList<Service> filteredData = new FilteredList<>(dataList, b -> true);
    filterField.textProperty().addListener((observable, oldValue, newValue) -> {
        filteredData.setPredicate(service -> {
            if (newValue == null || newValue.isEmpty()) {
                return true;
            }
            String lowerCaseFilter = newValue.toLowerCase();
            if (service.getTitreS().toLowerCase().contains(lowerCaseFilter)
                    || service.getDescrptionS().toLowerCase().contains(lowerCaseFilter)
                    || service.getTypeS().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            } else {
                LocalDateTime serviceDate = service.getDateS();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                String formattedDate = serviceDate.format(formatter);
                return formattedDate.contains(lowerCaseFilter);
            }
        });
        SortedList<Service> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tvService.comparatorProperty());
        tvService.setItems(sortedData);
    });
}   
      
       private ObservableList<Service> getServiceData() {
    
    ServiceService ps = new ServiceService();
           List<Service> listp = ps.readAll();
    return FXCollections.observableArrayList(listp);
}
       
       public ObservableList<Service> Tri(String tritype) {
    List<Service> list=new ArrayList<>();
    String sql="select * from service order by "+tritype+" ASC";
    try {
        Statement st=conn.createStatement();
        ResultSet re= st.executeQuery(sql);
        while(re.next()){
            java.sql.Timestamp timestamp = re.getTimestamp("date_s");
            LocalDateTime localDateTime = timestamp.toLocalDateTime();
            list.add(new Service(re.getInt("id"), re.getString("titre_s"), re.getString("description_s"),re.getString("type_s"),re.getString("image"),
                    localDateTime));
        }
    } catch (SQLException ex) {
        Logger.getLogger(ServiceService.class.getName()).log(Level.SEVERE, null, ex);
    }
    return FXCollections.observableArrayList(list);     
}

    @FXML
    private void excelfile(ActionEvent event) {
ArrayList<Service> data = new ArrayList<Service>();

    try {
        String filename = "src\\api\\DonnéeServices.xls";

        HSSFWorkbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet("Service Details");

        Row rowhead = sheet.createRow((short)0);
        rowhead.createCell(0).setCellValue("Titre");
        rowhead.createCell(1).setCellValue("Description");
        rowhead.createCell(2).setCellValue("Type");
        rowhead.createCell(3).setCellValue("Date");
        rowhead.createCell(4).setCellValue("Image");

       ObservableList<Service> servicelist = FXCollections.observableArrayList(tvService.getItems());
        int rownum = 1;
        for (Service service : servicelist) {
            Row row = sheet.createRow(rownum++);
            row.createCell(0).setCellValue(service.getTitreS());
            row.createCell(1).setCellValue(service.getDescrptionS());
            row.createCell(2).setCellValue(service.getTypeS());
            
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            String formattedDate = formatter.format(service.getDateS());
            row.createCell(3).setCellValue(formattedDate);
            
            row.createCell(4).setCellValue(service.getImage());
        }

        // prompt user to choose save location
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Excel File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel Files (*.xls)", "*.xls"));
        File file = fileChooser.showSaveDialog(btnExcel.getScene().getWindow());

        if (file != null) {
            FileOutputStream fileOut = new FileOutputStream(file);
            workbook.write(fileOut);
            fileOut.flush();
            fileOut.close();
            workbook.close();

            Alert a = new Alert(Alert.AlertType.INFORMATION, "Excel File Has Been Generated Successfully", ButtonType.OK);
            a.showAndWait();
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
}

    
    
    @FXML
    private void PDFGeneration(ActionEvent event) {
        
     Document document = new Document();
    
    try {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save PDF File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
        File file = fileChooser.showSaveDialog(null);
        if (file != null) {
            PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();

            PdfPTable pdfTable = new PdfPTable(5);
            pdfTable.setWidthPercentage(100);
            pdfTable.setSpacingBefore(10f);
            pdfTable.setSpacingAfter(10f);

            PdfPCell cell1 = new PdfPCell(new Paragraph("Titre"));
            PdfPCell cell2 = new PdfPCell(new Paragraph("Description"));
            PdfPCell cell3 = new PdfPCell(new Paragraph("Type"));
            PdfPCell cell4 = new PdfPCell(new Paragraph("Date"));
            PdfPCell cell5 = new PdfPCell(new Paragraph("Image"));

            pdfTable.addCell(cell1);
            pdfTable.addCell(cell2);
            pdfTable.addCell(cell3);
            pdfTable.addCell(cell4);
            pdfTable.addCell(cell5);
            
            ObservableList<Service> servicelist = FXCollections.observableArrayList(tvService.getItems());

            for (Service service : servicelist) {

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
                String formattedDate = formatter.format(service.getDateS());

                pdfTable.addCell(service.getTitreS());
                pdfTable.addCell(service.getDescrptionS());
                pdfTable.addCell(service.getTypeS());
                pdfTable.addCell(formattedDate);
                pdfTable.addCell(service.getImage());
            }

            document.add(pdfTable);
            document.close();

            Alert a = new Alert(Alert.AlertType.INFORMATION, "PDF File Has Been Generated Successfully", ButtonType.OK);
            a.showAndWait();
        }
    } catch (DocumentException e) {
        e.printStackTrace();
    } catch (FileNotFoundException e) {
        e.printStackTrace();
    }
}

    @FXML
    private void handleClick(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/ui/PieChart.fxml"));
        // Create the new stage
                Stage newStage = new Stage();
                // Set the title of the new stage
                newStage.setTitle("PieChart");
                // Create the scene for the new stage
                Scene scene = new Scene(root);
                newStage.setScene(scene);
                // Show the new stage
                newStage.show();
    }
}


