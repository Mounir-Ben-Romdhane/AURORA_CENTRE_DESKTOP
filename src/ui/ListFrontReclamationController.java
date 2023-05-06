/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ui;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.DeviceNColor;
import com.itextpdf.text.pdf.PdfDocument;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import entite.Reclamation;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.Cell;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import service.ReclamationService;
import service.UserService;
import util.DataSource;

/**
 * FXML Controller class
 *
 * @author malek
 */
public class ListFrontReclamationController implements Initializable {

    @FXML
    private TableView<Reclamation> tableViewReclamation;
    @FXML
    private TableColumn<Reclamation, String> txtType;
    @FXML
    private TableColumn<Reclamation, String> txtDes;
    @FXML
    private TableColumn<Reclamation, String> txtEmail;
    @FXML
    private TableColumn<Reclamation, String> txtDate;
    @FXML
    private TableColumn<Reclamation, String> txtEtat;
    @FXML
    private TableColumn<Reclamation, String> txtAction;
    Reclamation reclamation=null;
    @FXML
    private TextField txtsearch;
    ObservableList<Reclamation> datalist;
    ReclamationService rs=new ReclamationService();
    @FXML
    private ComboBox<String> txtTri;
    private Connection conn;
    public ListFrontReclamationController() {
        conn=DataSource.getInstance().getCnx();
    }
    private UserService ps = new UserService();
    private String email=ps.getCurrentUser().getEmail();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        try {
            // TODO
            ObservableList<String> list=FXCollections.observableArrayList("Tri par date","Tri par description","Tri par email");
      txtTri.setItems(list);
            loadData();
            search();
            txtTri.setOnAction(new EventHandler<ActionEvent>(){
                @Override
                public void handle(ActionEvent event) {
                  String selectedValue = txtTri.getValue();
                  if(selectedValue.equals("Tri par date")){
                      String tritype="date_reclamation";
                      tableViewReclamation.setItems(Tri(tritype));
                      tableViewReclamation.refresh();
                  }
                  else if(selectedValue.equals("Tri par description")){
                     String tritype="description";
                      tableViewReclamation.setItems(Tri(tritype));
                      tableViewReclamation.refresh(); 
                  }else if(selectedValue.equals("Tri par email")){
                      String tritype="email_reclamation";
                      tableViewReclamation.setItems(Tri(tritype));
                      tableViewReclamation.refresh(); 
                  }
                }
                
            });
            
            
        } catch (SQLException ex) {
            Logger.getLogger(AfficheUsersController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }   
     private void loadData() throws SQLException { 
        //add cell of button edit 
        
        txtType.setCellValueFactory(new PropertyValueFactory<>("type"));
    txtDes.setCellValueFactory(new PropertyValueFactory<>("description"));
    txtEmail.setCellValueFactory(new PropertyValueFactory<>("email_reclamation"));
    txtDate.setCellValueFactory(new PropertyValueFactory<>("date_reclamation"));
  Callback<TableColumn.CellDataFeatures<Reclamation, String>, ObservableValue<String>> statusCellValueFactory =
    new Callback<TableColumn.CellDataFeatures<Reclamation, String>, ObservableValue<String>>() {
        @Override
        public ObservableValue<String> call(TableColumn.CellDataFeatures<Reclamation, String> param) {
            int status = param.getValue().getStatus();
            switch (status) {
               case 0:
                   return new SimpleStringProperty("En cours");
               case 1:
                   return new SimpleStringProperty("Accepter");
               case 2:
                   return new SimpleStringProperty("Refuser");
               default:
                   return new SimpleStringProperty("Unknown");
            }
        }
    };
txtEtat.setCellValueFactory(statusCellValueFactory);
         Callback<TableColumn<Reclamation, String>, TableCell<Reclamation, String>> cellFoctory = (TableColumn<Reclamation, String> param) -> {
            // make cell containing buttons
            final TableCell<Reclamation, String> cell = new TableCell<Reclamation, String>() {
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
                        FontAwesomeIconView pdficon=new FontAwesomeIconView(FontAwesomeIcon.PRINT);

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
                        pdficon.setStyle(
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
                                reclamation = tableViewReclamation.getSelectionModel().getSelectedItem();
                                ReclamationService rs=new ReclamationService();
                                rs.delete(reclamation.getId());
                                tableViewReclamation.setItems(getReclamationData(email)); 
                          tableViewReclamation.refresh();
                            }
                        });
                        editIcon.setOnMouseClicked((MouseEvent event) -> {
                            try {
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("UpdateReclamation.fxml"));
                                Parent root = loader.load();
                                Stage newStage = new Stage();
                                // Set the title of the new stage
                                newStage.setTitle("Modif Reservation");
                                // Create the scene for the new stage
                                Scene scene = new Scene(root);
                                newStage.setScene(scene);
                                // Show the new stage
                                newStage.show();
                                UpdateReclamationController dc = loader.getController();
                                reclamation = tableViewReclamation.getSelectionModel().getSelectedItem();
                                ReclamationService rs=new ReclamationService();
                                dc.setReclamation(reclamation);
                                dc.setData(reclamation.getId(),reclamation.getStatus());
                            } catch (IOException ex) {
                                Logger.getLogger(ListFrontReclamationController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        });
                        pdficon.setOnMouseClicked((MouseEvent event)->{
                            try {
                               reclamation=tableViewReclamation.getSelectionModel().getSelectedItem();
                                Document document=new Document();
                                    PdfWriter.getInstance(document,new FileOutputStream("d:/hello.pdf"));
                                    document.open();
                                    
                                    float[] columnWidths = {50f, 50f};
                                    PdfPTable tableT = new PdfPTable(columnWidths);
                                    tableT.setWidthPercentage(100);

                                     PdfPCell reclamationCell = new PdfPCell(new Phrase("Reclamation"));
                                       reclamationCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                                      tableT.addCell(reclamationCell);

                                      PdfPCell auroraCell = new PdfPCell(new Phrase("Aurora\n Pays : Tunisia\n PostalZip: 1091"));
                                        
                                      auroraCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                                      tableT.addCell(auroraCell);

                                      document.add(tableT);   
                                    float col=28f;
                                    float columnwidth[]={col,col,col,col,col};
                                    
                                 PdfPTable table = new PdfPTable(columnwidth);
                                 
                                
        PdfPCell typeHeader = new PdfPCell(new Phrase("Type"));
        typeHeader.setBackgroundColor(BaseColor.LIGHT_GRAY);
        table.addCell(typeHeader);

        PdfPCell descriptionHeader = new PdfPCell(new Phrase("Description"));
        descriptionHeader.setBackgroundColor(BaseColor.LIGHT_GRAY);
        table.addCell(descriptionHeader);

        PdfPCell emailHeader = new PdfPCell(new Phrase("Email"));
        emailHeader.setBackgroundColor(BaseColor.LIGHT_GRAY);
        table.addCell(emailHeader);

        PdfPCell dateHeader = new PdfPCell(new Phrase("Date"));
        dateHeader.setBackgroundColor(BaseColor.LIGHT_GRAY);
        table.addCell(dateHeader);

        PdfPCell statusHeader = new PdfPCell(new Phrase("Status"));
        statusHeader.setBackgroundColor(BaseColor.LIGHT_GRAY);
        table.addCell(statusHeader);

        PdfPCell typeCell = new PdfPCell(new Phrase(reclamation.getType()));
        table.addCell(typeCell);

        PdfPCell descriptionCell = new PdfPCell(new Phrase(reclamation.getDescription()));
        table.addCell(descriptionCell);

        PdfPCell emailCell = new PdfPCell(new Phrase(reclamation.getEmail_reclamation()));
        table.addCell(emailCell);

        PdfPCell dateCell = new PdfPCell(new Phrase(reclamation.getDate_reclamation().toString()));
        table.addCell(dateCell);

        PdfPCell statusCell = new PdfPCell(new Phrase(String.valueOf(reclamation.getStatus())));
        table.addCell(statusCell);

        table.completeRow();
                                  
                               document.add(new Paragraph(" "));
                               document.add(new Paragraph(" "));
                                 document.add(table);
                                document.close();
                            } catch (Exception ex) {
                                System.out.println(ex);
                            }   
                            });

                        //HBox managebtn = new HBox(editIcon, deleteIcon);
                        HBox managebtn = new HBox(deleteIcon,editIcon,pdficon);
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
         txtAction.setCellFactory(cellFoctory);       
         tableViewReclamation.setItems(getReclamationData(email));
         datalist=FXCollections.observableArrayList(getReclamationData(email));
         
         System.out.println("--------------------------------------------------");
         System.out.println(datalist);
         System.out.println("--------------------------------------------------");
         
        
    }
      private ObservableList<Reclamation> getReclamationData(String email) {
    
    ReclamationService ps = new ReclamationService();
    List<Reclamation> listp = ps.readAll();
    List<Reclamation> filteredList = new ArrayList<>();
    for (Reclamation rec : listp) {
        if (rec.getEmail_connecte().equals(email)) {
            filteredList.add(rec);
        }
    }

    return FXCollections.observableArrayList(filteredList);
}
    public void search(){
        
        
          FilteredList<Reclamation> filtreddata=new FilteredList<>(datalist,b->true);
          txtsearch.textProperty().addListener(((observable,oldvalue,newvalue) -> {
              filtreddata.setPredicate(reclamation->{
                  if(newvalue==null || newvalue.isEmpty()){
                      return true;
                  }
                  String lowerCaseFilter=newvalue.toLowerCase();
                  if(reclamation.getType().toLowerCase().indexOf(lowerCaseFilter) !=-1 
                          || reclamation.getDescription().toLowerCase().indexOf(lowerCaseFilter)!=-1
                          || reclamation.getEmail_reclamation().toLowerCase().indexOf(lowerCaseFilter)!=-1
                          ){
                      return true;
                  }else
                      return false;
              });
          }));
          SortedList<Reclamation> sorteddata=new SortedList<>(filtreddata);
          sorteddata.comparatorProperty().bind(tableViewReclamation.comparatorProperty());
          tableViewReclamation.setItems(sorteddata);
}
    public ObservableList<Reclamation> Tri(String tritype) {
       List<Reclamation> list=new ArrayList<>();
       String sql="select * from reclamation where email_connecte = '"+email+"' order by "+tritype+" ASC";
        try {
            Statement st=conn.createStatement();
            ResultSet re= st.executeQuery(sql);
            while(re.next()){
               java.sql.Date date = re.getDate("date_reclamation");
               LocalDateTime localDateTime = date.toLocalDate().atStartOfDay();
              list.add(new Reclamation(re.getString("type"), re.getString("nom"),re.getString("description"),re.getInt("status"),
                      localDateTime,re.getString("email_connecte"),re.getString("email_reclamation")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ReclamationService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return FXCollections.observableArrayList(list);     
    }
    
}
 
 