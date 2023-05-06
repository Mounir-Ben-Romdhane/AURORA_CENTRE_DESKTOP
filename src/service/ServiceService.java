package service;

import API.MailerAPI;
import api.MailService;
import entite.Service;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import util.DataSource;

public class ServiceService  implements IService<Service> {
    
    //recevoir et envoyer des donnees
    private Connection conn;
    private DataSource ds = DataSource.getInstance();

    private List<Service> services = new ArrayList<>();    
    
    public ServiceService(){
        conn=DataSource.getInstance().getCnx();
    }
    
    @Override
    public void insert(Service t) {
        
        String requete="insert into service(id,titre_s, description_s, type_s, image, date_s)values('"+t.getId()+"','"+t.getTitreS()+"','"+t.getDescrptionS()+"','"+t.getTypeS()+"','"+t.getImage()+"','"+t.getDateS()+"')";

        try {
                //Envoyer et executer en meme temps la requete
                //Prepared statement est plus rapide vu qu'elle a deja envoyer requete just execute
            Statement ste = conn.createStatement();
            System.out.println("Service ajouté!");

            ste.executeUpdate(requete);
            
        } catch (SQLException ex) {
            Logger.getLogger(Service.class.getName()).log(Level.SEVERE, null, ex);
        }
        //MailService.sendEmail("mfm.maissa@gmail.com");
        UserService us = new UserService();
        
        MailerAPI.sendEmail(us.getCurrentUser().getEmail(),"New Service","Cher Mr/Mme,Le centre AURORA vous informe qu'il ya un nouveau service ajouté, Merci !");
    }
    

    
    
        

    @Override
   public void delete(int id) throws SQLException{
        //String req1 = "DELETE FROM reservation WHERE service_id = " + id;
        String req = "DELETE FROM `service` WHERE id ="+ id;
     
        
        Statement st = ds.getCnx().createStatement();
       // st.executeUpdate(req1);
        st.executeUpdate(req);
    }

  

    @Override
    public void update(Service t)throws SQLException{
        
        String requete="UPDATE service set titre_s='"+t.getTitreS()+"',description_s='"+t.getDescrptionS()+"',type_s='"+t.getTypeS()+"',date_s='"+t.getDateS()+"',image='"+t.getImage()+"' WHERE id="+t.getId();
        Statement st = ds.getCnx().createStatement();
        st.executeUpdate(requete);
    }    

    @Override
    //Query non executeUpdate retourne List d'objets 
    public List<Service> readAll() {
        List<Service> list = new ArrayList<>();
        String requete ="select * from service";

        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(requete);
            
            //rs.getDate("esem fll base")
            
            while (rs.next()) {
                //Soit nom du colonne sinon indice
               java.sql.Date date = rs.getDate("date_s");
                LocalDateTime localDateTime = date.toLocalDate().atStartOfDay();
                list.add(new Service(rs.getInt("id"),rs.getString("titre_s"),rs.getString("description_s"),rs.getString("type_s"),rs.getString("image"),localDateTime))    ;            
            }
        } catch (SQLException ex) {
            Logger.getLogger(ServiceService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    @Override
    public Service readById(int id) {
    
        Service service = null;
        try {

            String req = "SELECT * FROM `service`WHERE id=" + id;
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                java.sql.Date date = rs.getDate("date_s");
                LocalDateTime localDateTime = date.toLocalDate().atStartOfDay();
    
              service = new Service(rs.getInt("id"),rs.getString("titre_s"),rs.getString("description_s"),rs.getString("type_s"),rs.getString("image"),localDateTime);

            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return service;
    }
    
    public Service getServiceByName(String serviceName) {
    Service service = null;
    String sql = "SELECT * FROM service WHERE titre_s=?";
    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setString(1, serviceName);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            java.sql.Date date = rs.getDate("date_s");
            LocalDateTime localDateTime = date.toLocalDate().atStartOfDay();
            service = new Service(rs.getInt("id"),
                    rs.getString("titre_s"),
                    rs.getString("description_s"),
                    rs.getString("type_s"),
                    rs.getString("image"),
                    localDateTime);
        }
    } catch (SQLException ex) {
        Logger.getLogger(ServiceService.class.getName()).log(Level.SEVERE, null, ex);
    }
    return service;
}




}
