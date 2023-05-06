/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import api.MailService;
import entite.Produit;
import entite.Order;
import entite.Service;
import entite.evenement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import util.DataSource;

/**
 *
 * @author Samar
 */
public class ProduitService implements IServiceO<Produit> {

    private Connection cnx;
    public Statement ste;
    public PreparedStatement pst;
    public ProduitService() {
        cnx = DataSource.getInstance().getCnx();
    }

    @Override
    public void insert(Produit e) throws SQLException {
        
        String requete="insert into produit(id,category_id, description, nom, prix, image)values('"+e.getId()+"','"+e.getCategoryName()+"','"+e.getDescription()+"','"+e.getNom()+"','"+e.getPrix()+"','"+e.getImage()+"')";

        try {

            Statement ste = cnx.createStatement();
            System.out.println("Produit ajouté!");

            ste.executeUpdate(requete);
            
        } catch (SQLException ex) {
            Logger.getLogger(Service.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public boolean delete(int id) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean update(Produit t) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

     public List<Produit> recupererEvenement() throws SQLException {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

        List<Produit> Produit = new ArrayList<>();
        String s = "select * from produit";
        Statement st = cnx.createStatement();
        ResultSet rs = st.executeQuery(s);
        while (rs.next()) {
            Produit u = new Produit();
           
            u.setId(rs.getInt("id"));
            u.setNom(rs.getString("nom"));
            u.setPrix(rs.getInt("prix"));
            u.setDescription(rs.getString("description"));
            u.setImage(rs.getString("image"));
            Produit.add(u);

        }
        return Produit;
    }
    
     
     
//        @FXML
//    private void btnMod(ActionEvent event) throws SQLException {
//    
//       
//            
//           String requete = "UPDATE produit SET nom = ?, description = ?, prix = ?,image = ? WHERE id = ?";
//        
//        try {   
//                    PreparedStatement pst=conn.prepareStatement(requete);
//                    pst.setString(1, nom.getText());
//                    pst.setString(2, description.getText());
//                    pst.setString(3, prix.getValue());
//              
//                    pst.setString(5, image.getText());
//                      
//                    pst.executeUpdate();
//                    //System.out.println(titreS.getText());
//                    //System.out.println(dascriptionS.getText());
//                    //System.out.println(typeS.getValue());
//                    //System.out.println(dateS.getValue().toString());
//                    //System.out.println(imageS.getText());
//                    //System.out.println(idservice);
//                    //Close current stage
//                            Stage currentStage = (Stage) dascription.getScene().getWindow();
//                            currentStage.close();
// 
//        } catch (SQLException ex) {
//            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
//        }
       
    
    
    
    
    
    
    
    
    
    
    
    
    @Override
    public List<Produit> readAll() throws SQLException {
        List<Produit> Produits = new ArrayList<>();
        String s = "select * from `produit`";
        Statement st = cnx.createStatement();
        ResultSet rs = st.executeQuery(s);
        while (rs.next()) {
            Produit u = new Produit();
            u.setId(rs.getInt("id"));
            u.setNom(rs.getString("nom"));
            u.setPrix(rs.getInt("prix"));
            u.setDescription(rs.getString("description"));
            u.setImage(rs.getString("image"));
            Produits.add(u);
        }
        return Produits;    
    }
    
    
    
    
    
    
    
    
    
    
    
    
    public Produit readById(int id) throws SQLException {
        Produit u = new Produit();
        String s = "select * from `produit` where `id` = "+id;
        Statement st = cnx.createStatement();
        ResultSet rs = st.executeQuery(s);
        while (rs.next()) {
            u.setId(rs.getInt("id"));
            u.setNom(rs.getString("nom"));
            u.setPrix(rs.getInt("prix"));
            u.setDescription(rs.getString("description"));
            u.setPrix(rs.getInt("prix"));
            u.setImage(rs.getString("image"));
        }
        return u;    
    }

}
