/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ui;

import entite.User;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
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
    
    private boolean bName=false,bEmail=false,bAdd=false,bTel=false;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        tfEmailProfil.addEventFilter(KeyEvent.ANY, event -> {
            event.consume();
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
        //COntrole saisie EMail
        lbEmailProfil.setFont(Font.font("Roboto",FontWeight.BOLD,12));
        
        tfEmailProfil.onKeyReleasedProperty().set(new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent event) {
                Pattern pattern = Pattern.compile("[a-zA-Z0-9][a-zA-Z0-9._]*@[a-zA-Z0-9._]+([.][a-zA-Z0-9]+)+");
                Matcher match = pattern.matcher(tfEmailProfil.getText());
                int length = tfEmailProfil.getText().toString().length();
                
                if(length == 0){
                    lbEmailProfil.setText("Please enter your email");
                    lbEmailProfil.setTextFill(Color.BLACK);
                }else if(length < 8 ){
                    lbEmailProfil.setText("Week ( Email should be longer then 8 and should contains '@' , '.' )");
                    lbEmailProfil.setTextFill(Color.RED);
                }else if(!tfEmailProfil.getText().toString().contains("@")){
                    lbEmailProfil.setText("Week ( Email should be longer then 8 and should contains '@' , '.' )");
                    lbEmailProfil.setTextFill(Color.RED);
                }else if(!tfEmailProfil.getText().toString().contains(".")){
                    lbEmailProfil.setText("Week ( Email should be longer then 8 and should contains '@' , '.' )");
                    lbEmailProfil.setTextFill(Color.RED);
                }
                else{
                    if(match.find() && match.group().equals(tfEmailProfil.getText()))
                    {
                        lbEmailProfil.setText("Valide email");
                        lbEmailProfil.setTextFill(Color.GREEN);
                        bEmail=true;
                    }else {
                        lbEmailProfil.setText("Week ( Email should be longer then 8 and should contains '@' , '.' )");
                         lbEmailProfil.setTextFill(Color.RED);
                        bEmail=false;
                    }
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
        
        loadInfo();
        
    }    

    @FXML
    private void changeProfilAction(ActionEvent event) throws Exception {
        UserService ps = new UserService();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("Confirmation Dialog");
                            alert.setContentText("Are you sure to update this information ?");
                            alert.setHeaderText(null);
                            Optional <ButtonType> action = alert.showAndWait();
                            if(action.get() == ButtonType.OK ){
                                User p = new User(tfFullNameProfil.getText(), tfEmailProfil.getText(), tfNumTelProfil.getText(), tfFullAddressProfil.getText());
                                ps.updateProfil(p);
                                 Alert alertS = new Alert(Alert.AlertType.INFORMATION);
                                alertS.setContentText("Profil updated successfully !!");
                                alertS.setHeaderText(null);
                                alertS.setTitle("Succes");
                                alertS.show();
                                loadInfo();
                            }
               
    }

    private void loadInfo() {
        UserService ps = new UserService();
        User currentUser = ps.getCurrentUser();
        
        
        tfFullNameProfil.setText(currentUser.getUserName());
        tfEmailProfil.setText(currentUser.getEmail());
        tfNumTelProfil.setText(currentUser.getNumTel());
        tfFullAddressProfil.setText(currentUser.getFullAddress());
        
    }
    
}
