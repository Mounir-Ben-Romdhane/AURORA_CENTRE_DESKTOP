/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ui;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import entite.User;
import java.awt.Desktop;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
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
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javax.swing.JFileChooser;
import service.UserService;
import util.DataSource;

/**
 * FXML Controller class
 *
 * @author MSI
 */
public class AfficheUsersController implements Initializable {

    @FXML
    private VBox VBoxShowUsers;
    @FXML
    private TableView<User> tableViewUsers;
    @FXML
    private TableColumn<User, String> fullNameCol;
    @FXML
    private TableColumn<User, String> emailCol;
    @FXML
    private TableColumn<User, String> addCol;
    @FXML
    private TableColumn<User, String> numTelCol;
    @FXML
    private TableColumn<User, String> actionsCol;
    
    Button btnBLock = null;
    
    String query = null;
    Connection connection = DataSource.getInstance().getCnx();
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    User user = null;
    
    ObservableList<User> userList = FXCollections.observableArrayList();
    
   
    
    int userId;
    @FXML
    private TableColumn<User, String> isVerifiedCol;
    

    int iP=0;
    @FXML
    private TableColumn<?, ?> etatCol;
    @FXML
    private TextField tfSearch;
    @FXML
    private Button btnSave;
    @FXML
    private ComboBox comboBox;
    @FXML
    private Button btnSavePdf;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            
            ObservableList<String> listTrier = FXCollections.observableArrayList("UserName","Email","Address","Phone");
            comboBox.setItems(listTrier);
            
