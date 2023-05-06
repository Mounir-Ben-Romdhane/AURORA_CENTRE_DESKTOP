/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ui;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import entite.Reservation;
import entite.Service;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import service.ServiceReservation;
import service.ServiceService;

/**
 * FXML Controller class
 *
 * @author mfmma
 */
public class AfficheReservationsController implements Initializable {

    @FXML
    private VBox VBoxShowReservations;
    @FXML
    private TableView<Reservation> tvReservation;
    @FXML
    private TableColumn<Reservation, Integer> colNumero;
    @FXML
    private TableColumn<Reservation, String> colUserName;
    @FXML
    private TableColumn<Reservation, String> colEmail;
    @FXML
    private TableColumn<Reservation, Integer> colNumeroTel;
    @FXML
    private TableColumn<Reservation, String> colService_ID;
    @FXML
    private TableColumn<Reservation, LocalDateTime> colDate;
    @FXML
    private TableColumn<Reservation, String> actionsCol;
    @FXML
    private TextField filterField;
    
    Reservation reservation = null;

  
    ObservableList<Reservation> dataList;
    
    service.ServiceReservation serviceReservation = new ServiceReservation();
    private static ObservableList<Reservation> observableListReservation;
    int index = -1 ;
    @FXML
    private Button btnExcel;
    @FXML
    private Button btnPDF;
    @FXML
    private ComboBox<String> txtTri;
            private Connection conn;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       /* ObservableList<String> list = FXCollections.observableArrayList("Tri par date", "Tri par email", "Tri par Numero");
        txtTri.setItems(list);*/
        updateTable();
        search_service();
       /* txtTri.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String selectedValue = txtTri.getValue();
                if (selectedValue.equals("Tri par date")) {
                    String tritype = "date_r";
                    tvReservation.setItems(Tri(tritype));
                    tvReservation.refresh();
                } else if (selectedValue.equals("Tri par type")) {
                    String tritype = "email";
                    tvReservation.setItems(Tri(tritype));
                    tvReservation.refresh();
                } else if (selectedValue.equals("Tri par Titre")) {
                    String tritype = "numero";
                    tvReservation.setItems(Tri(tritype));
                    tvReservation.refresh();
                }
            }
            
        });*/
    }    

    
    
   
  

       public void updateTable(){
        observableListReservation = FXCollections.observableArrayList(serviceReservation.readAll());
        
        colNumero.setCellValueFactory(new PropertyValueFactory<Reservation, Integer>("numero"));
        colUserName.setCellValueFactory(new PropertyValueFactory<Reservation, String>("user_name"));
        colEmail.setCellValueFactory(new PropertyValueFactory<Reservation, String>("email"));
        colDate.setCellValueFactory(new PropertyValueFactory<Reservation, LocalDateTime>("dateR"));
        colNumeroTel.setCellValueFactory(new PropertyValueFactory<Reservation, Integer>("numeroTel"));
        colService_ID.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId_service().getTitreS()));
        tvReservation.setItems(observableListReservation);
        
         System.out.println(observableListReservation);
        
         Callback<TableColumn<Reservation, String>, TableCell<Reservation, String>> cellFoctory = (TableColumn<Reservation, String> param) -> {
            // make cell containing buttons
            final TableCell<Reservation, String> cell = new TableCell<Reservation, String>() {
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
                                    reservation = tvReservation.getSelectionModel().getSelectedItem();
                                    ServiceReservation rs=new ServiceReservation();
                                    rs.delete(reservation.getId());
                                    tvReservation.setItems(getReservationData());
                                    tvReservation.refresh();
                                } catch (SQLException ex) {
                                    Logger.getLogger(AfficheReservationsController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        });
                        editIcon.setOnMouseClicked((MouseEvent event) -> {
                            try {
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("ModifierReservation.fxml"));
                                Parent root = loader.load();
                                Stage newStage = new Stage();
                                // Set the title of the new stage
                                newStage.setTitle("Modif Reservation");
                                // Create the scene for the new stage
                                Scene scene = new Scene(root);
                                newStage.setScene(scene);
                                // Show the new stage
                                newStage.show();
                                ModifierReservationController dc = loader.getController();
                                reservation = tvReservation.getSelectionModel().getSelectedItem();
                                ServiceReservation rs=new ServiceReservation();
                                dc.setSelectedRes(reservation);
                                dc.setData(reservation.getId());
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
        tvReservation.setItems(getReservationData());
        dataList = FXCollections.observableArrayList(serviceReservation.readAll());
}   
       
           @FXML
      public void search_service() { 
     
        FilteredList<Reservation> filteredData = new FilteredList<>(dataList, b -> true);
filterField.textProperty().addListener((observable, oldValue, newValue) -> {
    filteredData.setPredicate(reservation -> {
        if (newValue == null || newValue.isEmpty()) {
            return true;
        }

        String lowerCaseFilter = newValue.toLowerCase();
       if (String.valueOf(reservation.getNumero()).toLowerCase().contains(lowerCaseFilter)
        || reservation.getUser_name().toLowerCase().contains(lowerCaseFilter)
        || String.valueOf(reservation.getNumeroTel()).toLowerCase().contains(lowerCaseFilter)
        || reservation.getEmail().toLowerCase().contains(lowerCaseFilter)
                || reservation.getId_service().getTitreS().toLowerCase().contains(lowerCaseFilter)) {
            return true;
        } else {
            LocalDateTime reservationDate = reservation.getDateR();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String formattedDate = reservationDate.format(formatter);
            return formattedDate.contains(lowerCaseFilter);
        }
    });
    SortedList<Reservation> sortedData = new SortedList<>(filteredData);
    sortedData.comparatorProperty().bind(tvReservation.comparatorProperty());
    tvReservation.setItems(sortedData);
});
      }

       
       
  private ObservableList<Reservation> getReservationData() {
    
    ServiceReservation ps = new ServiceReservation();
           List<Reservation> listp = ps.readAll();
    return FXCollections.observableArrayList(listp);
}

    @FXML
    private void excelfile(ActionEvent event) {
        ArrayList<Reservation> data = new ArrayList<Reservation>();

    try {
        String filename = "src\\api\\DonnéeReservations.xls";

        HSSFWorkbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet("Resérvations Details");

        Row rowhead = sheet.createRow((short)0);
        rowhead.createCell(0).setCellValue("Numero");
        rowhead.createCell(1).setCellValue("UserName");
        rowhead.createCell(2).setCellValue("Email");
        rowhead.createCell(3).setCellValue("NuméroTel");
        rowhead.createCell(4).setCellValue("Date");
        rowhead.createCell(5).setCellValue("Service_ID");

       ObservableList<Reservation> reservationlist = FXCollections.observableArrayList(tvReservation.getItems());
        int rownum = 1;
        for (Reservation reservation : reservationlist) {
            Row row = sheet.createRow(rownum++);
            row.createCell(0).setCellValue(reservation.getNumero());
            row.createCell(1).setCellValue(reservation.getUser_name());
            row.createCell(2).setCellValue(reservation.getEmail());
            row.createCell(3).setCellValue(reservation.getNumeroTel());

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            String formattedDate = formatter.format(reservation.getDateR());
            row.createCell(4).setCellValue(formattedDate);
            row.createCell(5).setCellValue(reservation.getId_service().getTitreS());

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

            PdfPCell cell1 = new PdfPCell(new Paragraph("Numero"));
            PdfPCell cell2 = new PdfPCell(new Paragraph("UserName"));
            PdfPCell cell3 = new PdfPCell(new Paragraph("Email"));
            PdfPCell cell4 = new PdfPCell(new Paragraph("Date"));
            PdfPCell cell5 = new PdfPCell(new Paragraph("NumeroTel"));
            PdfPCell cell6 = new PdfPCell(new Paragraph("Service_ID"));

            pdfTable.addCell(cell1);
            pdfTable.addCell(cell2);
            pdfTable.addCell(cell3);
            pdfTable.addCell(cell4);
            pdfTable.addCell(cell5);
            pdfTable.addCell(cell6);

            
            ObservableList<Reservation> reservationlist = FXCollections.observableArrayList(tvReservation.getItems());

            for (Reservation reservation : reservationlist) {

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
                String formattedDate = formatter.format(reservation.getDateR());

                pdfTable.addCell(new PdfPCell(new Phrase(reservation.getNumero())));
                pdfTable.addCell(reservation.getUser_name());
                pdfTable.addCell(reservation.getEmail());
                pdfTable.addCell(formattedDate);
                pdfTable.addCell(new PdfPCell(new Phrase(reservation.getNumeroTel())));
                pdfTable.addCell(reservation.getId_service().getTitreS());
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
    /*
   public ObservableList<Reservation> Tri(String tritype) {
    List<Reservation> list = new ArrayList<>();
    String sql = "SELECT * FROM reservation ORDER BY " + tritype + " ASC";
    try (PreparedStatement statement = conn.prepareStatement(sql);
         ResultSet resultSet = statement.executeQuery()) {
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            int serviceId = resultSet.getInt("service_id");
            ServiceService serviceService = new ServiceService();
           // Service service = serviceService.getServiceByName(serviceId);
            
            int numero = resultSet.getInt("numero");
            int numeroTel = resultSet.getInt("numero_tel");
            String email = resultSet.getString("email");
            String userName = resultSet.getString("user_name");
            LocalDateTime dateR = resultSet.getTimestamp("date_r").toLocalDateTime();
            String emailConnect = resultSet.getString("email_connect");
            list.add(new Reservation(id, numero, numeroTel, email, userName, dateR, service, emailConnect));
        }
    } catch (SQLException ex) {
        Logger.getLogger(ServiceService.class.getName()).log(Level.SEVERE, null, ex);
    }
    return FXCollections.observableArrayList(list);
}*/
}

   
       
 

    

