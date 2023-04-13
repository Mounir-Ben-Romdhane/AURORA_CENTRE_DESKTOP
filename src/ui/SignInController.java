/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ui;

import animatefx.animation.BounceIn;
import animatefx.animation.FadeIn;
import entite.User;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import service.UserService;


/**
 * FXML Controller class
 *
 * @author MSI
 */
public class SignInController implements Initializable {

    @FXML
    private TextField tfEmailSignIn;
    @FXML
    private PasswordField tfPasswordSignIn;
    @FXML
    private Label lbEmailSignIn;
    @FXML
    private Label lbPasswordSignIn;
    
    private boolean bPass=false,bEmail=false;
    @FXML
    private Button btnSignIn;
    @FXML
    private VBox VBox;
    @FXML
    private ImageView showHidePassword;
    
    private String password;
  
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        
        //COntrole saisie EMail
        lbEmailSignIn.setFont(Font.font("Roboto",FontWeight.BOLD,12));
        
        tfEmailSignIn.onKeyReleasedProperty().set(new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent event) {
                Pattern pattern = Pattern.compile("[a-zA-Z0-9][a-zA-Z0-9._]*@[a-zA-Z0-9._]+([.][a-zA-Z0-9]+)+");
                Matcher match = pattern.matcher(tfEmailSignIn.getText());
                
                int length = tfEmailSignIn.getText().toString().length();
                
                if(length == 0){
                    lbEmailSignIn.setText("Please enter your email");
                    lbEmailSignIn.setTextFill(Color.BLACK);
                }else if(length < 8 ){
                    lbEmailSignIn.setText("Week ( Email should be longer then 8 and should contains '@' , '.' )");
                    lbEmailSignIn.setTextFill(Color.RED);
                }else if(!tfEmailSignIn.getText().toString().contains("@")){
                    lbEmailSignIn.setText("Week ( Email should be longer then 8 and should contains '@' , '.' )");
                    lbEmailSignIn.setTextFill(Color.RED);
                }else if(!tfEmailSignIn.getText().toString().contains(".")){
                    lbEmailSignIn.setText("Week ( Email should be longer then 8 and should contains '@' , '.' )");
                    lbEmailSignIn.setTextFill(Color.RED);
                }
                else{
                    if(match.find() && match.group().equals(tfEmailSignIn.getText()))
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
        
        tfPasswordSignIn.onKeyReleasedProperty().set(new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent event) {
                int length = tfPasswordSignIn.getText().toString().length();
                
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
        
    }   
    
    @FXML
    private void btnSignInAction(ActionEvent event) throws Exception {
        UserService ps = new UserService();
        if(bEmail && bPass){
            if(ps.SignInUser(tfEmailSignIn.getText(), tfPasswordSignIn.getText())=="trueAdmin"){
                            
                            Stage currentStage = (Stage) btnSignIn.getScene().getWindow();
                            currentStage.close();
                
                            Stage stage = new Stage();
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("DashboardAdmin.fxml"));

                            Parent root = loader.load();
                           // tfEmailSignIn.getScene().setRoot(root);

                            btnSignIn.getScene().setFill(Color.TRANSPARENT);
                            stage.setTitle("Dashboard Admin");
                            stage.initStyle(StageStyle.TRANSPARENT);
                            stage.setScene(new Scene(root));
                            stage.show();
                            
                            new BounceIn(root).play();
                            
                            /*
                            
                            tfEmailSignIn.setText("");
                            tfPasswordSignIn.setText("");
                            lbEmailSignIn.setText("Please enter your email");
                            lbEmailSignIn.setTextFill(Color.BLACK);
                            lbPasswordSignIn.setText("Please enter your password");
                            lbPasswordSignIn.setTextFill(Color.BLACK);
                               */
            }else if(ps.SignInUser(tfEmailSignIn.getText(), tfPasswordSignIn.getText())=="trueUser"){
                Stage stage = new Stage();
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("DashboardClient.fxml"));

                            Parent root = loader.load();
                           // tfEmailSignIn.getScene().setRoot(root);

                            btnSignIn.getScene().setFill(Color.TRANSPARENT);
                            stage.setTitle("Dashboard Client");
                            stage.initStyle(StageStyle.TRANSPARENT);
                            stage.setScene(new Scene(root));
                            stage.show();
                           // tfEmailSignIn.setText("");
                            //tfPasswordSignIn.setText("");
            }
            else if(ps.SignInUser(tfEmailSignIn.getText(), tfPasswordSignIn.getText())=="false"){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Password incorrect");
                            alert.setContentText("Provided credentials are incorrect!");
                            alert.setHeaderText(null);
                            alert.show();
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("User not found");
                    alert.setContentText("Provided credentials are incorrect!");
                    alert.setHeaderText(null);
                    alert.show();
            }

        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("All information should be valide!");
            alert.setTitle("Information not valide");
            alert.setHeaderText(null);
            alert.show();
            lbEmailSignIn.setText("Week ( Email should be longer then 8 and should contains '@' , '.' )");
            lbEmailSignIn.setTextFill(Color.RED);
            lbPasswordSignIn.setText("Please enter your password");
            lbPasswordSignIn.setTextFill(Color.RED);
        }
        
      
        
    }


    
    }
    

