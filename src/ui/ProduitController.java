package ui;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import entite.Produit;
import entite.Service;
import entite.User;
import entite.evenement;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.geometry.Insets;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import service.EvenementService;
import service.ProduitService;
import service.ServiceService;
import ui.AfficheServicesController;
import ui.ModifierProduitController;
import ui.ModifierServiceController;
import util.DataSource;


/**
 * FXML Controller class
 *
 * @author winxspace
 */
 public class ProduitController implements Initializable {
        Connection con = DataSource.getInstance().getCnx();
        PreparedStatement st = null;
        ResultSet rs = null;

        
         ProduitService ps= new ProduitService();

        private Button btnSave;



        private TextField tImage;

        private TextField tDescription;

        private TextField tPrice;

        private TextField tName;
        private ChoiceBox<String> myChoiceBox;

        @FXML
        private TableColumn<Produit, String> colDescription;


        @FXML
        private TableColumn<Produit, Integer> colPrice;

        @FXML
        private TableColumn<Produit, String> colName;
        
        int id = 0;
        int idCat = 0;
    @FXML
    private TextField tfSearch;
    @FXML
    private TableColumn<Produit, String> actionsCol;
    @FXML
    private TableView<Produit> tableproduit;
    Produit produit=null;
        
ObservableList<Produit> produitList = FXCollections.observableArrayList();

   

        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {
            // TODO
            
            showProduct();
            System.out.println("aaaaaaaaaaa");
            FilteredList<Produit> filtredData = new FilteredList<>(produitList,e -> true);
            tfSearch.setOnKeyReleased(e -> {
                tfSearch.textProperty().addListener( (observableValue, oldValue, newValue) ->{
                    filtredData.setPredicate((Predicate<? super Produit>) produit ->{
                        if(newValue == null || newValue.isEmpty()){
                            return true;
                        }
                        String lowerCaseFiler = newValue.toLowerCase();
                        if(produit.getNom().contains(lowerCaseFiler)){
                            return true;
                        }
                        return false;
                        
                    });
                });
                SortedList<Produit> sortedData = new SortedList<>(filtredData);
                sortedData.comparatorProperty().bind(tableproduit.comparatorProperty());
                tableproduit.setItems(sortedData);
            });
        }
        
         public void showProduct() {  
    
         try {
              System.out.println("aaaaaaaaaaa");
            // TODO
            List<Produit> Produit = ps.recupererEvenement();
            ObservableList<Produit> olp = FXCollections.observableArrayList(Produit);
            
           // colId.setCellValueFactory(new PropertyValueFactory("id"));
            colName.setCellValueFactory(new PropertyValueFactory("nom"));
            colDescription.setCellValueFactory(new PropertyValueFactory("description"));
            colPrice.setCellValueFactory(new PropertyValueFactory("prix"));
            //colImage.setCellValueFactory(new PropertyValueFactory("image"));
            // colCategory.setCellValueFactory(new PropertyValueFactory("categoryName"));
            
            Callback<TableColumn<Produit, String>, TableCell<Produit, String>> cellFoctory = (TableColumn<Produit, String> param) -> {
            // make cell containing buttons
            final TableCell<Produit, String> cell = new TableCell<Produit, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    //that cell created only on non-empty rows
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {

                        FontAwesomeIconView deleteIcon = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
                        FontAwesomeIconView editIcon = new FontAwesomeIconView(FontAwesomeIcon.EDIT);

                        deleteIcon.setStyle(
                                " -fx-cursor: hand ;"
                                + "-glyph-size:28px;"
                                + "-fx-fill:#ff1744;"
                        );
                        editIcon.setStyle(
                                " -fx-cursor: hand ;"
                                + "-glyph-size:28px;"
                                + "-fx-fill:#00E676;"
                        );
                        deleteIcon.setOnMouseClicked((MouseEvent event) -> {
                           String delete = "DELETE FROM produit  where id = ?";
                            try {
                                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                alert.setTitle("Confirmation Dialog");
                                alert.setHeaderText("Are you sure you want to delete this item?");
                                alert.setContentText("This action cannot be undone.");

                                Optional<ButtonType> result = alert.showAndWait();
                                if (result.get() == ButtonType.OK) {
                                    st = con.prepareStatement(delete);
                                    st.setInt(1, tableproduit.getSelectionModel().getSelectedItem().getId());
                                    st.executeUpdate();
                                    showProduct();
                                    clear();
                                } else {
                                }

                            } catch (SQLException ex) {
                                Logger.getLogger(ProduitController.class.getName())
                                        .log(Level.SEVERE, null, ex);
                            }
                        });
                        editIcon.setOnMouseClicked((MouseEvent event) -> {
                        
                             try {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("ModifierProduit.fxml"));
                            Parent root = loader.load();
                            Stage newStage = new Stage();
                            // Set the title of the new stage
                            newStage.setTitle("ModifierProduit");
                            // Create the scene for the new stage
                            Scene scene = new Scene(root);
                            newStage.setScene(scene);
                            // Show the new stage
                            newStage.show();
                            ModifierProduitController dc = loader.getController();
                            Produit produit = tableproduit.getSelectionModel().getSelectedItem();
                            ProduitService rs=new ProduitService();
                            int id_service = produit.getId(); // get the id_service from the selected Service object
                            dc.setData(id_service); // pass the id_service to the constructor of ModifierServiceController
                            dc.setSelectedCtegorie(produit);
                        } catch (IOException ex) {
                            Logger.getLogger(AfficheServicesController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    });

                        //HBox managebtn = new HBox(editIcon, deleteIcon);
                        HBox managebtn = new HBox(deleteIcon,editIcon);
                        managebtn.setStyle("-fx-alignment:center");
                        HBox.setMargin(deleteIcon, new Insets(2, 2, 0, 3));
                        HBox.setMargin(editIcon, new Insets(2, 3, 0, 2));

                        setGraphic(managebtn);

                        setText(null);

                    }
                }

            };

            return cell;
        };
           actionsCol.setCellFactory(cellFoctory);
            
            tableproduit.setItems(olp);
         
        } catch (SQLException ex) {
            System.out.println("error" + ex.getMessage());
        }
    }//get events
         
         
         
         
         
         
         private ObservableList<Produit> getServiceData() throws SQLException {
    
    ProduitService ps = new ProduitService();
           List<Produit> listp = ps.readAll();
    return FXCollections.observableArrayList(listp);
}   
        
  
        public ObservableList<Produit> getProduct() {
            ObservableList<Produit> products = FXCollections.observableArrayList();
            String query =  "Select * from produit";

            try {
                st = con.prepareStatement(query);
                rs = st.executeQuery();
                while (rs.next()) {
                    Produit product = new Produit();
                    product.setId(rs.getInt("id"));
                    product.setNom(rs.getString("nom"));
                    product.setPrix(rs.getInt("prix"));
                    product.setDescription(rs.getString("description"));
                    product.setImage(rs.getString("image"));
                    product.setCategoryName(rs.getInt("category_id"));
                     //product.setCategoryName(rs.getString("lib_cat"));
                    products.add(product);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return products;
        }
        public void getCategory() {
            ObservableList<String> categoryNameList = FXCollections.observableArrayList();
            String query = "Select * from category";
            
            try {
                st = con.prepareStatement(query);
                rs = st.executeQuery();
                while (rs.next()) {
                    categoryNameList.add(rs.getString("lib_cat"));
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            myChoiceBox.setItems((ObservableList<String>) categoryNameList);

        }
       
        
       
        
       
        
        
       

        void createProduct(ActionEvent event) {

            Window owner = btnSave.getScene().getWindow();
            String insert = "insert into produit(nom,prix,description,image) values(?,?,?,?)";
            
            try {
                st = con.prepareStatement(insert);
                st.setString(1, tName.getText());
                st.setInt(2, parseInt(tPrice.getText()));
                st.setString(3, tDescription.getText());
                st.setString(4, tImage.getText());
                //st.setInt(5, myChoiceBox.getValue());
                st.executeUpdate();
                if (tName.getText().isEmpty() || tPrice.getText().isEmpty() || tDescription.getText().isEmpty() || tImage.getText().isEmpty()) {
                    showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                            "Please fill in all fields");
                    return;
                }
                clear();
                showProduct();
            } catch (SQLException ex) {
                Logger.getLogger(ProduitController.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
        }
        
        void deleteProduct(ActionEvent event) {
            String delete = "DELETE FROM produit  where id = ?";
            try {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Dialog");
                alert.setHeaderText("Are you sure you want to delete this item?");
                alert.setContentText("This action cannot be undone.");
                
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    produit = tableproduit.getSelectionModel().getSelectedItem();
                    st = con.prepareStatement(delete);
                    st.setInt(1, produit.getId());
                    st.executeUpdate();
                    showProduct();
                    clear();
                } else {
                }

            } catch (SQLException ex) {
                Logger.getLogger(ProduitController.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
        }

        void updateProduct(ActionEvent event) {
            String update = "update produit set name =?,prix = ?,description =?,image =? where id =?";
            try {
                st = con.prepareStatement(update);
                st.setString(1, tName.getText());
                st.setInt(2, parseInt(tPrice.getText()));
                st.setString(3, tDescription.getText());
                st.setString(4, tImage.getText());
                st.setInt(5, id);
                st.executeUpdate();
                showProduct();
                clear();
            } catch (SQLException ex) {
                Logger.getLogger(ProduitController.class.getName())
                        .log(Level.SEVERE, null, ex);
            }

        }

        void clear() {
            tName.setText(null);
            tPrice.setText(null);
            tDescription.setText(null);
            tImage.setText(null);
            btnSave.setDisable(false);
        }

        void clearProduct(ActionEvent event) {
            clear();
        }

        private static void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
            Alert alert = new Alert(alertType);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.initOwner(owner);
            alert.show();
        }

        @FXML
        void getData(MouseEvent event) {
            Produit product = tableproduit.getSelectionModel().getSelectedItem();
            id = product.getId();
            tName.setText(product.getNom());
            tPrice.setText(String.valueOf(product.getPrix()));
            tDescription.setText(product.getDescription());
            tImage.setText(product.getImage());
//            myChoiceBox.setValue(product.getCategoryName());
            btnSave.setDisable(true);

        }
//        private String getCategoryName(int categoryId) {
//            String selectCat = "select lib_cat from category where id=?";
//            try {
//                PreparedStatement pst = con.prepareStatement(selectCat);
//                pst.setInt(1, categoryId);
//                rs = pst.executeQuery();
//                String nomCat = "";
//                while (rs.next()) {
//                    nomCat = rs.getString("lib_cat");
//                }
//                return nomCat;
//            } catch (SQLException ex) {
//                Logger.getLogger(ProduitController.class.getName())
//                        .log(Level.SEVERE, null, ex);
//                return "";
//            }
//        }
//
//        private int getCategoryId(String categoryName) {
//            String selectCat = "select id from category where lib_cat=?";
//            try {
//                PreparedStatement pst = con.prepareStatement(selectCat);
//                pst.setString(1, categoryName);
//                rs = pst.executeQuery();
//                while (rs.next()) {
//                    idCat = rs.getInt("id");
//                }
//                return idCat;
//            } catch (SQLException ex) {
//                Logger.getLogger(ProduitController.class.getName())
//                        .log(Level.SEVERE, null, ex);
//                return 0;
//            }
//        }
//        public void editCat(ActionEvent event) throws IOException {
//            Parent parent = FXMLLoader.load(getClass().getResource("Category.fxml"));
//            Scene scene = new Scene(parent);
//            Stage stage=new Stage();
//            stage.setTitle("CRUD");
//            stage.setScene(scene);
//            stage.show();
//
//        }



    }
