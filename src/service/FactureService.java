/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entite.Facture;
import java.sql.Connection;
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
 * @author Samar
 */
public class FactureService implements IServiceO<Facture>{
    
     private Connection cnx;

    public FactureService() {
        cnx = DataSource.getInstance().getCnx();
    }

    @Override
    public void insert(Facture t) throws SQLException {
       try {
            String req = "INSERT INTO `facture` (`commande_id`, `date_facturation`, `est_sup`)"+ "VALUES ("+t.getCommande_id()+", Now(),"+t.isEst_sup()+")";
            System.out.println(req);
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    

    @Override
    public boolean delete(int id) throws SQLException {
        Boolean ok = false;
        try {
            String req = "DELETE FROM `facture` WHERE `id` = " + id;
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            ok = true;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return ok;
    }

   /*@Override
    public List<Facture> recupererFacture() throws SQLException {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

        List<Facture> Facture = new ArrayList<>();
        String s = "select * from `facture`";
        Statement st = cnx.createStatement();
        ResultSet rs = st.executeQuery(s);
        while (rs.next()) {
            Facture e = new Facture();
            e.setCommande_id(rs.getInt("commande_id"));
            e.setDate_facturation(rs.getString("date_facturation"));
            e.isEst_sup(rs.getBoolean("est_sup"));
          
            e.setId(rs.getInt("id"));
            Facture.add(e);

        }
        return Facture;
    }*/
    @Override
    public List<Facture> readAll() throws SQLException {
        List<Facture> Factures = new ArrayList<>();
        String s = "select * from `facture`";
        Statement st = cnx.createStatement();
        ResultSet rs = st.executeQuery(s);
        while (rs.next()) {
            Facture u = new Facture();
            u.setId(rs.getInt("id"));
            u.setCommande_id(rs.getInt("commande_id"));
            u.setDate_facturation(rs.getString("date_facturation"));
            u.setEst_sup(rs.getBoolean("est_sup"));
           
            Factures.add(u);
        }
        return Factures;
    }

    
    @Override
    public boolean update(Facture t) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public Facture readById(int id) throws SQLException {
        Facture u = new Facture();
        String s = "select * from `facture` where `id` = "+id;
        Statement st = cnx.createStatement();
        ResultSet rs = st.executeQuery(s);
        while (rs.next()) {
            u.setId(rs.getInt("id"));
            u.setCommande_id(rs.getInt("commande_id"));
            u.setDate_facturation(rs.getString("date_facturation"));
            u.setEst_sup(rs.getBoolean("est_sup"));
        }
        return u;
    }

 
}
    

