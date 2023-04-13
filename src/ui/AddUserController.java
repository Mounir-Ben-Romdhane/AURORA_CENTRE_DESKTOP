/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ui;

import animatefx.animation.BounceIn;
import entite.User;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import service.UserService;

/**
 * FXML Controller class
 *
 * @author MSI
 */
public class AddUserController implements Initializable {

    

    @FXML
    private VBox VBoxAddUser;
    @FXML
    private Label hello;
    @FXML
    private TextField tfFullNameUser;
    @FXML
    private Label lbFullName;
    @FXML
    private TextField tfEmailUser;
    @FXML
    private Label lbEmailSignUp;
    @FXML
    private TextField tfFullAddressUser;
    @FXML
    private TextField tfNumTelUser;
    @FXML
    private Label lbNumTelSignUp;
    @FXML
    private PasswordField tfPasswordUser;
    @FXML
    private Label lbPasswordSignUp;
    @FXML
    private PasswordField tfConfirmePasswordUser;
    @FXML
    private Label lbConfirmePasswordSignUp;
    @FXML
    private Button btnAddUser;
    
     private boolean bName=false,bEmail=false,bAdd=false,bTel=false,bPass=false,bConfPass=false;
    @FXML
    private Label lbFullAddress;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        //COntrole saisie fullName
        lbFullName.setFont(Font.font("Roboto",FontWeight.BOLD,12));
        
