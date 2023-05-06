/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import entite.evenement;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author asus
 */
public class AffichageController implements Initializable {
    int idEvent;

    @FXML
    private Label titreEventLabel;
    @FXML
    private Label typeEventLabel;
    @FXML
    private Label dateEventLabel;
    @FXML
    private Label descriptionEventLabel;
    @FXML
    private ImageView imageview;
    @FXML
    private Button participerEventButton;
    @FXML
    private Button annulerButton;
    @FXML
    private TextField ideventF;
    @FXML
    private TextField iduserF;
    @FXML
    private TextField idPartField;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        ideventF.setVisible(false);
                iduserF.setVisible(false);
               
                annulerButton.setVisible(false);
                idPartField.setVisible(false);
    }    
    private evenement eve=new evenement();

    
    
    public void setEvennement(evenement e) {
        this.eve=e;
        titreEventLabel.setText(e.getTitreev());
        typeEventLabel.setText(e.getTypeev());
        descriptionEventLabel.setText(e.getDescriptionev());
        dateEventLabel.setText(String.valueOf(e.getDateev()));
        ideventF.setText(String.valueOf(e.getId()));
        iduserF.setText(String.valueOf(1));
         String path = e.getImageev();
         File file=new File(path);
         Image img = new Image(file.toURI().toString());
         imageview.setImage(img);

    }
    public void setIdevent(int idevent){
        this.idEvent=idevent;
    }
    
    
    
    
    
    @FXML
    private void participerEvent(ActionEvent event) {
        try {                       
                            
                  FXMLLoader loader = new FXMLLoader(getClass().getResource("Participation.fxml"));
        Parent root = loader.load();
          ParticipationController destController = loader.getController();
//          destController.prodredcu(ps.selectProduitById(ps.getProductIdByName(nomProduirLabel.getText())));
          
         destController.setIdevent(Integer.parseInt(ideventF.getText()));
                    Scene sence = new Scene(root);
                    Stage stage = new Stage();
                    stage.setScene(sence);
                    stage.show();
            
        } catch (IOException ex) {
            System.out.println("error" + ex.getMessage());
        }

//        participation p=new participation(Integer.parseInt(ideventF.getText()),"test description","test user","test email",96854);
//        
//        ps.ajouterParticipation(p);
//
//        
    }
    
}
