/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import API.MailerAPI;
import animatefx.animation.BounceIn;
import entite.User;
import java.io.File;
import java.io.FileInputStream;
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
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
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
    
   public User userConnect;
   
    
    
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
                    MailerAPI.sendEmail(p.getEmail().toString());
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
            preparedStatement = conn.prepareStatement("SELECT * FROM user WHERE email = ?");
            preparedStatement.setString(1, email);
            resultSet = preparedStatement.executeQuery();
            
            if(!resultSet.isBeforeFirst()){
                System.out.println("User not found in the database!!");
                
                res="0";
            }else{
                while (resultSet.next()) {
                   String retrievedPassword = resultSet.getString("password");
                   
                    if(BCrypt.checkpw(password, retrievedPassword)){
                        
                        if(resultSet.getBoolean("etat")){
                        
                            if(resultSet.getString("roles").equals("[\"ROLE_ADMIN\"]")){
                                System.out.println("ADMIN");
                                res = "trueAdmin";
                                userConnect = new User(resultSet.getString("username"),resultSet.getString("email"),
                                resultSet.getString("num_tel"),resultSet.getString("full_address"));
                                
                                userConnect.setCurrent_User(userConnect);
                                System.out.println(userConnect.toString());
                                break;
                            }else if(resultSet.getString("roles").equals("[\"ROLE_USER\"]")){
                                System.out.println("USER");
                                res = "trueUser";
                                userConnect = new User(resultSet.getString("username"),resultSet.getString("email"),
                                resultSet.getString("num_tel"),resultSet.getString("full_address"));
                                
                                userConnect.setCurrent_User(userConnect);
                                System.out.println(userConnect.toString());
                                break;
                            }
                        }else{
                            res="blocked";
                        }
                        
                        
                        break;
                    }
                    if(!retrievedPassword.equals(password)){
                        System.out.println(""+retrievedPassword);
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
            //System.out.println("Login succes!");
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

   
    
    public User getCurrentUser(){
        //System.out.println("Mail user connected : "+userConnect.getCurrent_User());
        return userConnect.getCurrent_User();
    }
    
    
    public void updateProfil(User p,FileInputStream fis,File file)  throws Exception{
        String requete = "UPDATE user SET username = ?, email = ?, num_tel = ?, full_address = ?, imagee = ? WHERE email = ?";
        
        try {   
                    PreparedStatement pst=conn.prepareStatement(requete);
                    pst.setString(1, p.getUserName());
                    pst.setString(2, p.getEmail());
                    pst.setString(3, p.getNumTel());
                    pst.setString(4, p.getFullAddress());
                    pst.setBinaryStream(5,fis,file.length());
                    pst.setString(6, p.getEmail());
                    pst.executeUpdate();
                    userConnect.setCurrent_User(p);
 
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
             
    }
    
    
    public void resetPassword(String newPass,String email)  throws Exception{
        try {   
            String hashedNewPassword = BCrypt.hashpw(newPass, BCrypt.gensalt());
            String query = "UPDATE user SET password = '" + hashedNewPassword + "' WHERE email = '" + email + "'";
            PreparedStatement pst=conn.prepareStatement(query);
            pst.executeUpdate();
 
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
             
    }
    
    
    public void rememberMe(String email,int rememberChecked)  throws Exception{
        
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        boolean res = false;
        String requete = "insert into remember(email,remember)"
                + "values('" + email+ "'," + rememberChecked+
                ")";
        
        try {
          
                Statement ste = conn.createStatement();
                ste.executeUpdate(requete);
            
            
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
             
    }
    
    
    public String getRememberMe()  throws Exception{
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "SELECT * FROM remember ORDER BY id DESC LIMIT 1";
        String res = "false";
        
        
    try {
        
        preparedStatement = conn.prepareStatement(query);
        resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                
                if(resultSet.getInt("remember")==1){
                    System.out.println("true");
                           res = userOrAdmin(resultSet.getString("email"));
                           
                }else{
                     res= "false";
                    System.out.println("false");
                }
                
            }
    
            } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
    return res;
    }
    
    
    public String userOrAdmin(String email)  throws Exception{
        
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null; 
        String res = "";
        try {
                 preparedStatement = conn.prepareStatement("SELECT * FROM user WHERE email = ?");
                preparedStatement.setString(1, email);
                resultSet = preparedStatement.executeQuery();
            
                while (resultSet.next()) {
                   
                   
                        
                        if(resultSet.getString("roles").equals("[\"ROLE_ADMIN\"]")){
                            System.out.println("ADMIN");
                            res = "trueAdmin";
                            userConnect = new User(resultSet.getString("username"),resultSet.getString("email"),
                            resultSet.getString("num_tel"),resultSet.getString("full_address"));
                            System.out.println(userConnect.toString());
                            userConnect.setCurrent_User(userConnect);
                            break;
                        }else if(resultSet.getString("roles").equals("[\"ROLE_USER\"]")){
                            System.out.println("USER");
                            res = "trueUser";
                            userConnect = new User(resultSet.getString("username"),resultSet.getString("email"),
                            resultSet.getString("num_tel"),resultSet.getString("full_address"));
                            System.out.println(userConnect.toString());
                            userConnect.setCurrent_User(userConnect);
                            break;
                        }
                        
                        
                        /*
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setContentText("User Login succesfully!");
                        alert.setTitle("Succes");
                        alert.setHeaderText(null);
                        alert.show();
                        */
                        
                        break;
                }
                    
            
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
             
    }

    @Override
    public void delete(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void update(User t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
     
     
     
     
     
      public String blockUser(String email)  throws Exception{
        
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String res = "false";
        String requete = "select * from user where email=?";
        
        try {
          
                PreparedStatement smt = conn.prepareStatement(requete);
                smt.setString(1, email);
                ResultSet rs= smt.executeQuery();
            if(rs.next()){
                   System.out.println("email : "+rs.getString("email")+" etat :"+rs.getString("etat")); 
                   String rees=rs.getString("etat");
                   System.out.println("res:"+rees);
                   if(rs.getInt("etat")==1){
                       res="true";
                   }else if (rs.getInt("etat")==0){
                       res="false";
                   }
                }
            
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
          
           return res;  
    }
    
     public void changeEtat(String email,int etat)  throws Exception{
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        
        String query = "UPDATE user SET etatt = '" + etat + "' WHERE email = '" + email + "'";
        
        
        
    try {
        
        preparedStatement = conn.prepareStatement(query);
        resultSet = preparedStatement.executeQuery();
            
            } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
}