        tfFullNameUser.onKeyReleasedProperty().set(new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent event) {
                int length = tfFullNameUser.getText().toString().length();
                
                if(length == 0){
                    lbFullName.setText("Please enter your full name");
                    lbFullName.setTextFill(Color.BLACK);
                    
                }else if(length < 5){
                    lbFullName.setText("Week");
                    lbFullName.setTextFill(Color.RED);
                }else if(length>=5 && length<=8){
                    lbFullName.setText("Good");
                    lbFullName.setTextFill(Color.BLUE);
                    bName=true;
                }else if(length>8){
                    lbFullName.setText("Strong");
                    lbFullName.setTextFill(Color.GREEN);
                    bName=true;
                }
                
            }
            
        });
        //COntrole saisie EMail
        lbEmailSignUp.setFont(Font.font("Roboto",FontWeight.BOLD,12));
        
        tfEmailUser.onKeyReleasedProperty().set(new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent event) {
                Pattern pattern = Pattern.compile("[a-zA-Z0-9][a-zA-Z0-9._]*@[a-zA-Z0-9._]+([.][a-zA-Z0-9]+)+");
                Matcher match = pattern.matcher(tfEmailUser.getText());
                int length = tfEmailUser.getText().toString().length();
                
                if(length == 0){
                    lbEmailSignUp.setText("Please enter your email");
                    lbEmailSignUp.setTextFill(Color.BLACK);
                }else if(length < 8 ){
                    lbEmailSignUp.setText("Week ( Email should be longer then 8 and should contains '@' , '.' )");
                    lbEmailSignUp.setTextFill(Color.RED);
                }else if(!tfEmailUser.getText().toString().contains("@")){
                    lbEmailSignUp.setText("Week ( Email should be longer then 8 and should contains '@' , '.' )");
                    lbEmailSignUp.setTextFill(Color.RED);
                }else if(!tfEmailUser.getText().toString().contains(".")){
                    lbEmailSignUp.setText("Week ( Email should be longer then 8 and should contains '@' , '.' )");
                    lbEmailSignUp.setTextFill(Color.RED);
                }
                else{
                    if(match.find() && match.group().equals(tfEmailUser.getText()))
                    {
                        lbEmailSignUp.setText("Valide email");
                        lbEmailSignUp.setTextFill(Color.GREEN);
                        bEmail=true;
                    }else {
                        lbEmailSignUp.setText("Week ( Email should be longer then 8 and should contains '@' , '.' )");
                         lbEmailSignUp.setTextFill(Color.RED);
                        bEmail=false;
                    }
                }
            }
            
        });
        //COntrole saisie address
        lbFullAddress.setFont(Font.font("Roboto",FontWeight.BOLD,12));
        
        tfFullAddressUser.onKeyReleasedProperty().set(new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent event) {
                int length = tfFullAddressUser.getText().toString().length();
                
                if(length == 0){
                    lbFullAddress.setText("Please enter your address");
                    lbFullAddress.setTextFill(Color.BLACK);
                }else if(length < 5){
                    lbFullAddress.setText("Week");
                    lbFullAddress.setTextFill(Color.RED);
                }else if(length>=5 && length<=8){
                    lbFullAddress.setText("Good");
                    lbFullAddress.setTextFill(Color.BLUE);
                    bAdd=true;
                }else if(length>8){
                    lbFullAddress.setText("Strong");
                    lbFullAddress.setTextFill(Color.GREEN);
                    bAdd=true;
                }
            }
            
        });
        
        //COntrole saisie numTel
        lbNumTelSignUp.setFont(Font.font("Roboto",FontWeight.BOLD,12));
        
        TextFormatter<String> textFormatter = new TextFormatter<>(change -> {
                String newText = change.getControlNewText();
                if (newText.matches("\\d{0,8}")) {
                    return change;
                }
                return null;
            });
            tfNumTelUser.setTextFormatter(textFormatter);
        
        tfNumTelUser.onKeyReleasedProperty().set(new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent event) {
                int length = tfNumTelUser.getText().toString().length();
                
                if(length == 0){
                    lbNumTelSignUp.setText("Please enter your phone number");
                    lbNumTelSignUp.setTextFill(Color.BLACK);
                }else if(length < 8){
                    lbNumTelSignUp.setText("Week");
                    lbNumTelSignUp.setTextFill(Color.RED);
                }else if(length==8){
                    lbNumTelSignUp.setText("Good");
                    lbNumTelSignUp.setTextFill(Color.GREEN);
                    bTel=true;
                }
            }
            
        });
        
        //CS password
        lbPasswordSignUp.setFont(Font.font("Roboto",FontWeight.BOLD,12));
        
        tfPasswordUser.onKeyReleasedProperty().set(new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent event) {
                int length = tfPasswordUser.getText().toString().length();
                
                if(length == 0){
                    lbPasswordSignUp.setText("Please enter your password");
                    lbPasswordSignUp.setTextFill(Color.BLACK);
                }else if(length < 5){
                    lbPasswordSignUp.setText("Week");
                    lbPasswordSignUp.setTextFill(Color.RED);
                }else if(length>=5 && length<=28){
                    lbPasswordSignUp.setText("Good");
                    lbPasswordSignUp.setTextFill(Color.GREEN);
                    bPass=true;
                }
            }
            
        });
        
        //CS confirme pass
        lbConfirmePasswordSignUp.setFont(Font.font("Roboto",FontWeight.BOLD,12));
        
        tfConfirmePasswordUser.onKeyReleasedProperty().set(new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent event) {
                String cP = tfConfirmePasswordUser.getText().toString();
                
                if(!cP.equals(tfPasswordUser.getText().toString())){
                    lbConfirmePasswordSignUp.setText("Please confirme your password");
                    lbConfirmePasswordSignUp.setTextFill(Color.RED);
                }else if(cP.equals(tfPasswordUser.getText().toString())){
                    lbConfirmePasswordSignUp.setText("Password confirme");
                    lbConfirmePasswordSignUp.setTextFill(Color.GREEN);
                    bConfPass=true;
                }
            }
            
        });
        
    }    

    @FXML
    private void signUpAction(ActionEvent event) throws Exception {
        
        UserService ps = new UserService();
        if( (bName && bEmail) && ( (bAdd && bTel) && (bPass && bConfPass) ) ){
            if(ps.SignUpUser(new User(tfFullNameUser.getText(),tfEmailUser.getText(),tfNumTelUser.getText(),tfFullAddressUser.getText(),tfPasswordUser.getText()))){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("DashboardAdmin.fxml"));
        
                Parent root = loader.load();
                tfFullNameUser.getScene().setRoot(root);
                
              new BounceIn(root).play();


                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("User added successfully!");
                alert.setHeaderText(null);
                alert.setTitle("Succes");
                alert.show();
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("User exist in the database!!");
                alert.setHeaderText(null);
                alert.setTitle("ERROR");
                alert.show();
            }
        
        
        
        }else{
            
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("All information should be valide!");
            alert.setTitle("Information not valide");
            alert.setHeaderText(null);
            alert.show();
            lbFullName.setText("Please enter your full name");
            lbFullName.setTextFill(Color.RED);
            lbEmailSignUp.setText("Please enter your email");
            lbEmailSignUp.setTextFill(Color.RED);
            lbFullAddress.setText("Please enter your address");
            lbFullAddress.setTextFill(Color.RED);
            lbNumTelSignUp.setText("Please enter your phone number");
            lbNumTelSignUp.setTextFill(Color.RED);
            lbPasswordSignUp.setText("Please enter your password");
            lbPasswordSignUp.setTextFill(Color.RED);
            
        }
        
    }
    
}
