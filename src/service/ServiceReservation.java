/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import api.MailReservation;
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
import util.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author mfmma
 */
public class ServiceReservation implements IService<Reservation>  {

    
    private Connection conn;
    private DataSource ds = DataSource.getInstance();

    
    public ServiceReservation(){
        conn=DataSource.getInstance().getCnx();
    }
    
    
    @Override
    public void insert(Reservation r){
        
    String requete = "INSERT INTO reservation( service_id, numero, date_r, email, numero_tel, user_name, email_connect) VALUES ('" + r.getId_service().getId() + "', '" + r.getNumero() + "', '" + r.getDateR() + "', '" + r.getEmail() + "', '" + r.getNumeroTel() + "', '" + r.getUser_name()+ "', 'SuS')";

        try {
                
            Statement ste = conn.createStatement();
            System.out.println("Reservation ajouté!");

            ste.executeUpdate(requete);
            
        } catch (SQLException ex) {
            Logger.getLogger(Service.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    



@Override
public void delete(int id) throws SQLException {
    System.out.println("Deleting reservation with id: " + id);
    String req = "DELETE FROM reservation WHERE id = ?";
    
       // Reservation reservation = readById(id);

    
    try (PreparedStatement pstmt = conn.prepareStatement(req)) {
        pstmt.setInt(1, id);
        pstmt.executeUpdate();
        System.out.println("Reservation supprimée !");
    } catch (SQLException ex) {
        Logger.getLogger(ServiceReservation.class.getName()).log(Level.SEVERE, null, ex);
    }
    //    MailReservation.sendEmail(reservation);

    
}


    @Override
    public void update(Reservation r) throws SQLException {
        
        String requete="UPDATE reservation set service_id='"+r.getId_service().getId()+"',numero='"+r.getNumero()+"',date_r='"+r.getDateR()+"',email='"+r.getEmail()+"',numero_tel='"+r.getNumeroTel()+"',user_name='"+r.getUser_name()+"' WHERE id="+r.getId();
        Statement st = ds.getCnx().createStatement();
        st.executeUpdate(requete);    }
    
    
    
   public List<Reservation> readAll() {
    List<Reservation> listR = new ArrayList<>();
    List<Service> listS = new ArrayList<>();
    String sql = "SELECT * FROM reservation";
    try {
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(sql);
        ServiceService ps = new ServiceService();
        listS = ps.readAll();
        while (rs.next()) {
            java.sql.Date date = rs.getDate("date_r");
            LocalDateTime localDateTime = date.toLocalDate().atStartOfDay();
            int service_id = rs.getInt("service_id");
            Service service = Service.getServiceById(listS, service_id);
            listR.add(new Reservation(
                    rs.getInt("id"),
                    rs.getInt("numero"),
                    rs.getInt("numero_tel"),
                    rs.getString("email"),
                    rs.getString("user_name"),
                    localDateTime,
                    service,
                    rs.getString("email_connect")));
        }
    } catch (SQLException ex) {
        Logger.getLogger(ServiceService.class.getName()).log(Level.SEVERE, null, ex);
    }
    return listR;
}

@Override
public Reservation readById(int id) {
    Reservation reservation = null;
    try {
        String req = "SELECT * FROM `reservation` WHERE id=?";
        PreparedStatement pstmt = conn.prepareStatement(req);
        pstmt.setInt(1, id);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            java.sql.Date date = rs.getDate("date_r");
            LocalDateTime localDateTime = date.toLocalDate().atStartOfDay();
            String serviceId = rs.getString("service_id");
            ServiceService serviceService = new ServiceService();
            Service service = serviceService.getServiceByName(serviceId);

            reservation = new Reservation(
                    rs.getInt("id"), 
                    rs.getInt("numero"), 
                    rs.getInt("numero_tel"), 
                    rs.getString("email"), 
                    rs.getString("user_name"), 
                    localDateTime, 
                    service,
                    rs.getString("email_connect")
            );
        }
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
    return reservation;
}


//For API STAT
public Map<String, Integer> getReservationCountByService() {
    
    Map<String, Integer> reservationCounts = new HashMap<>();
    
    List<Reservation> reservations = readAll(); // Assuming you have a method to get all reservations
    for (Reservation reservation : reservations) {
        String serviceName = reservation.getId_service().getTitreS();
        int count = reservationCounts.getOrDefault(serviceName, 0);
        reservationCounts.put(serviceName, count + 1);
    }
    return reservationCounts;
}

 /*public int getReservationCount() {
    List<Reservation> reservations = readAll(); // Assuming you have a method to get all reservations
    int count = 0;
    for (Reservation reservation : reservations) {
        count++;
    }
    return count;
}
 
 private Map<String, Integer> getReservations() {
    Map<String, Integer> reservations = new HashMap<>();

    List<Service> services = ServiceService.readAll();
    for (Service service : services) {
        int count = ServiceReservation.getReservationCount();
        reservations.put(service.getTitreS(), count);
    }

    return reservations;
}
*/
    
    }
    
    
    
   
