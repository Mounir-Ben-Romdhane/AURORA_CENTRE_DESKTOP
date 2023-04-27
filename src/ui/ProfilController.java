/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ui;

import entite.User;
import java.awt.Desktop;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import service.UserService;
import util.DataSource;

/**
 * FXML Controller class
 *
 * @author MSI
 */
public class ProfilController implements Initializable {

    @FXML
    private VBox VBoxProfil;
    @FXML
    private TextField tfFullNameProfil;
    @FXML
    private TextField tfFullAddressProfil;
    @FXML
    private TextField tfNumTelProfil;
    @FXML
    private TextField tfEmailProfil;
    @FXML
    private Label hello;
    @FXML
    private Label hello1;
    @FXML
    private Label hello11;
    @FXML
    private Label hello2;
    @FXML
    private Label hello111;
    @FXML
    private Label hello1111;
    @FXML
    private Button btnModifProfil;
    @FXML
    private Label lbFullNameProfil;
    @FXML
    private Label lbFullAddresseProfil;
    @FXML
    private Label lbNumTelProfil;
    @FXML
    private Label lbEmailProfil;
    
    private boolean bName=false,bEmail=false,bAdd=false,bTel=false,bPass=false,bConfPass=false;
    @FXML
    private Label hello21;
    @FXML
    private Label hello1112;
    @FXML
    private Label hello11111;
    @FXML
    private PasswordField tfPasswordReset;
    @FXML
    private Label lbPasswordReset;
    @FXML
    private PasswordField tfConfirmePasswordReset;
    @FXML
    private Label lbConfirmePasswordReset;
    @FXML
    private Button btnResetPass;
    @FXML
    private Button btnBrowser;
    private FileChooser fileChooser;
    private File file;
    @FXML
    private Pane pane;
    private Stage stage;
    private final Desktop desktop = Desktop.getDesktop(); 
    @FXML
    private ImageView imageView;
    private Image image;
    private FileInputStream fis;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All files","*.*"),
                new FileChooser.ExtensionFilter("Images","*.png","*.jpg","*.gif")
                );
        
        tfEmailProfil.addEventFilter(KeyEvent.ANY, event -> {
            event.consume();
            lbEmailProfil.setFont(Font.font("Roboto",FontWeight.BOLD,12));
            lbEmailProfil.setText("You can't change your email from here !");
            lbEmailProfil.setTextFill(Color.YELLOW);
        });
        
        //COntrole saisie fullName
        lbFullNameProfil.setFont(Font.font("Roboto",FontWeight.BOLD,12));
        
        tfFullNameProfil.onKeyReleasedProperty().set(new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent event) {
                int length = tfFullNameProfil.getText().toString().length();
                
                if(length == 0){
                    lbFullNameProfil.setText("Please enter your full name");
                    lbFullNameProfil.setTextFill(Color.BLACK);
                    
                }else if(length < 5){
                    lbFullNameProfil.setText("Week");
                    lbFullNameProfil.setTextFill(Color.RED);
                }else if(length>=5 && length<=8){
                    lbFullNameProfil.setText("Good");
                    lbFullNameProfil.setTextFill(Color.BLUE);
                    bName=true;
                }else if(length>8){
                    lbFullNameProfil.setText("Strong");
                    lbFullNameProfil.setTextFill(Color.GREEN);
                    bName=true;
                }
                
            }
            
        });
        
        //COntrole saisie address
        lbFullAddresseProfil.setFont(Font.font("Roboto",FontWeight.BOLD,12));
        
        tfFullAddressProfil.onKeyReleasedProperty().set(new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent event) {
                int length = tfFullAddressProfil.getText().toString().length();
                
                if(length == 0){
                    lbFullAddresseProfil.setText("Please enter your address");
                    lbFullAddresseProfil.setTextFill(Color.BLACK);
                }else if(length < 5){
                    lbFullAddresseProfil.setText("Week");
                    lbFullAddresseProfil.setTextFill(Color.RED);
                }else if(length>=5 && length<=8){
                    lbFullAddresseProfil.setText("Good");
                    lbFullAddresseProfil.setTextFill(Color.BLUE);
                    bAdd=true;
                }else if(length>8){
                    lbFullAddresseProfil.setText("Strong");
                    lbFullAddresseProfil.setTextFill(Color.GREEN);
                    bAdd=true;
                }
            }
            
        });
        
        //COntrole saisie numTel
        lbNumTelProfil.setFont(Font.font("Roboto",FontWeight.BOLD,12));
        
        TextFormatter<String> textFormatter = new TextFormatter<>(change -> {
                String newText = change.getControlNewText();
                if (newText.matches("\\d{0,8}")) {
                    return change;
                }
                return null;
            });
            tfNumTelProfil.setTextFormatter(textFormatter);
        
        tfNumTelProfil.onKeyReleasedProperty().set(new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent event) {
                int length = tfNumTelProfil.getText().toString().length();
                
                
                
                if(length == 0){
                    lbNumTelProfil.setText("Please enter your phone number");
                    lbNumTelProfil.setTextFill(Color.BLACK);
                }else if(length < 8){
                    lbNumTelProfil.setText("Week");
                    lbNumTelProfil.setTextFill(Color.RED);
                }else if(length==8){
                    lbNumTelProfil.setText("Good");
                    lbNumTelProfil.setTextFill(Color.GREEN);
                    bTel=true;
                }
            }
            
        });
        
        //CS password
        lbPasswordReset.setFont(Font.font("Roboto",FontWeight.BOLD,12));
        
        tfPasswordReset.onKeyReleasedProperty().set(new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent event) {
                int length = tfPasswordReset.getText().toString().length();
                
                if(length == 0){
                    lbPasswordReset.setText("Please enter your password");
                    lbPasswordReset.setTextFill(Color.BLACK);
                }else if(length < 5){
                    lbPasswordReset.setText("Week");
                    lbPasswordReset.setTextFill(Color.RED);
                }else if(length>=5 && length<=28){
                    lbPasswordReset.setText("Good");
                    lbPasswordReset.setTextFill(Color.GREEN);
                    bPass=true;
                }
            }
            
        });
        
        //CS confirme pass
        lbConfirmePasswordReset.setFont(Font.font("Roboto",FontWeight.BOLD,12));
        
        tfConfirmePasswordReset.onKeyReleasedProperty().set(new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent event) {
                String cP = tfConfirmePasswordReset.getText().toString();
                
                if(!cP.equals(tfPasswordReset.getText().toString())){
                    lbConfirmePasswordReset.setText("Please confirme your password");
                    lbConfirmePasswordReset.setTextFill(Color.RED);
                }else if(cP.equals(tfPasswordReset.getText().toString())){
                    lbConfirmePasswordReset.setText("Password confirme");
                    lbConfirmePasswordReset.setTextFill(Color.GREEN);
                    bConfPass=true;
                }
            }
            
        });
        
        try {
            loadInfo();
        } catch (Exception ex) {
            Logger.getLogger(ProfilController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }    

    @FXML
    private void changeProfilAction(ActionEvent event) throws Exception {
        UserService ps = new UserService();
        fis = new FileInputStream(file);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("Confirmation Dialog");
                            alert.setContentText("Are you sure to update this information ?");
                            alert.setHeaderText(null);
                            Optional <ButtonType> action = alert.showAndWait();
                            if(action.get() == ButtonType.OK ){
                                User p = new User(tfFullNameProfil.getText(), tfEmailProfil.getText(), tfNumTelProfil.getText(), tfFullAddressProfil.getText());
                                ps.updateProfil(p,fis,file);
                                 Alert alertS = new Alert(Alert.AlertType.INFORMATION);
                                alertS.setContentText("Profil updated successfully !!");
                                alertS.setHeaderText(null);
                                alertS.setTitle("Succes");
                                alertS.show();
                                loadInfo();
                            }
               
    }
    private void loadInfo() throws Exception {
        UserService ps = new UserService();
        User currentUser = ps.getCurrentUser();
        
        
        tfFullNameProfil.setText(currentUser.getUserName());
        tfEmailProfil.setText(currentUser.getEmail());
        tfNumTelProfil.setText(currentUser.getNumTel());
        tfFullAddressProfil.setText(currentUser.getFullAddress());
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        
        try {   
                   preparedStatement = DataSource.getInstance().getCnx().prepareStatement("SELECT imagee FROM user WHERE email = ?");
                    preparedStatement.setString(1, currentUser.getEmail());
                    resultSet = preparedStatement.executeQuery();
                    if(resultSet.next()){
                        InputStream is = resultSet.getBinaryStream(1);
                        OutputStream os = new FileOutputStream(new File("photo.jpg"));
                        byte[] contents = new byte[1024];
                        int size = 0;
                        while( (size = is.read(contents)) != -1 ){
                            os.write(contents,0,size);
                            image = new Image("file:photo.jpg",imageView.getFitWidth(),imageView.getFitHeight(),true,true);
                             imageView.setImage(image);
                        }
                    }
                    
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        //String imageDataString = ps.getImage(currentUser.getEmail());
        //System.out.println(imageDataString);
        //byte[] imageData = currentUser.getPassword().getBytes();
        //Image image = new Image(new ByteArrayInputStream(imageData));
        //imageView.setImage(image);
    }

    @FXML
    private void ResetPasswordAction(ActionEvent event) throws Exception {
        UserService ps = new UserService();
        if(bPass && bConfPass){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("Confirmation Dialog");
                            alert.setContentText("Are you sure to change your password ?");
                            alert.setHeaderText(null);
                            Optional <ButtonType> action = alert.showAndWait();
                            if(action.get() == ButtonType.OK ){
                                ps.resetPassword(tfConfirmePasswordReset.getText(),tfEmailProfil.getText());
                                 Alert alertS = new Alert(Alert.AlertType.INFORMATION);
                                alertS.setContentText("Password changed successfully !!");
                                alertS.setHeaderText(null);
                                alertS.setTitle("Succes");
                                alertS.show();
                                tfConfirmePasswordReset.setText("");
                                tfPasswordReset.setText("");
                                tfConfirmePasswordReset.clear();
                                tfPasswordReset.clear();
                                lbConfirmePasswordReset.setText("");
                                lbPasswordReset.setText("");
                            }
        }else{
            lbPasswordReset.setText("Please enter your new password !");
            lbPasswordReset.setTextFill(Color.RED);
        }
        
    }

    @FXML
    private void handleBrowser(ActionEvent event) {
        stage = (Stage) pane.getScene().getWindow();
        file = fileChooser.showOpenDialog(stage);
        /*
        try {
            desktop.open(file);
        } catch (IOException ex) {
            Logger.getLogger(ProfilController.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        if(file != null){
            System.out.println(""+file.getAbsolutePath());
            image = new Image(file.getAbsoluteFile().toURI().toString(),imageView.getFitWidth(),imageView.getFitHeight(),true,true);
            imageView.setImage(image);
            imageView.setPreserveRatio(true);         
        }
    }


    
    
}
