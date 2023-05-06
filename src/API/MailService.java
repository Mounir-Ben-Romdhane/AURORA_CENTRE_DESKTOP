
package api;

import service.ServiceReservation;
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



public class MailService {
    public static void sendEmail(String s) {
        
/*
      final String username = "pidevDesktop@gmail.com";
      final String password = "";

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
         message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(s));
         message.setSubject("Canceling the event");
         message.setText("cher Mr/Mme,"
            + "\n\n nous somme vraiment désolé de vous informer que votre réservation"+" "+ " "+"est annulée merci pour votre compréhension ");

         Transport.send(message);

         System.out.println("Mail sent successfully");

      } catch (MessagingException e) {
         throw new RuntimeException(e);
      }*/

 String html = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "\t<title>Ma page HTML colorée et décorée</title>\n" +
                "\t<style>\n" +
                "\t\tbody {\n" +
                "\t\t\tbackground-color: lightblue;\n" +
                "\t\t\tfont-family: Arial, sans-serif;\n" +
                "\t\t\tfont-size: 14px;\n" +
                "\t\t\tcolor: white;\n" +
                "\t\t\tmargin: 0;\n" +
                "\t\t\tpadding: 0;\n" +
                "\t\t}\n" +
                "\t\th1 {\n" +
                "\t\t\tfont-size: 36px;\n" +
                "\t\t\tcolor: #ff1a75;\n" +
                "\t\t\tmargin: 20px;\n" +
                "\t\t\tpadding: 10px;\n" +
                "\t\t\tborder: 3px solid darkblue;\n" +
                "\t\t\tbackground-color: white;\n" +
                "\t\t\ttext-align: center;\n" +
                "\t\t}\n" +
                "\t\tp {\n" +
                "\t\t\tmargin: 20px;\n" +
                "\t\t\tpadding: 10px;\n" +
                "\t\t\tborder: 3px solid darkblue;\n" +
                "\t\t\tbackground-color: white;\n" +
                "\t\t\tcolor: black;\n" +

                "\t\t}\n" +
                "\t</style>\n" +
                "</head>\n" +
                "<body>\n" +
                "\t<h1>Ajout Service</h1>\n" +
                "<p>Cher Mr/Mme,Le centre AURORA vous informe qu'il ya un nouveau service ajouté, Merci !</p>" +
              //  "<h2>Ceci est un autre paragraphe de texte.</h2>" +
                "</body>\n" +
                "</html>\n";


        //String from = "pidevdesktop@gmail.com";
        String to = "mfm.maissa@gmail.com";
        String username = "pidevdesktop@gmail.com";
        String password = "jaabofxqjclbslgv";



        System.out.println("Preparing to send email");
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.socketFactory.port", 465);
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", 465);


        Session session = Session.getInstance(properties, new Authenticator(){
            @Override
            protected PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication(username,password);
            }
        });

        try {
            // create MimeMessage object
            MimeMessage message = new MimeMessage(session);

            // set from and to address
            message.setFrom(new InternetAddress(s));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            // set subject and message text
            message.setSubject("This is the subject");

            message.setContent(html, "text/html");

            // send message
            Transport.send(message);
            System.out.println("Email message sent successfully.");

        } catch (MessagingException mex) {
            mex.printStackTrace();
        }

    }


   }
    
    
