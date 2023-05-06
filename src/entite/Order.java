/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entite;

import java.util.Date;

/**
 *
 * @author Samar
 */
public class Order {
    int id,user_id,quantity;
    String createdAt;
    boolean is_paid;
    String reference;
    double prix,total;

    public Order(int id, int user_id, int quantity, String createdAt, boolean is_paid, String reference, double prix, double total) {
        this.id = id;
        this.user_id = user_id;
        this.quantity = quantity;
        this.createdAt = createdAt;
        this.is_paid = is_paid;
        this.reference = reference;
        this.prix = prix;
        this.total = total;
    }

    public Order(int user_id, int quantity, String createdAt, boolean is_paid, String reference, double prix, double total) {
        this.user_id = user_id;
        this.quantity = quantity;
        this.createdAt = createdAt;
        this.is_paid = is_paid;
        this.reference = reference;
        this.prix = prix;
        this.total = total;
    }

    public Order(int user_id, int quantity, boolean is_paid, String reference, double prix, double total) {
        this.user_id = user_id;
        this.quantity = quantity;
        this.is_paid = is_paid;
        this.reference = reference;
        this.prix = prix;
        this.total = total;
        this.createdAt = new Date().toString();
    }

    public Order(int id, int quantity, boolean is_paid, double prix, double total) {
        this.id = id;
        this.quantity = quantity;
        this.is_paid = is_paid;
        this.prix = prix;
        this.total = total;
    }
    
    

    public Order() {
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    } 

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isIs_paid() {
        return is_paid;
    }

    public void setIs_paid(boolean is_paid) {
        this.is_paid = is_paid;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    @Override
    public String toString() {
        return "Order{" + "id=" + id + ", user_id=" + user_id + ", createdAt=" + createdAt + ", is_paid=" + is_paid + ", reference=" + reference + '}';
    }
    
}
