/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entite;

import javafx.scene.control.Button;

/**
 *
 * @author wiemhjiri
 */
public class User {

    private float id;
    private String userName,email,numTel,fullAddress,password,isVerified,image;
    private Button etat;
    public static User Current_User;

   

    

    public User() {
    }

    public User(float id) {
        this.id = id;
    }

    public User(String userName, String email, String numTel, String fullAddress) {
        this.userName = userName;
        this.email = email;
        this.numTel = numTel;
        this.fullAddress = fullAddress;
    }

    public float getId() {
        return id;
    }

    public void setId(float id) {
        this.id = id;
    }

    public User(float id, String userName, String email, String numTel, String fullAddress) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.numTel = numTel;
        this.fullAddress = fullAddress;
    }
    
    
    
    public User(float id, String userName, String email, String numTel, String fullAddress,String isVerified,Button etat) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.numTel = numTel;
        this.fullAddress = fullAddress;
        this.isVerified = isVerified;
        this.etat=etat;
    }
    
    
    
    
    

    public User(String userName, String email, String numTel, String fullAddress, String password) {
        this.userName = userName;
        this.email = email;
        this.numTel = numTel;
        this.fullAddress = fullAddress;
        this.password = password;
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }
    
    

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumTel() {
        return numTel;
    }

    public void setNumTel(String numTel) {
        this.numTel = numTel;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User Name = "+userName + "\t Email = " + email + "\t Phone Number = " + numTel + 
                "\t Full Address = " + fullAddress ;
    }

    public String getIsVerified() {
        return isVerified;
    }

    public void setIsVerified(String isVerified) {
        this.isVerified = isVerified;
    }

  
    public static User getCurrent_User() {
        return Current_User;
    }

    public static void setCurrent_User(User Current_User) {
        User.Current_User = Current_User;
    }

    public Button getEtat() {
        return etat;
    }

    public void setEtat(Button etat) {
        this.etat = etat;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

   
    
    

}
