/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

//import entite.UserDetails;
import API.MailerAPI;
import entite.evenement;
import entite.Participation;
import entite.Reclamation;
import entite.Reponse;
import entite.Reservation;
import entite.Service;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import util.DataSource;

/**
 *
 * @author asus
 */
public class ParticipationService {
    Connection cnx;
    public Statement ste;
    public PreparedStatement pst;

    public ParticipationService() {

        cnx = DataSource.getInstance().getCnx();
    }
    EvenementService ps = new EvenementService();
    
    public List<Participation> readAll() {
    List<Participation> listR = new ArrayList<>();
    List<evenement> listS = new ArrayList<>();
    String sql = "SELECT * FROM participationns";
    try {
        Statement st = cnx.createStatement();
        ResultSet rs = st.executeQuery(sql);
        listS = ps.readAll();
        while (rs.next()) {
            int evenement_id= rs.getInt("evenement_id");
            evenement env = evenement.getServiceById(listS, evenement_id);
            listR.add(new Participation(
                    rs.getInt("id"),
                    rs.getString("description_pn"),
                    rs.getString("usernameev"),
                    rs.getString("emailev"),
                    rs.getInt("numtelev"),
                    env));
        }
    } catch (SQLException ex) {
        Logger.getLogger(ServiceService.class.getName()).log(Level.SEVERE, null, ex);
    }
    return listR;
}