            loadData();
            FilteredList<User> filtredData = new FilteredList<>(userList,e -> true);
            tfSearch.setOnKeyReleased(e -> {
                tfSearch.textProperty().addListener( (observableValue, oldValue, newValue) ->{
                    filtredData.setPredicate((Predicate<? super User>) user ->{
                        if(newValue == null || newValue.isEmpty()){
                            return true;
                        }
                        String lowerCaseFiler = newValue.toLowerCase();
                        if(user.getEmail().contains(lowerCaseFiler)){
                            return true;
                        }else if(user.getFullAddress().toLowerCase().contains(lowerCaseFiler)){
                            return true;
                        }else if(user.getNumTel().toLowerCase().contains(lowerCaseFiler)){
                            return true;
                        }else if(user.getIsVerified().toLowerCase().contains(lowerCaseFiler)){
                            return true;
                        }
                        
                        return false;
                    });
                });
                SortedList<User> sortedData = new SortedList<>(filtredData);
                sortedData.comparatorProperty().bind(tableViewUsers.comparatorProperty());
                tableViewUsers.setItems(sortedData);
            });
            
        } catch (SQLException ex) {
            Logger.getLogger(AfficheUsersController.class.getName()).log(Level.SEVERE, null, ex);
        }
    } 
    
    private void refrachListClck(){
       userList.clear();
       String res,etat;
       
        try {
             
        query = "SELECT * FROM `user` WHERE id != 1";
        preparedStatement = connection.prepareStatement(query);
        resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                
                if(resultSet.getInt("is_verified")==1){
                    res="Compte Verified";
                }else{
                    res="Compte non Verified";
                }
                
                if(!resultSet.getBoolean("etat")){
                    btnBLock = new Button();
                    btnBLock.setText("Blocked");
                    btnBLock.setStyle("-fx-background-color: red; -fx-text-fill: white;"); 
                    etat="Blocked";
                    
                }else{
                    btnBLock = new Button();
                    btnBLock.setText("Deblocked");
                    btnBLock.setStyle("-fx-background-color: green; -fx-text-fill: white;"); 
                    etat="Deblocked";
                }
                
                
                userList.add(new User(
                        resultSet.getInt("id"),
                        resultSet.getString("username"),
                        resultSet.getString("email"),
                        resultSet.getString("num_tel"),
                        resultSet.getString("full_address"),
                        res,
                        btnBLock
                ));
                tableViewUsers.setItems(userList);
            }
            preparedStatement.close();
            resultSet.close();
        } catch (SQLException ex) {
            Logger.getLogger(AfficheUsersController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(AfficheUsersController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    

    private void loadData() throws SQLException {
        
        
        
        refrachListClck();
        
        fullNameCol.setCellValueFactory(new PropertyValueFactory<>("userName"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        addCol.setCellValueFactory(new PropertyValueFactory<>("fullAddress"));
        numTelCol.setCellValueFactory(new PropertyValueFactory<>("numTel"));
        isVerifiedCol.setCellValueFactory(new PropertyValueFactory<>("isVerified"));
        etatCol.setCellValueFactory(new PropertyValueFactory<>("etat"));
        
        
        
        
        //add cell of button edit 
         Callback<TableColumn<User, String>, TableCell<User, String>> cellFoctory = (TableColumn<User, String> param) -> {
            // make cell containing buttons
            final TableCell<User, String> cell = new TableCell<User, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    //that cell created only on non-empty rows
                    if (empty) {
                        setGraphic(null);
                        setText(null);

                    } else {

                        FontAwesomeIconView deleteIcon = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
                        FontAwesomeIconView blockIcon = new FontAwesomeIconView(FontAwesomeIcon.BAN);
                           
                        deleteIcon.setStyle(
                                " -fx-cursor: hand ;"
                                + "-glyph-size:28px;"
                                + "-fx-fill:#ff1744;"
                        );
                        
                        blockIcon.setStyle(
                                " -fx-cursor: hand ;"
                                + "-glyph-size:28px;"
                                + "-fx-fill:#FFFF00;"
                        );
                        
                        deleteIcon.setOnMouseClicked((MouseEvent event) -> {
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("Confirmation Dialog");
                            alert.setContentText("Are you sure to delete this user ?");
                            alert.setHeaderText(null);
                            Optional <ButtonType> action = alert.showAndWait();
                            if(action.get() == ButtonType.OK ){
                                try {
                                user = tableViewUsers.getSelectionModel().getSelectedItem();
                                query = "DELETE FROM `user` WHERE id  ="+user.getId();
                                
                                preparedStatement = connection.prepareStatement(query);
                                preparedStatement.execute();
                                refrachListClck();
                                
                            } catch (SQLException ex) {
                                Logger.getLogger(AfficheUsersController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            }
                        });
                        
                        blockIcon.setOnMouseClicked((MouseEvent event) -> {
                                    User u = tableViewUsers.getSelectionModel().getSelectedItem();
                                    if(u.getEtat().getText().equals("Blocked")){
                                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                    alert.setTitle("Confirmation Dialog");
                                    alert.setContentText("Are you sure to deblock this user ?");
                                    alert.setHeaderText(null);
                                    Optional <ButtonType> action = alert.showAndWait();
                                    if(action.get() == ButtonType.OK ){
                                        try {
                                        
                                       PreparedStatement preparedStatement = connection.prepareStatement("UPDATE user SET etat = ? WHERE email =?");
                                      preparedStatement.setBoolean(1, true);
                                        preparedStatement.setString(2, u.getEmail().toString());
                                        preparedStatement.execute();
                                        refrachListClck();
                                    } catch (SQLException ex) {
                                        Logger.getLogger(AfficheUsersController.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
                            }
                                if(u.getEtat().getText().equals("Deblocked")){
                                       Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
                                    alert1.setTitle("Confirmation Dialog");
                                    alert1.setContentText("Are you sure to block this user ?");
                                    alert1.setHeaderText(null);
                                    Optional <ButtonType> action1 = alert1.showAndWait();
                                    if(action1.get() == ButtonType.OK ){
                                       
                                       PreparedStatement preparedStatement1;
                                           try {
                                               preparedStatement1 = DataSource.getInstance().getCnx().prepareStatement("UPDATE user SET etat = ? WHERE email =?");
                                               preparedStatement1.setBoolean(1, false);
                                                preparedStatement1.setString(2, u.getEmail().toString());
                                                preparedStatement1.execute();
                                                refrachListClck();
                                           } catch (SQLException ex) {
                                               Logger.getLogger(AfficheUsersController.class.getName()).log(Level.SEVERE, null, ex);
                                           }
                                         

                                    } 
                                }
                        });
                        
                        
                        
                       
                        HBox managebtn = new HBox(blockIcon, deleteIcon);
                       // HBox managebtn = new HBox(deleteIcon);
                        managebtn.setStyle("-fx-alignment:center");
                        HBox.setMargin(deleteIcon, new Insets(2, 2, 0, 3));
                        HBox.setMargin(blockIcon, new Insets(2, 3, 0, 2));

                        setGraphic(managebtn);

                        setText(null);

                    }
                }

            };

            return cell;
        };
         actionsCol.setCellFactory(cellFoctory);
         tableViewUsers.setItems(userList);
         
        
    }

    @FXML
    private void handleSaveFilee(ActionEvent event) {
        Stage secondaryStage = new Stage () ;
        FileChooser fileChooser = new FileChooser ();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All files","*.*"),
                new FileChooser.ExtensionFilter("txt","*.txt"),
                new FileChooser.ExtensionFilter("pdf","*.pdf","*.docx")
                );
        fileChooser. setTitle ("Save User Table");
        fileChooser.setInitialDirectory(new File(System. getProperty("user.home") ));
        if (userList.isEmpty()) {
            secondaryStage.initOwner(this.VBoxShowUsers.getScene().getWindow());
            Alert emptyTableAlert = new Alert (Alert.AlertType.ERROR, "EMPTY TABLE", ButtonType.OK) ;
            emptyTableAlert.setContentText("You have nothing to save");
            emptyTableAlert.initModality(Modality.APPLICATION_MODAL) ;
            emptyTableAlert.initOwner(this.VBoxShowUsers.getScene().getWindow());
            emptyTableAlert.showAndWait();
            if (emptyTableAlert.getResult() == ButtonType.OK) {
            emptyTableAlert.close ();
            }
        }
        else {
            File file = fileChooser.showSaveDialog(secondaryStage);
            if(file != null) {
            saveFile(tableViewUsers.getItems(),file);
            }
        }
    }

    private void saveFile(ObservableList<User> items, File file) {
        try {
            BufferedWriter outWriter = new BufferedWriter(new FileWriter(file)) ;
            
            for (User user: userList) {
                outWriter.write(user.toString()) ;
                outWriter.newLine();
                }
                System.out.println(userList.toString());
                outWriter.close();
             } catch (IOException e) {
            Alert ioAlert = new Alert (Alert.AlertType. ERROR, "OOPS!", ButtonType. OK);
            ioAlert.setContentText("Sorry. An error has occurred.");
            ioAlert.showAndWait() ;
            if (ioAlert.getResult () == ButtonType.OK) {
            ioAlert.close();
                    
            }
                    }
                    
    }
    
    
    @FXML
    private void handleSaveFile() throws FileNotFoundException, DocumentException, BadElementException, IOException, SQLException {
     
       
        Document doc= new Document();
        PdfWriter.getInstance(doc,new FileOutputStream("User_list.pdf"));
        doc.open();
        String format="dd/mm/yy hh:mm";
        SimpleDateFormat formater=new SimpleDateFormat(format);
        java.util.Date date=new java.util.Date();
        Paragraph paragraph = new Paragraph("AURORA CENTRE");
        paragraph.setAlignment(com.itextpdf.text.Element.ALIGN_CENTER);

        doc.add(paragraph);
        doc.add(new Paragraph("\n"));
        doc.add(new Paragraph("All users information in this table :"+"\n"));
         doc.add(new Paragraph("\n"));
         PdfPTable t = new PdfPTable(5);
         // Set the total width of the table to the width of the page
            t.setTotalWidth(doc.getPageSize().getWidth() - doc.leftMargin() - doc.rightMargin());
         
         PdfPCell cell = new PdfPCell(new Phrase("Username"));
        cell.setBackgroundColor(BaseColor.PINK); // set the background color of the cell
        t.addCell(cell);
         
        PdfPCell cell1 = new PdfPCell(new Phrase("Email"));
        cell1.setBackgroundColor(BaseColor.PINK); // set the background color of the cell
        t.addCell(cell1);
        
        PdfPCell cell2 = new PdfPCell(new Phrase("Address"));
        cell2.setBackgroundColor(BaseColor.PINK); // set the background color of the cell
        t.addCell(cell2);
        
        PdfPCell cell3 = new PdfPCell(new Phrase("Phone Number"));
        cell3.setBackgroundColor(BaseColor.PINK); // set the background color of the cell
        t.addCell(cell3);
        
        PdfPCell cell4 = new PdfPCell(new Phrase("IsVerified"));
        cell4.setBackgroundColor(BaseColor.PINK); // set the background color of the cell
        t.addCell(cell4);
        
         
           for (User user: userList) {
                t.addCell(user.getUserName());
               t.addCell(user.getEmail());
                t.addCell(user.getFullAddress());
                t.addCell(user.getNumTel());
                t.addCell(user.getIsVerified());
                }
           doc.add(t);
        Desktop.getDesktop().open(new File("User_list.pdf"));
        
        doc.close();
    }

    @FXML
    private void Select(ActionEvent event) {
        if(comboBox.getSelectionModel().getSelectedItem().toString().equals("UserName")){
            userList.clear();
            String res,etat;
             try {
             
                query = "SELECT * FROM `user` WHERE id != 1 ORDER BY username ASC";
                preparedStatement = connection.prepareStatement(query);
                resultSet = preparedStatement.executeQuery();
                    while(resultSet.next()){

                        if(resultSet.getInt("is_verified")==1){
                            res="Compte Verified";
                        }else{
                            res="Compte non Verified";
                        }

                        if(!resultSet.getBoolean("etat")){
                            btnBLock = new Button();
                            btnBLock.setText("Blocked");
                            btnBLock.setStyle("-fx-background-color: red; -fx-text-fill: white;"); 
                            etat="Blocked";

                        }else{
                            btnBLock = new Button();
                            btnBLock.setText("Deblocked");
                            btnBLock.setStyle("-fx-background-color: green; -fx-text-fill: white;"); 
                            etat="Deblocked";
                        }


                        userList.add(new User(
                                resultSet.getInt("id"),
                                resultSet.getString("username"),
                                resultSet.getString("email"),
                                resultSet.getString("num_tel"),
                                resultSet.getString("full_address"),
                                res,
                                btnBLock
                        ));
                        tableViewUsers.setItems(userList);
                    }
                    preparedStatement.close();
                    resultSet.close();
                } catch (SQLException ex) {
                    Logger.getLogger(AfficheUsersController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception ex) {
                    Logger.getLogger(AfficheUsersController.class.getName()).log(Level.SEVERE, null, ex);
                }
        }else if(comboBox.getSelectionModel().getSelectedItem().toString().equals("Email")){
            userList.clear();
            String res,etat;
             try {
             
                query = "SELECT * FROM `user` WHERE id != 1 ORDER BY email ASC";
                preparedStatement = connection.prepareStatement(query);
                resultSet = preparedStatement.executeQuery();
                    while(resultSet.next()){

                        if(resultSet.getInt("is_verified")==1){
                            res="Compte Verified";
                        }else{
                            res="Compte non Verified";
                        }

                        if(!resultSet.getBoolean("etat")){
                            btnBLock = new Button();
                            btnBLock.setText("Blocked");
                            btnBLock.setStyle("-fx-background-color: red; -fx-text-fill: white;"); 
                            etat="Blocked";

                        }else{
                            btnBLock = new Button();
                            btnBLock.setText("Deblocked");
                            btnBLock.setStyle("-fx-background-color: green; -fx-text-fill: white;"); 
                            etat="Deblocked";
                        }


                        userList.add(new User(
                                resultSet.getInt("id"),
                                resultSet.getString("username"),
                                resultSet.getString("email"),
                                resultSet.getString("num_tel"),
                                resultSet.getString("full_address"),
                                res,
                                btnBLock
                        ));
                        tableViewUsers.setItems(userList);
                    }
                    preparedStatement.close();
                    resultSet.close();
                } catch (SQLException ex) {
                    Logger.getLogger(AfficheUsersController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception ex) {
                    Logger.getLogger(AfficheUsersController.class.getName()).log(Level.SEVERE, null, ex);
                }
        }else if(comboBox.getSelectionModel().getSelectedItem().toString().equals("Address")){
            userList.clear();
            String res,etat;
             try {
             
                query = "SELECT * FROM `user` WHERE id != 1 ORDER BY full_address ASC";
                preparedStatement = connection.prepareStatement(query);
                resultSet = preparedStatement.executeQuery();
                    while(resultSet.next()){

                        if(resultSet.getInt("is_verified")==1){
                            res="Compte Verified";
                        }else{
                            res="Compte non Verified";
                        }

                        if(!resultSet.getBoolean("etat")){
                            btnBLock = new Button();
                            btnBLock.setText("Blocked");
                            btnBLock.setStyle("-fx-background-color: red; -fx-text-fill: white;"); 
                            etat="Blocked";

                        }else{
                            btnBLock = new Button();
                            btnBLock.setText("Deblocked");
                            btnBLock.setStyle("-fx-background-color: green; -fx-text-fill: white;"); 
                            etat="Deblocked";
                        }


                        userList.add(new User(
                                resultSet.getInt("id"),
                                resultSet.getString("username"),
                                resultSet.getString("email"),
                                resultSet.getString("num_tel"),
                                resultSet.getString("full_address"),
                                res,
                                btnBLock
                        ));
                        tableViewUsers.setItems(userList);
                    }
                    preparedStatement.close();
                    resultSet.close();
                } catch (SQLException ex) {
                    Logger.getLogger(AfficheUsersController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception ex) {
                    Logger.getLogger(AfficheUsersController.class.getName()).log(Level.SEVERE, null, ex);
                }
        }else if(comboBox.getSelectionModel().getSelectedItem().toString().equals("Phone")){
            userList.clear();
            String res,etat;
             try {
             
                query = "SELECT * FROM `user` WHERE id != 1 ORDER BY num_tel ASC";
                preparedStatement = connection.prepareStatement(query);
                resultSet = preparedStatement.executeQuery();
                    while(resultSet.next()){

                        if(resultSet.getInt("is_verified")==1){
                            res="Compte Verified";
                        }else{
                            res="Compte non Verified";
                        }

                        if(!resultSet.getBoolean("etat")){
                            btnBLock = new Button();
                            btnBLock.setText("Blocked");
                            btnBLock.setStyle("-fx-background-color: red; -fx-text-fill: white;"); 
                            etat="Blocked";

                        }else{
                            btnBLock = new Button();
                            btnBLock.setText("Deblocked");
                            btnBLock.setStyle("-fx-background-color: green; -fx-text-fill: white;"); 
                            etat="Deblocked";
                        }


                        userList.add(new User(
                                resultSet.getInt("id"),
                                resultSet.getString("username"),
                                resultSet.getString("email"),
                                resultSet.getString("num_tel"),
                                resultSet.getString("full_address"),
                                res,
                                btnBLock
                        ));
                        tableViewUsers.setItems(userList);
                    }
                    preparedStatement.close();
                    resultSet.close();
                } catch (SQLException ex) {
                    Logger.getLogger(AfficheUsersController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception ex) {
                    Logger.getLogger(AfficheUsersController.class.getName()).log(Level.SEVERE, null, ex);
                }
        }
        
    }

    
    
}
