/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ui;

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
public class ForgetPassController implements Initializable {

    @FXML
    private VBox ForgetPass;
    @FXML
    private TextField tfEmailForget;
    @FXML
    private Label lbEmailSignIn;
    @FXML
    private PasswordField tfNewPasswordForget;
    @FXML
    private Label lbPasswordSignIn;
    @FXML
    private PasswordField tfConfirmePassForget;
    @FXML
    private Label lbPasswordSignIn1;
    @FXML
    private Button btnForgetPass;
    
    private boolean bPass=false,bConfPass=false,bEmail=false;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        //COntrole saisie EMail
        lbEmailSignIn.setFont(Font.font("Roboto",FontWeight.BOLD,12));
        
        tfEmailForget.onKeyReleasedProperty().set(new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent event) {
                Pattern pattern = Pattern.compile("[a-zA-Z0-9][a-zA-Z0-9._]*@[a-zA-Z0-9._]+([.][a-zA-Z0-9]+)+");
                Matcher match = pattern.matcher(tfEmailForget.getText());
                int length = tfEmailForget.getText().toString().length();
                
                if(length == 0){
                    lbEmailSignIn.setText("Please enter your email");
                    lbEmailSignIn.setTextFill(Color.BLACK);
                }else if(length < 8 ){
                    lbEmailSignIn.setText("Week ( Email should be longer then 8 and should contains '@' , '.' )");
                    lbEmailSignIn.setTextFill(Color.RED);
                }else if(!tfEmailForget.getText().toString().contains("@")){
                    lbEmailSignIn.setText("Week ( Email should be longer then 8 and should contains '@' , '.' )");
                    lbEmailSignIn.setTextFill(Color.RED);
                }else if(!tfEmailForget.getText().toString().contains(".")){
                    lbEmailSignIn.setText("Week ( Email should be longer then 8 and should contains '@' , '.' )");
                    lbEmailSignIn.setTextFill(Color.RED);
                }
                else{
                    if(match.find() && match.group().equals(tfEmailForget.getText()))
                    {
                        lbEmailSignIn.setText("Valide email");
                        lbEmailSignIn.setTextFill(Color.GREEN);
                        bEmail=true;
                    }else {
                        lbEmailSignIn.setText("Week ( Email should be longer then 8 and should contains '@' , '.' )");
                         lbEmailSignIn.setTextFill(Color.RED);
                        bEmail=false;
                    }
                }
            }
            
        });
        
        //CS password
        lbPasswordSignIn.setFont(Font.font("Roboto",FontWeight.BOLD,12));
        
        tfNewPasswordForget.onKeyReleasedProperty().set(new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent event) {
                int length = tfNewPasswordForget.getText().toString().length();
                
                if(length == 0){
                    lbPasswordSignIn.setText("Please enter your password");
                    lbPasswordSignIn.setTextFill(Color.BLACK);
                }else if(length < 5){
                    lbPasswordSignIn.setText("Week");
                    lbPasswordSignIn.setTextFill(Color.RED);
                }else if(length>=5 && length<=28){
                    lbPasswordSignIn.setText("Good");
                    lbPasswordSignIn.setTextFill(Color.GREEN);
                    bPass=true;
                }
            }
            
        });
        //CS confirme password
        lbPasswordSignIn1.setFont(Font.font("Roboto",FontWeight.BOLD,12));
        
        tfConfirmePassForget.onKeyReleasedProperty().set(new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent event) {
                String cP = tfConfirmePassForget.getText().toString();
                
                if(!cP.equals(tfNewPasswordForget.getText().toString())){
                    lbPasswordSignIn1.setText("Please confirme your password");
                    lbPasswordSignIn1.setTextFill(Color.RED);
                }else if(cP.equals(tfNewPasswordForget.getText().toString())){
                    lbPasswordSignIn1.setText("Password confirme");
                    lbPasswordSignIn1.setTextFill(Color.GREEN);
                    bConfPass=true;
                }
            }
            
        });
    }    

    @FXML
    private void btnForgetPassAction(ActionEvent event) throws Exception {
        
        UserService ps = new UserService();
        if( ( bEmail && (bPass && bConfPass) ) ){
            
        if(ps.ForgetPassUser(tfEmailForget.getText(), tfNewPasswordForget.getText())){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Main.fxml"));
            Parent root = loader.load();
            lbEmailSignIn.getScene().setRoot(root);
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("User not found");
                    alert.setContentText("User not found in the database!!!");
                    alert.setHeaderText(null);
                    alert.show();
        }
         
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("All information should be valide!");
            alert.setTitle("Information not valide");
            alert.show();
            lbEmailSignIn.setText("Week ( Email should be longer then 8 and should contains '@' , '.' )");
            lbEmailSignIn.setTextFill(Color.RED);
            lbPasswordSignIn.setText("Please enter your password");
            lbPasswordSignIn.setTextFill(Color.RED);
        }
        
    }
    
}
