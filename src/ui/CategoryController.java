/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import Entite.Category;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import util.DataSource;

/**
 * FXML Controller class
 *
 * @author winxspace
 */
 public class CategoryController implements Initializable {
    Connection con = DataSource.getInstance().getCnx();
    PreparedStatement st = null;
    ResultSet rs = null;

    @FXML
    private Button btnClear;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private TextField tName;

    @FXML
    private TableColumn<Category, Integer> colId;

    @FXML
    private TableColumn<Category, String> colName;

    @FXML
    private TableView<Category> table;
    int id = 0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showCategories();

    }

    public ObservableList<Category> getCategories() {
        ObservableList<Category> categories = FXCollections.observableArrayList();
        String query = "Select* from categorie";
        
        try {
            st = con.prepareStatement(query);
            rs = st.executeQuery();
            while (rs.next()) {
                Category category = new Category();
                category.setIdcat(rs.getInt("idcat"));
                category.setNomcat(rs.getString("nomcat"));
                categories.add(category);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return categories;


    }

    public void showCategories() {
        ObservableList<Category> list = getCategories();
        table.setItems(list);
        colId.setCellValueFactory(new PropertyValueFactory<Category, Integer>("idcat"));
        colName.setCellValueFactory(new PropertyValueFactory<Category, String>("nomcat"));

    }

    @FXML
    void clearCategory(ActionEvent event) {

    }

    @FXML
    void createCategory(ActionEvent event) throws SQLException {
        String insert = "insert into categorie(nomcat) values(?)";
        
        try {
            st = con.prepareStatement(insert);
            st.setString(1, tName.getText());
            st.executeUpdate();
            showCategories();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void getData(MouseEvent event) {
        Category category = table.getSelectionModel().getSelectedItem();
        id = category.getIdcat();
        tName.setText(category.getNomcat());
        btnSave.setDisable(true);

    }

    @FXML
    void deleteCategory(ActionEvent event) throws SQLException {
       
        String delete = "DELETE FROM categorie  where idcat = ?";
        try {
            st = con.prepareStatement(delete);
            st.setInt(1, id);
            st.executeUpdate();
            showCategories();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    void clear() {

    }


    @FXML
    void updateCategory(ActionEvent event) throws SQLException {
        String update = "update categorie set nomcat =? where idcat =?";
        try {
            st = con.prepareStatement(update);
            st.setString(1, tName.getText());
            st.setInt(2, id);
            st.executeUpdate();
            showCategories();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


}
