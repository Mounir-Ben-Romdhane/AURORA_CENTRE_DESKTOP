/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entite;

/**
 *
 * @author wiemhjiri
 */
public class User {

    private float id;
    String userName,email,numTel,fullAddress,password,isVerified;
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
    
    public User(float id, String userName, String email, String numTel, String fullAddress,String isVerified) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.numTel = numTel;
        this.fullAddress = fullAddress;
        this.isVerified = isVerified;
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
        return "User{" + "userName=" + userName + ", email=" + email + ", numTel=" + numTel + ", fullAddress=" + fullAddress + '}';
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

}
