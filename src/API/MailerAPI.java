
package API;


import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.Authenticator;



public class MailerAPI {
    public static void sendEmail(String s) {
        

      final String username = "pidevDesktop@gmail.com";
      final String password = "jaabofxqjclbslgv";

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
         message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(s,false));
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