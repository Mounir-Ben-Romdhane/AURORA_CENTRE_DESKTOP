
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
    
    public static void sendEmail(String s,String obj,String body) {
        

      final String username = "98bfd03fd80444";
      final String password = "bec34e1a01f67f";

      Properties props = new Properties();
      props.put("mail.smtp.auth", "true");
      props.put("mail.smtp.starttls.enable", "true");
      props.put("mail.smtp.host", "sandbox.smtp.mailtrap.io");
      props.put("mail.smtp.port", "2525");

       Session session = Session.getInstance(props,
         new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
               return new PasswordAuthentication(username, password);
            }
         });

      try {
          
          

         Message message = new MimeMessage(session);
         message.setFrom(new InternetAddress(username));
         message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(s));
         message.setSubject(obj);
         message.setText(body);

         Transport.send(message);

         System.out.println("Mail sent successfully");

      } catch (MessagingException e) {
         throw new RuntimeException(e);
      }
    }
    
}