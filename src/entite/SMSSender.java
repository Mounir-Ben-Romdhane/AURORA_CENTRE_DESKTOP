/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entite;

import com.twilio.Twilio;
import com.twilio.converter.Promoter;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import java.net.URI;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import util.DataSource;

/**
 *
 * @author Samar
 */
public class SMSSender {
   
    
    Connection cnx ; 
    public SMSSender() {
    cnx = DataSource.getInstance().getCnx(); 
}
  // Find your Account Sid and Token at twilio.com/console
  public static final String ACCOUNT_SID = "AC48385175d901c49e266a82956f1797e0";
  public static final String AUTH_TOKEN = "74ff255a9ea50254ebbbb579b3331879";

  public  void main(String[] args) {
    Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
     PhoneNumber clientPhoneNumber = new PhoneNumber("+21658749090");
        Message message = Message.creator(clientPhoneNumber, new PhoneNumber(""), "Votre commande a été bien reçue").create();
        System.out.println(message.getSid());
  }
  
      public void SMSSENDER(int id) {
    // Remplacez les informations de compte et de numéro de téléphone par les vôtres
    String accountSid = "AC48385175d901c49e266a82956f1797e0";
    String authToken = "74ff255a9ea50254ebbbb579b3331879";
 
      try {
        // Récupérer le numéro de téléphone de l'utilisateur à partir de la base de données
        String clientPhoneNumber = recupererUserPhone(id);
        
        Twilio.init(accountSid, authToken);
        Message message = Message.creator(
            new PhoneNumber("+216"+clientPhoneNumber),
            new PhoneNumber("++16812244118"),
            "Votre commande est confirmée"
        ).create();
        
        System.out.println("SID du message : " + message.getSid());
    } catch (Exception ex) {
        System.out.println("Erreur : " + ex.getMessage());
    }

      }
      
      
      public String recupererUserPhone(int id) throws SQLException {
    try {
        String req = "SELECT num_tel from User WHERE id=? " ;
        PreparedStatement pst = cnx.prepareStatement(req);

        pst.setInt(1, id);
        ResultSet rs = pst.executeQuery();
        
        // Si le numéro de téléphone existe, renvoyer sa valeur
        if (rs.next()) {
            return rs.getString("num_tel");
        } else {
            throw new SQLException("Numéro de téléphone introuvable pour l'utilisateur avec l'ID " + id);
        }
    } catch (SQLException ex) {
        System.out.println("Erreur lors de la récupération du numéro de téléphone de l'utilisateur : " + ex.getMessage());
        throw ex;
    }
}
}