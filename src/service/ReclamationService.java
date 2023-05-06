/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import API.MailerAPI;
import entite.Reclamation;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.DataSource;

/**
 *
 * @author malek
 */
public class ReclamationService implements Iservices<Reclamation>{
    private Connection conn;
    public ReclamationService() {
        conn=DataSource.getInstance().getCnx();
    }

    @Override
    public void insert(Reclamation r) {
         if(r.getDate_reclamation() == null) {
    System.out.println("Error: date is null");
   
}
        String requete="insert into reclamation(type,nom,description,status,date_reclamation,email_connecte,email_reclamation)values('"+r.getType()+"','"+r.getNom()+ "','"+r.getDescription()+ "','"+r.getStatus()+ "','"+r.getDate_reclamation()+ "','"+r.getEmail_connecte()+ "','"+r.getEmail_reclamation()+"')";
        try {
            Statement ste=conn.createStatement();
            ste.executeUpdate(requete);
            UserService us = new UserService();
        
        MailerAPI.sendEmail(us.getCurrentUser().getEmail(),"New Service","Cher Mr/Mme,Le centre AURORA vous informe qu'il ya un nouveau reclamation ajouté, Merci !");
        } catch (SQLException ex) {
            Logger.getLogger(ReclamationService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }

    @Override
    public void delete(int id) {
       String requete1 = "DELETE FROM reponse WHERE reclamation_id = " + id;
            String requete="DELETE FROM reclamation where id="+id+"";     
            try {
            Statement ste=conn.createStatement();
            ste.executeUpdate(requete1);
            ste.executeUpdate(requete);
        } catch (SQLException ex) {
            Logger.getLogger(ReclamationService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update(Reclamation t, int id) {
        String requete="UPDATE reclamation set type='"+t.getType()+"',nom='"+t.getNom()+"',description='"+t.getDescription()+"',status='"+t.getStatus()+"',date_reclamation='"+t.getDate_reclamation()+"',email_connecte='"+t.getEmail_connecte()+"',email_reclamation='"+t.getEmail_reclamation()+"' WHERE id="+id;
      try {
            Statement ste=conn.createStatement();
            ste.executeUpdate(requete);
        } catch (SQLException ex) {
            Logger.getLogger(ReclamationService.class.getName()).log(Level.SEVERE, null, ex);
        }       
    }

    @Override
    public List<Reclamation> readAll() {
       List<Reclamation> list=new ArrayList<>();
       String sql="select * from reclamation";
        try {
            Statement st=conn.createStatement();
            ResultSet re= st.executeQuery(sql);
            while(re.next()){
               java.sql.Date date = re.getDate("date_reclamation");
LocalDateTime localDateTime = date.toLocalDate().atStartOfDay();
              list.add(new Reclamation(re.getInt("id"), re.getString("type"), re.getString("nom"),re.getString("description"),re.getInt("status"),
                      localDateTime,re.getString("email_connecte"),re.getString("email_reclamation")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ReclamationService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;     
    }

    @Override
    public Reclamation readById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    
}
