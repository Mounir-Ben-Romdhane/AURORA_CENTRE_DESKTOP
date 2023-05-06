/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import entite.Reclamation;
import entite.Reponse;
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
public class ReponseService implements Iservices<Reponse>{
     private Connection conn;
    public ReponseService() {
        conn=DataSource.getInstance().getCnx();
    }

    @Override
    public void insert(Reponse t) {
        if(t.getDate_reponse() == null) {
    System.out.println("Error: date is null");
   
}
        System.out.println("---------------------------");
       System.out.println(t.getReclamation().getId());
        System.out.println("---------------------------");
        String requete="insert into reponse(reclamation_id,reponse,date_reponse)values('"+t.getReclamation().getId()+"','"+t.getResponse()+ "','"+t.getDate_reponse()+ "')";
        try {
            Statement ste=conn.createStatement();
            ste.executeUpdate(requete);
        } catch (SQLException ex) {
            Logger.getLogger(ReclamationService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void delete(int id) {
        String requete="DELETE FROM reponse where id="+id+"";     
            try {
            Statement ste=conn.createStatement();
            ste.executeUpdate(requete);
        } catch (SQLException ex) {
            Logger.getLogger(ReclamationService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update(Reponse t, int id) {
        String requete="UPDATE reponse set reponse='"+t.getResponse()+"',date_reponse='"+t.getDate_reponse()+"' WHERE id="+id;
      try {
            Statement ste=conn.createStatement();
            ste.executeUpdate(requete);
        } catch (SQLException ex) {
            Logger.getLogger(ReclamationService.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
        
    }

    @Override
    public List<Reponse> readAll() {
        List<Reponse> list=new ArrayList<>();
        List<Reclamation> listreclamation=new ArrayList<>();
       String sql="select * from reponse";
        try {
            Statement st=conn.createStatement();
            ResultSet re= st.executeQuery(sql);
            ReclamationService ps=new ReclamationService();
            listreclamation=ps.readAll();
            while(re.next()){
               java.sql.Date date = re.getDate("date_reponse");
            LocalDateTime localDateTime = date.toLocalDate().atStartOfDay();
          int reclamationId = re.getInt("reclamation_id");
          
                Reclamation reclamation = Reclamation.getReclamationById(listreclamation, reclamationId);
            list.add(new Reponse(re.getInt("id"), reclamation, re.getString("reponse"), localDateTime));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ReclamationService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    @Override
    public Reponse readById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
