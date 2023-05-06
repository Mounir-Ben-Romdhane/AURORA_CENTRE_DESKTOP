/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import animatefx.animation.Pulse;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import entite.Participation;
import java.awt.Desktop;
import java.awt.HeadlessException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
//import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import org.controlsfx.control.Notifications;
//import org.w3c.dom.Document;
import service.ParticipationService;
import util.DataSource;


/**
 * FXML Controller class
 *
 * @author asus
 */
public class AfficherParticipationController implements Initializable {

    private Button btnLogOut;
    @FXML
    private VBox vBoxDashboardAdmin;
    @FXML
    private TableView<Participation> TableViewP;
    @FXML
    private TableColumn<Participation, Integer> idpC;
    @FXML
    private TableColumn<Participation, String> description_pnC;
    @FXML
    private TableColumn<Participation, String> usernameevC;
    @FXML
    private TableColumn<Participation, String> emailevC;
    @FXML
    private TableColumn<Participation, Integer> numtelevC;
    private TextField descriptionM;
    private TextField usernameM;
    private TextField emailM;
    private TextField telM;
    
    ParticipationService Ps = new ParticipationService();
    @FXML
    private TableColumn<Participation, Button> qr_code;
    @FXML
    private Button createpdf;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        getParticipation();
        this.qr_code();
    }    
    private void qr_code() {

        qr_code.setCellFactory((param) -> {
            return new TableCell() {
                @Override
                protected void updateItem(Object item, boolean empty) {
                    setGraphic(null);
                    if (!empty) {
                        Button b = new Button("qr code");
                        b.setOnAction((event) -> {
                            
                            ParticipationService rs=new ParticipationService();
                            Participation res = new Participation();
                            //res= rs.chercherParticipation(TableViewP.getItems().get(getIndex()));
                            String data = "id: "+res.getId()+" description: "+res.getDescription_pn()+" username: "+res.getUsernameev();
                            int size = 250;
                            String fileType = "png";
                            File qrFile = new File("C:\\Users\\MSI\\OneDrive\\Documents\\GitHub\\PIDEV_DESKTOP\\src\\image\\code." + fileType);
                            try {
                                // Créez une instance de la classe QRCodeWriter
                                QRCodeWriter qrCodeWriter = new QRCodeWriter();
                                
                                // Définissez les paramètres d'encodage du QR code
                                ErrorCorrectionLevel errorCorrectionLevel = ErrorCorrectionLevel.L;
                                java.util.Map<EncodeHintType, Object> hints = new java.util.HashMap<EncodeHintType, Object>();
                                hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
                                hints.put(EncodeHintType.ERROR_CORRECTION, errorCorrectionLevel);
                                
                                // Encodez les données dans le QR code
                                BitMatrix bitMatrix = qrCodeWriter.encode(data, BarcodeFormat.QR_CODE, size, size, hints);
                                
                                // Enregistrez le QR code dans un fichier image
                                MatrixToImageWriter.writeToFile(bitMatrix, fileType, qrFile);
                                
                                System.out.println("QR code généré avec succès !");
                                
                                JFrame frame = new JFrame();
                                ImageIcon icon = new ImageIcon("C:\\Users\\MSI\\OneDrive\\Documents\\GitHub\\PIDEV_DESKTOP\\src\\image\\code." + fileType);
                                
                                
                                JLabel label = new JLabel(icon);
                                frame.add(label);
                                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                                frame.pack();
                                frame.setVisible(true);
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (com.google.zxing.WriterException ex) {
                                Logger.getLogger(AfficherParticipationController.class.getName()).log(Level.SEVERE, null, ex);
                            }

                        });
                        setGraphic(b);

                    }
                }
            };

        });
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    public void getParticipation() {
        // TODO
        try {
            List<Participation> part = Ps.recupererParticipation();
            ObservableList<Participation> olp = FXCollections.observableArrayList(part);
            TableViewP.setItems(olp);
            idpC.setCellValueFactory(new PropertyValueFactory("id"));
            description_pnC.setCellValueFactory(new PropertyValueFactory("description_pn"));
            usernameevC.setCellValueFactory(new PropertyValueFactory("usernameev"));
            emailevC.setCellValueFactory(new PropertyValueFactory("emailev"));
            numtelevC.setCellValueFactory(new PropertyValueFactory("numtelev"));

        } catch (SQLException ex) {
            System.out.println("error" + ex.getMessage());
        }
    }


    private void handleLogOut(ActionEvent event) throws IOException {
        Stage currentStage = (Stage) btnLogOut.getScene().getWindow();
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


    private void retourev(ActionEvent event) {
         try {
            Parent reclamationParent = FXMLLoader.load(getClass().getResource("Evenement.fxml"));
            Scene reclamationScene = new Scene(reclamationParent);
            Stage window = (Stage) (((Button) event.getSource()).getScene().getWindow());
            window.setScene(reclamationScene);
            window.show();
        } catch (IOException e) {
        }
    }

    @FXML
    private void choisirEvent(MouseEvent event) {
        Participation e = TableViewP.getItems().get(TableViewP.getSelectionModel().getSelectedIndex());
        descriptionM.setText(e.getDescription_pn());
        emailM.setText(e.getEmailev());
        usernameM.setText(e.getUsernameev());
        telM.setText(String.valueOf(e.getNumtelev()));
    }


   @FXML
   private void createpdf(ActionEvent event) throws SQLException {
        try {
       Document doc = new Document();
       PdfWriter.getInstance(doc,new FileOutputStream("C:\\Users\\MSI\\OneDrive\\Documents\\GitHub\\PIDEV_DESKTOP\\src\\image\\pdf.pdf"));
       doc.open();
       
      Image img = Image.getInstance("C:\\Users\\MSI\\OneDrive\\Documents\\GitHub\\PIDEV_DESKTOP\\src\\image\\aurora.png");
      img.scaleAbsoluteHeight(200);
       img.scaleAbsoluteWidth(200);
     img.setAlignment(Image.ALIGN_CENTER);
      doc.add(img);
       
       doc.add(new Paragraph(" "));
       Font font = new Font(FontFamily.HELVETICA, 32, Font.NORMAL, BaseColor.YELLOW);
       Paragraph p = new Paragraph("Liste des Participations", font);
       p.setAlignment(Element.ALIGN_CENTER);
       doc.add(p);
       doc.add(new Paragraph(" "));
       doc.add(new Paragraph(" "));
       
       PdfPTable tabpdf = new PdfPTable(5);
       tabpdf.setWidthPercentage(100);
       
       PdfPCell cell;
          
       cell = new PdfPCell(new Phrase("id", FontFactory.getFont("Times New Roman", 11)));
       cell.setHorizontalAlignment(Element.ALIGN_CENTER);
       cell.setBackgroundColor(BaseColor.WHITE);
       tabpdf.addCell(cell);
       
       cell = new PdfPCell(new Phrase("description_pn", FontFactory.getFont("Times New Roman", 11)));
       cell.setHorizontalAlignment(Element.ALIGN_CENTER);
       cell.setBackgroundColor(BaseColor.WHITE);
       tabpdf.addCell(cell);
       
       cell = new PdfPCell(new Phrase("usernameev", FontFactory.getFont("Times New Roman", 11)));
       cell.setHorizontalAlignment(Element.ALIGN_CENTER);
       cell.setBackgroundColor(BaseColor.WHITE);
       tabpdf.addCell(cell);
       
       cell = new PdfPCell(new Phrase("emailev", FontFactory.getFont("Times New Roman", 11)));
       cell.setHorizontalAlignment(Element.ALIGN_CENTER);
       cell.setBackgroundColor(BaseColor.WHITE);
       tabpdf.addCell(cell);
       
       cell = new PdfPCell(new Phrase("numtelev", FontFactory.getFont("Times New Roman", 11)));
       cell.setHorizontalAlignment(Element.ALIGN_CENTER);
       cell.setBackgroundColor(BaseColor.WHITE);
       tabpdf.addCell(cell);
       
   
       
       Connection con1 = DataSource.getInstance().getCnx();
       String req = "SELECT id,description_pn,usernameev,emailev,numtelev from participationns";
        Statement st = con1.createStatement();
        ResultSet rs1 =  st.executeQuery(req);
        
        
        
        
       while(rs1.next()){
           System.out.println(rs1.getString("id"));
           cell = new PdfPCell(new Phrase(rs1.getString("id"), FontFactory.getFont("Times New Roman", 11)));
           cell.setHorizontalAlignment(Element.ALIGN_CENTER);
           cell.setBackgroundColor(BaseColor.WHITE);
           tabpdf.addCell(cell);
           
           cell = new PdfPCell(new Phrase(rs1.getString("description_pn"), FontFactory.getFont("Times New Roman", 11)));
           cell.setHorizontalAlignment(Element.ALIGN_CENTER);
           cell.setBackgroundColor(BaseColor.WHITE);
           tabpdf.addCell(cell);
           
            cell = new PdfPCell(new Phrase(rs1.getString("usernameev"), FontFactory.getFont("Times New Roman", 11)));
           cell.setHorizontalAlignment(Element.ALIGN_CENTER);
           cell.setBackgroundColor(BaseColor.WHITE);
           tabpdf.addCell(cell);
           
           
            cell = new PdfPCell(new Phrase(rs1.getString("emailev"), FontFactory.getFont("Times New Roman", 11)));
           cell.setHorizontalAlignment(Element.ALIGN_CENTER);
           cell.setBackgroundColor(BaseColor.WHITE);
           tabpdf.addCell(cell);
           
            cell = new PdfPCell(new Phrase(rs1.getString("numtelev"), FontFactory.getFont("Times New Roman", 11)));
           cell.setHorizontalAlignment(Element.ALIGN_CENTER);
           cell.setBackgroundColor(BaseColor.WHITE);
           tabpdf.addCell(cell);
           
       }
       
       doc.add(tabpdf);
       JOptionPane.showMessageDialog(null, "Success !!");
       doc.close();
       Desktop.getDesktop().open(new File("C:\\Users\\MSI\\OneDrive\\Documents\\GitHub\\PIDEV_DESKTOP\\src\\image\\pdf.pdf"));
       
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
