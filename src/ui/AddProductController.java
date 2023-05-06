/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ui;

import entite.Produit;
import entite.Service;
import java.io.File;
import java.io.IOException;
import static java.lang.Integer.parseInt;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javax.swing.JOptionPane;
import service.ProduitService;
import service.ServiceService;
import util.DataSource;

/**
 * FXML Controller class
 *
 * @author MSI
 */
public class AddProductController implements Initializable {
    
     Connection con = DataSource.getInstance().getCnx();
        PreparedStatement st = null;
        ResultSet rs = null;

    @FXML
    private TextField tName;
    @FXML
    private TextField tDescription;
    @FXML
    private ChoiceBox<String> myChoiceBox;
    @FXML
    private TextField tImage;
    @FXML
    private Button btnImage;
    @FXML
    private ImageView imageView;
    @FXML
    private TextField tPrice;
    @FXML
    private Button btnAjoutP;

    /**
     * Initializes the controller class.
     */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        getCategory();
    }    

    @FXML
    private void chooseFile(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(btnImage.getScene().getWindow());
        if (selectedFile != null) {
            String fileName = selectedFile.getName();
            Path sourcePath = selectedFile.toPath();
            Path destinationPath = Paths.get("src", "image", fileName);
            try {
                Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("File copied to: " + destinationPath.toAbsolutePath());
                tImage.setText(fileName);
                Image image = new Image(selectedFile.toURI().toString());
                imageView.setImage(image);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void AjouProduit(ActionEvent event) throws SQLException {
        String nom = tName.getText();
    String description = tDescription.getText();
    int prix = Integer.parseInt(tPrice.getText());
    
    String image = tImage.getText();

    if (nom.isEmpty() || description.isEmpty()||  image.isEmpty()) {
        JOptionPane.showMessageDialog(null, "Please fill all the fields!");
        return;
    }

    
      

    Produit nouveauProduit = new Produit( nom, description, image, prix,6);
    ProduitService ser = new ProduitService();
    ser.insert(nouveauProduit);

    JOptionPane.showMessageDialog(null, "produit added successfully!");
    }

    public void getCategory() {
            ObservableList<String> categoryNameList = FXCollections.observableArrayList();
            String query = "Select * from category";
            
            try {
                st = con.prepareStatement(query);
                rs = st.executeQuery();
                while (rs.next()) {
                    categoryNameList.add(rs.getString("id"));
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            myChoiceBox.setItems((ObservableList<String>) categoryNameList);

        }
    
    
}
