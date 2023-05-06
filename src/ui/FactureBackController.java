/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import entite.Facture;
import java.awt.Desktop;
import java.awt.HeadlessException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import org.controlsfx.control.Notifications;
import service.FactureService;
import service.OrderService;
import util.DataSource;

/**
 * FXML Controller class
 *
 * @author Samar
 */
public class FactureBackController implements Initializable {

    
    FactureService Es= new FactureService();
    @FXML
    private TableView<Facture> tabfac;
    private TableColumn<Facture, Integer> idf;
    @FXML
    private TableColumn<Facture, Integer> commandefac;
    @FXML
    private TableColumn<Facture, String> datefac;
    @FXML
    private TableColumn<Facture, Boolean> estsup;
    @FXML
    private Button pdff;
    @FXML
    private Button supprimerbtn;
    private TextField searchbarre;
    ObservableList<Facture> datalist;
    Facture facture=null;
    @FXML
    private Button exportfac;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        getFacture();
        
    }    
    
    public void getFacture() {  
    
         try {
            // TODO
            List<Facture> Facture = Es.readAll();
            ObservableList<Facture> olp = FXCollections.observableArrayList(Facture);
            tabfac.setItems(olp);
           // idf.setCellValueFactory(new PropertyValueFactory("id"));
            commandefac.setCellValueFactory(new PropertyValueFactory("commande_id"));
            datefac.setCellValueFactory(new PropertyValueFactory("date_facturation"));
            estsup.setCellValueFactory(new PropertyValueFactory("est_sup"));
            
            
         
        } catch (SQLException ex) {
            System.out.println("error" + ex.getMessage());
        }
    }//get events


    @FXML
    private void supprimerButton(ActionEvent event) {
        try {
            int myIndex = tabfac.getSelectionModel().getSelectedIndex();
            
            int id = Integer.parseInt(String.valueOf(tabfac.getItems().get(myIndex).getId()));
            System.out.println(id);
            System.out.println(myIndex);
            
            try {
                Es.delete(id);
            } catch (SQLException ex) {
                Logger.getLogger(FactureBackController.class.getName()).log(Level.SEVERE, null, ex);
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Annulation de la facture");
            alert.setHeaderText("Annulation de la Facture");
            alert.setContentText("Facture annulée!");
            alert.showAndWait();
            List<Facture> Facture = Es.readAll();
            ObservableList<Facture> olp = FXCollections.observableArrayList(Facture);
            tabfac.setItems(olp);
            tabfac.refresh();
            //tabfac();
            
        } catch (SQLException ex) {
            Logger.getLogger(FactureBackController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
     /*public void search() {
    FilteredList<Facture> filtreddata = new FilteredList<>(datalist, b -> true);
    searchbarre.textProperty().addListener(((observable, oldvalue, newvalue) -> {
        filtreddata.setPredicate(Facture -> {
            if (newvalue == null || newvalue.isEmpty()) {
                return true;
            }
            String lowerCaseFilter = newvalue;
            if (String.valueOf(facture.getCommande_id()).toLowerCase().indexOf(lowerCaseFilter) != -1 
                    || String.valueOf(facture.getId()).toLowerCase().indexOf(lowerCaseFilter) != -1) {
                return true;
            } else {
                return false;
            }
        });
        SortedList<Facture> sorteddata = new SortedList<>(filtreddata);
        sorteddata.comparatorProperty().bind(tabfac.comparatorProperty());
        tabfac.setItems(sorteddata);
    }));
}*/

                  

    private void rechercher() throws SQLException {
        String query = searchbarre.getText();
            ObservableList<Facture> filteredList = FXCollections.observableArrayList();
                      
            List<Facture> li =Es.readAll();
        ObservableList<Facture> data = FXCollections.observableArrayList(li);
            for (Facture categorie : li) {
                if (categorie.getDate_facturation().toLowerCase().contains(query.toLowerCase())) {
                    filteredList.add(categorie);
                }
            }
        tabfac.setItems(filteredList);
    
    }

    @FXML
    private void exportfact(ActionEvent event) {
        try {
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream("C:\\Users\\MSI\\OneDrive\\Bureau\\excel.csv"), "UTF-8"));
         

            List<Facture> factures = Es.readAll();
            writer.write("ID ,Date_facturation  , Commande_id , payment \n");
            for (Facture obj : factures) {

                int id = obj.getId();
writer.write(Integer.toString(id));

                writer.write("   :");
                writer.write(obj.getDate_facturation());
                writer.write("    :");
                writer.write(obj.getCommande_id());
                writer.write("  :");
               
              writer.write(Boolean.toString(obj.isEst_sup()));
              writer.write("   :");
               
               
            
                writer.write("\n");

            }
            writer.flush();
            writer.close();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("EXCEL ");

            alert.setHeaderText("EXCEL");
            alert.setContentText("Enregistrement effectué avec succès!");

            alert.showAndWait();
        } catch (Exception e) {
            System.out.println("Failed to send message: " + e.getMessage());
        }
         
    }

    @FXML
    private void printPdf(ActionEvent event) throws SQLException {
        
        try {
       Document doc = new Document();
       PdfWriter.getInstance(doc,new FileOutputStream("C:\\Users\\MSI\\OneDrive\\Documents\\GitHub\\PIDEV_DESKTOP\\src\\image\\PDF.pdf"));
       doc.open();
       
      Image img = Image.getInstance("C:\\Users\\MSI\\OneDrive\\Documents\\GitHub\\PIDEV_DESKTOP\\src\\image\\logo.jpeg");
      img.scaleAbsoluteHeight(200);
       img.scaleAbsoluteWidth(200);
     img.setAlignment(Image.ALIGN_CENTER);
      doc.add(img);
       
       doc.add(new Paragraph(" "));
       Font font = new Font(FontFamily.HELVETICA, 32, Font.NORMAL, BaseColor.YELLOW);
       Paragraph p = new Paragraph("Liste des factures", font);
       p.setAlignment(Element.ALIGN_CENTER);
       doc.add(p);
       doc.add(new Paragraph(" "));
       doc.add(new Paragraph(" "));
       
       PdfPTable tabpdf = new PdfPTable(3);
       tabpdf.setWidthPercentage(100);
       
       PdfPCell cell;
          
       cell = new PdfPCell(new Phrase("la commande numeéro :", FontFactory.getFont("Times New Roman", 11)));
       cell.setHorizontalAlignment(Element.ALIGN_CENTER);
       cell.setBackgroundColor(BaseColor.WHITE);
       tabpdf.addCell(cell);
       
       cell = new PdfPCell(new Phrase("date de facturation", FontFactory.getFont("Times New Roman", 11)));
       cell.setHorizontalAlignment(Element.ALIGN_CENTER);
       cell.setBackgroundColor(BaseColor.WHITE);
       tabpdf.addCell(cell);
       
       cell = new PdfPCell(new Phrase("Payment efectué", FontFactory.getFont("Times New Roman", 11)));
       cell.setHorizontalAlignment(Element.ALIGN_CENTER);
       cell.setBackgroundColor(BaseColor.WHITE);
       tabpdf.addCell(cell);
       
   
       
       Connection cnx = DataSource.getInstance().getCnx();
       String req = "SELECT commande_id ,date_facturation,est_sup from `facture`";
        Statement st = cnx.createStatement();
        ResultSet rs1 =  st.executeQuery(req);
        
        
        
        
       while(rs1.next()){
           cell = new PdfPCell(new Phrase(rs1.getString("commande_id"), FontFactory.getFont("Times New Roman", 11)));
           cell.setHorizontalAlignment(Element.ALIGN_CENTER);
           cell.setBackgroundColor(BaseColor.WHITE);
           tabpdf.addCell(cell);
           
           System.out.println(rs1.getString("date_facturation"));
           cell = new PdfPCell(new Phrase(rs1.getString("date_facturation"), FontFactory.getFont("Times New Roman", 11)));
           cell.setHorizontalAlignment(Element.ALIGN_CENTER);
           cell.setBackgroundColor(BaseColor.WHITE);
           tabpdf.addCell(cell);
           
           cell = new PdfPCell(new Phrase(rs1.getString("est_sup"), FontFactory.getFont("Times New Roman", 11)));
           cell.setHorizontalAlignment(Element.ALIGN_CENTER);
           cell.setBackgroundColor(BaseColor.WHITE);
           tabpdf.addCell(cell);
           
           
           
       }
       
       doc.add(tabpdf);
       JOptionPane.showMessageDialog(null, "Success !!");
       doc.close();
       Desktop.getDesktop().open(new File("C:\\Users\\MSI\\OneDrive\\Documents\\GitHub\\PIDEV_DESKTOP\\src\\image\\PDF.pdf"));
       
       Notifications notificationBuilder = Notifications.create()
            .title("Succes").text("Your document has been saved as PDF !!").graphic(null).hideAfter(javafx.util.Duration.seconds(5))
               .position(Pos.CENTER_LEFT)
               .onAction((ActionEvent event1) -> {
                   System.out.println("clicked ON ");
       });
       notificationBuilder.darkStyle();
       notificationBuilder.show();
        } catch (DocumentException | HeadlessException | IOException e) {
            System.out.println("ERROR PDF");
            System.out.println(Arrays.toString(e.getStackTrace()));
            System.out.println(e.getMessage());
        }
    }


    }
    
    