    public void ajouterParticipation(Participation t) {

//        String requete = "INSERT INTO `participationns` (`evenement_id`,`description_pn`,`usernameev`,`emailev`,`numtelev`) VALUES(?,?,?,?,?) ;";

        String requete="insert into participationns(evenement_id,description_pn,usernameev,emailev,numtelev)values('"+t.getEvenement_id().getId()+"','"+t.getDescription_pn()+ "','"+t.getUsernameev()+ "','"+t.getEmailev()+ "','"+t.getNumtelev()+ "')";
        try {
            Statement ste=cnx.createStatement();
            ste.executeUpdate(requete);
            UserService us = new UserService();
        
        MailerAPI.sendEmail(us.getCurrentUser().getEmail(),"New Participation","Cher Mr/Mme,Le centre AURORA vous informe qu'il ya un nouveau participation ajouté, Merci !");
        } catch (SQLException ex) {
            Logger.getLogger(ReclamationService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
     public void delete(int id) {
        String requete="DELETE FROM participationns where id="+id+"";     
            try {
            Statement ste=cnx.createStatement();
            ste.executeUpdate(requete);
        } catch (SQLException ex) {
            Logger.getLogger(ReclamationService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     
     
     
     
     public void update(Participation t, int id) {
        String requete="UPDATE participationns set description_pn='"+t.getDescription_pn()+"',usernameev='"+t.getUsernameev()+"' ,emailev='"+t.getEmailev()+"' ,numtelev='"+t.getNumtelev()+"' WHERE id="+id;
      try {
            Statement ste=cnx.createStatement();
            ste.executeUpdate(requete);
        } catch (SQLException ex) {
            Logger.getLogger(ReclamationService.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
        
    }

    public List<Participation> recupererParticipation() throws SQLException {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

        List<Participation> particip = new ArrayList<>();
        String s = "select * from participationns";
        Statement st = cnx.createStatement();
        ResultSet rs = st.executeQuery(s);
        while (rs.next()) {
            Participation pa = new Participation();
            pa.setId(rs.getInt("id"));
          //  pa.setUser_id(rs.getInt("user_id"));
            pa.setEvenement_id(ps.FetchOneEvent(rs.getInt("evenement_id")));
            pa.setDescription_pn(rs.getString("description_pn"));
            pa.setUsernameev(rs.getString("usernameev"));
            pa.setEmailev(rs.getString("emailev"));
            pa.setNumtelev(rs.getInt("numtelev"));

            particip.add(pa);

        }
        return particip;
    }

   
    
//    public void supprimerParticipation(Participation p) throws SQLException {
//        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//        String req = "delete from participationns where id= ?";
//        PreparedStatement ps = cnx.prepareStatement(req);
//        ps.setInt(1, p.getId());
//        ps.executeUpdate();
//        System.out.println("participationns with id= " + p.getId() + "  is deleted successfully");
//    }
//
//    public Participation FetchOneRes(int id) throws SQLException {
//        Participation r = new Participation();
//        String requete = "SELECT * FROM `participation` where id=" + id;
//
//        try {
//            ste = (Statement) cnx.createStatement();
//            ResultSet rs = ste.executeQuery(requete);
//
//            while (rs.next()) {
//
//                r = new Participation(rs.getInt("evenement_id"),rs.getString("description_pn"), rs.getString("usernameev"), rs.getString("emaileev"), rs.getInt("numtelev"));
//
//                //r = new Participation(rs.getInt("id"), rs.getDate("evenement_id"), rs.getInt("description_pn"), rs.getInt("usernameev"),rs.getString("emaileev"),rs.getString("numtelev"));
//            }
//
//        } catch (SQLException ex) {
//            Logger.getLogger(EvenementService.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return r;
//    }
//
//    public void DeleteParticipation(Participation p) throws SQLException {
//       
//        ParticipationService rs = new ParticipationService();
//
//        Participation r = rs.FetchOneRes(p.getId());
//
//        String requete = "delete from participationns where id=" + p.getId();
//      try{
//            pst = (PreparedStatement) cnx.prepareStatement(requete);
//            //pst.setInt(1, id);
//
//            pst.executeUpdate();
//            System.out.println("participationns with id=" + p.getId() + " is deleted successfully");
//        } catch (SQLException ex) {
//            System.out.println("error in delete participationns " + ex.getMessage());
//        }
//    }
//
//    public void modifierParticipation(Participation p) throws SQLException {
//        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//        String req = "UPDATE participationns SET description_pn = ?,usernameev = ?,emailev=?,numtelev=? where id = ?";
//        PreparedStatement ps = cnx.prepareStatement(req);
////        ps.setInt(1, p.getUser_id());
//        //ps.setInt(2, p.getEvenement_id());
//        ps.setString(1, p.getDescription_pn());
//        ps.setString(2, p.getUsernameev());
//        ps.setString(3, p.getEmailev());
//        ps.setInt(4, p.getNumtelev());
//        ps.setInt(5, p.getId());
//
//        ps.executeUpdate();
//    }
//
//    
//    
//     public Participation FetchOneEvent(int id) {
//        Participation part = new Participation();
//        String requete = "SELECT * FROM `participationns` where evenement_id =" + id;
//
//        try {
//            ste = (Statement) cnx.createStatement();
//            ResultSet rs = ste.executeQuery(requete);
//
//            while (rs.next()) {
//
//               
//                part = new Participation(rs.getInt("evenement_id"),rs.getString("description_pn"), rs.getString("usernameev"), rs.getString("emaileev"), rs.getInt("numtelev"));
//
//
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(EvenementService.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return part;
//    }
//     
//     public Participation chercherParticipation(Participation t) throws SQLException {
//        String s = "select * from participationns where id = "+t.getId();
//        Statement st = cnx.createStatement();
//        ResultSet rs =  st.executeQuery(s);
//        Participation u = new Participation();
//        while(rs.next()){
//            u.setId(rs.getInt("id"));
//            u.setDescription_pn(rs.getString("description_pn"));
//            u.setUsernameev(rs.getString("usernameev"));
//            u.setEmailev(rs.getString("emailev"));
//            u.setNumtelev(rs.getInt("numtelev"));
//            //u.setUser_id(rs.getInt("user_id"));
//            u.setEvenement_id(rs.getInt("evenement_id")); 
//            
//            
//        }
//        return u;
//    }
//     
     
    
//     
//     
//     public ObservableList<participation> rechherchePartByEvent(int id)throws SQLException {
//        ObservableList<participation> participations = FXCollections.observableArrayList();
//        String s = "SELECT * FROM `Participation` where id_evenement =" + id;
//        Statement st = cnx.createStatement();
//        ResultSet rs = st.executeQuery(s);
//        while (rs.next()) {
//            Participation p = new Participation();
//            p.setId((rs.getInt("id")));
//            p.setDate_participation(rs.getDate("date_participation"));
//            p.setUser_id(rs.getInt("user_id"));
//            p.setEvenement_id(rs.getInt("evenement_id")); 
//            p.setDescription_participation("description_participation");
//            participations.add(p);
//        }
//        return participations;
//    }  
//    
}
