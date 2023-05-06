
package pidevdesktop;
import api.MailReservation;
import static api.MailReservation.sendEmail;
import entite.Reservation;
import entite.Service;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.mail.MessagingException;
import service.ServiceReservation;

import service.ServiceService;
import util.DataSource;

/**
 *
 * @author mfmma
 */
public class PidevDesktop {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        

        DataSource ds1 = DataSource.getInstance();
        System.err.println(ds1);
        DataSource ds2 = DataSource.getInstance();
        System.err.println(ds2);
        DataSource ds3 = DataSource.getInstance();
        System.err.println(ds3);
        
        
        LocalDateTime dateS = LocalDateTime.now();
        
        Service p1 = new Service("mm","Sy","test","test",dateS);
        Service p2 = new Service("ok","ok","test","test",dateS);


        
        ServiceService ps =new ServiceService();

       //ps.insert(p2);
      
   
       System.out.println("Affichage avant la suppression");
        for (Service service : ps.readAll()) {
        System.out.println(service);
    }
 //        try {
//            
       //    ps.update(p1,5536);
           //ps.delete(5543);
             
//        } catch (SQLException ex) {
  //          System.out.println(ex.getMessage());
  //      }
    
        
        System.out.println("//////////////////////////");
        System.out.println("Affichage aprés la suppression");
            for (Service service : ps.readAll()) {
        System.out.println(service);
        
      
    }
        System.out.println("Affichage par id :");
        Service p3 = ps.readById(1);

        System.out.println(ps.readById(1));
                
                
        System.out.println("Reservation");
        
        LocalDateTime dateR = LocalDateTime.now();
        ServiceReservation sr = new ServiceReservation();

       // Reservation r1 =  new Reservation(4,123456,"maissa","mm",dateR,p1);

        //Ajout
   //a    sr.insert(r1);

       // try {
            
        //Suppression
     //   sr.delete(70);
        //Modif
      //  sr.update(r1, 73);
             
    //    } catch (SQLException ex) {
     //       System.out.println(ex.getMessage());
     //   }
    
       
        
        System.out.println("Affichage :");
        System.out.println(sr.readAll());
        System.out.println("Affichage par id :");
       // System.out.println(sr.readById(70));
       
// Creating an instance of Service
int id = 1;
String titreS = "Cleaning Service";
String descrptionS = "Professional cleaning services for homes and offices";
String typeS = "Home Services";
String image = "cleaning.jpg";
Service service = new Service(id, titreS, descrptionS, typeS, image, dateS);




  // Example usage:
   
      //  sendEmail(r1);
   
    }}
         
            
            


    
    
