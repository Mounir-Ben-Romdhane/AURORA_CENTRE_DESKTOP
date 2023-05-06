package api;

import entite.Reservation;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Class for sending email notifications.
 */
public class MailReservation {
    
    public static void sendEmail(Reservation reservation) throws MessagingException {

        final String username = "pidevDesktop@gmail.com";
        final String password = "zJ85sJmQhDy873";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(reservation.getEmail()));
            message.setSubject("Reservation Confirmation");
            message.setText("Dear " + reservation.getId_service() + ",\n\n"
                    + "Your reservation for " + reservation.getId() + " on "
                    + reservation.getDateR() + " has been confirmed.\n\n"
                    + "Thank you for choosing our service.\n\n"
                    + "Best regards,\n"
                    + "Reservation team");

            Transport.send(message);

            System.out.println("Reservation confirmation email sent successfully to " + reservation.getEmail());

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}


/*
package api;

import service.ServiceReservation;
import entities.Reservation;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.Authenticator;



public class MailReservation {
    public static void sendEmail() {
        

      final String username = "pidevDesktop@gmail.com";
      final String password = "zJ85sJmQhDy873";

      Properties props = new Properties();
      props.put("mail.smtp.auth", "true");
      props.put("mail.smtp.starttls.enable", "true");
      props.put("mail.smtp.host", "smtp.gmail.com");
      props.put("mail.smtp.port", "587");

       Session session = Session.getInstance(props,
         new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
               return new PasswordAuthentication(username, password);
            }
         });

      try {
          
          

         Message message = new MimeMessage(session);
         message.setFrom(new InternetAddress(username));
         message.setRecipients(Message.RecipientType.TO,InternetAddress.parse("mfm.maissa@gmail.com"));
         message.setSubject("Canceling the event");
         message.setText("cher Mr/Mme,"
            + "\n\n nous somme vraiment désolé de vous informer que votre réservation"+" "+ " "+"est annulée merci pour votre compréhension ");

         Transport.send(message);

         System.out.println("Mail sent successfully");

      } catch (MessagingException e) {
         throw new RuntimeException(e);
      }
   }
} 
*/