/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entite.User;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JOptionPane;
import org.mindrot.jbcrypt.BCrypt;
import util.DataSource;

/**
 *
 * @author wiemhjiri
 */
public class UserService implements IService<User> {

    private Connection conn;
    
    @FXML
    private TextField hide;
    
    
    
    
    public UserService() {
        conn = DataSource.getInstance().getCnx();
        
    }
    
    
    
    public Boolean SignUpUser(User p)  throws Exception{
        PreparedStatement preparedStatement1 = null;
        ResultSet resultSet1 = null;
        boolean res1 = false;
        String requete = "insert into user(username,email,num_tel,password,full_address)"
                + " values (?,?,?,?,?)";
        try {
            
            preparedStatement1 = conn.prepareStatement("SELECT email FROM user WHERE email = ?");
            preparedStatement1.setString(1, p.getEmail());
            resultSet1 = preparedStatement1.executeQuery();
            
            if(!resultSet1.isBeforeFirst()){
                String hashedPassword = BCrypt.hashpw(p.getPassword(), BCrypt.gensalt());
                    PreparedStatement pst=conn.prepareStatement(requete);
                    pst.setString(1, p.getUserName());
                    pst.setString(2, p.getEmail());
                    pst.setString(3, p.getNumTel());
                    pst.setString(4, hashedPassword);
                    pst.setString(5, p.getFullAddress());
                    pst.executeUpdate();
                    res1=true;
            }else{
                while (resultSet1.next()) {
                    
                    System.out.println("Email exist in the database!!");
                
                     res1=false;
                }
            }
            
 
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res1;
             
    }
    
    public String SignInUser(String email , String password) throws Exception{
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String res = "false";
        try {
            preparedStatement = conn.prepareStatement("SELECT password , email FROM user WHERE email = ?");
            preparedStatement.setString(1, email);
            resultSet = preparedStatement.executeQuery();
            
            if(!resultSet.isBeforeFirst()){
                System.out.println("User not found in the database!!");
                
                res="0";
            }else{
                while (resultSet.next()) {
                   String retrievedPassword = resultSet.getString("password");
                   
                    if(BCrypt.checkpw(password, retrievedPassword)){
                        System.out.println("Login succes!");
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setContentText("User Login succesfully!");
                        alert.setTitle("Succes");
                        alert.setHeaderText(null);
                        alert.show();
                        res = "true";
                        break;
                    }
                    if(!retrievedPassword.equals(password)){
                        System.out.println("Password did not match");
                        res = "false";
                    }
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
        
    }
    
    public Boolean ForgetPassUser(String email , String password) throws Exception{
        
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        boolean res = false;
        try {
            
            preparedStatement = conn.prepareStatement("SELECT password , email FROM user WHERE email = ?");
            preparedStatement.setString(1, email);
            resultSet = preparedStatement.executeQuery();
            
            if(!resultSet.isBeforeFirst()){
                System.out.println("User not found in the database!!");
                
                res=false;
            }else{
            
            String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
            String query = "UPDATE user SET password = '" + hashedPassword + "' WHERE email = '" + email + "'";
            PreparedStatement pst=conn.prepareStatement(query);
            pst.executeUpdate();
            System.out.println("Login succes!");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Password changed successfully !!");
            alert.setHeaderText(null);
            alert.setTitle("Succes");
            alert.show();
            res=true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public void insert(User t) {
        String requete = "insert into personne(nom,prenom,age)"
                + "values('" + t.getUserName()+ "','" + t.getEmail()
                + "'," + t.getFullAddress()+ ")";
        try {
            Statement ste = conn.createStatement();
            ste.executeUpdate(requete);
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void insertPst(User p) {
        String requete = "insert into user(username,email,num_tel,password,full_address)"
                + " values (?,?,?,?,?)";
        try {
            PreparedStatement pst=conn.prepareStatement(requete);
            pst.setString(1, p.getUserName());
            pst.setString(2, p.getEmail());
            pst.setString(3, p.getNumTel());
            pst.setString(4, p.getPassword());
            pst.setString(5, p.getFullAddress());
            pst.executeUpdate();
 
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
             
    }

    @Override
    public void delete(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(User t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<User> readAll() {
        List<User> list=new ArrayList<>();
        String requete="select * from personne";
        try {
            Statement st=conn.createStatement();
            ResultSet rs= st.executeQuery(requete);
            while(rs.next()){
                //list.add(new User(rs.getInt("id"),
                  //      rs.getString(2), rs.getString("prenom"), 
                    //    rs.getInt(4)));
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    @Override
    public User readById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    
    
}
