/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import API.MailerAPI;
import entite.evenement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.DataSource;

/**
 *
 * @author asus
 */
public class EvenementService implements IService<evenement>{
    
    Connection cnx;
    public Statement ste;
    public PreparedStatement pst;

    public EvenementService() {
        cnx = DataSource.getInstance().getCnx();

    }

   
    public void ajouterEvenement(evenement e) throws SQLException {

        String requete = "INSERT INTO `evenement` (`titreev`,`descriptionev`,`imageev`,`dateev`,`typeev`,`color`) "
                + "VALUES (?,?,?,?,?,?);";
        try {
            pst = (PreparedStatement) cnx.prepareStatement(requete);
            pst.setString(1, e.getTitreev());
            pst.setString(2, e.getDescriptionev());
            pst.setString(3, e.getImageev());           
            pst.setDate(4, e.getDateev());
            pst.setString(5, e.getTypeev());
            pst.setString(6, e.getColor());
            pst.executeUpdate();
            System.out.println("event " + e.getTitreev() + " added successfully");
            UserService us = new UserService();
        
        MailerAPI.sendEmail(us.getCurrentUser().getEmail(),"New Event","Cher Mr/Mme,Le centre AURORA vous informe qu'il ya un nouveau evenement ajouté, Merci !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    
    public void modifierEvenement(evenement e) throws SQLException {
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        String req = "UPDATE evenement SET titreev = ?,descriptionev = ?,imageev=?,dateev=?,typeev=?,color=? where id = ?";
        PreparedStatement pst = cnx.prepareStatement(req);
        pst.setString(1, e.getTitreev());
            pst.setString(2, e.getDescriptionev());
            pst.setString(3, e.getImageev());           
            pst.setDate(4, e.getDateev());
           pst.setString(5, e.getTypeev());
        pst.setString(6, e.getColor());
        pst.setInt(7, e.getId());
        pst.executeUpdate();
    }

    
    public void supprimerEvenement(evenement e) throws SQLException {
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        String req = "delete from evenement where id= ?";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setInt(1, e.getId());
        ps.executeUpdate();
        System.out.println("event with id= " + e.getId()+ "  is deleted successfully");
    }

    
    public List<evenement> recupererEvenement() throws SQLException {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

        List<evenement> evenements = new ArrayList<>();
        String s = "select * from evenement";
        Statement st = cnx.createStatement();
        ResultSet rs = st.executeQuery(s);
        while (rs.next()) {
            evenement e = new evenement();
            e.setTitreev(rs.getString("titreev"));
            e.setDescriptionev(rs.getString("descriptionev"));
            e.setImageev(rs.getString("imageev"));
            e.setDateev(rs.getDate("dateev"));
             e.setTypeev(rs.getString("typeev"));
            e.setColor(rs.getString("color"));
            e.setId(rs.getInt("id"));
            evenements.add(e);

        }
        return evenements;
    }

    public String GenerateQrEvent(evenement e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    

   
 public evenement FetchOneEvent(int id) {
        evenement event = new evenement();
        String requete = "SELECT * FROM `evenement` where id=" + id;

        try {
            ste = (Statement) cnx.createStatement();
            ResultSet rs = ste.executeQuery(requete);

            while (rs.next()) {
                event = new evenement(rs.getInt("id"),rs.getString("titreev"), rs.getString("descriptionev"), rs.getString("imageev"), rs.getDate("dateev"), rs.getString("typeev"),rs.getString("color"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(EvenementService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return event;
    }

    @Override
    public void insert(evenement t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(evenement t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<evenement> readAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public evenement readById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
   
   
    
}
