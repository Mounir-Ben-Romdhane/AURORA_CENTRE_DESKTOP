/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entite;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author mfmma
 */
public class Reservation {

    private int id,numero,numeroTel ;
    private String email,user_name;
    private LocalDateTime dateR;
    private Service id_service;
    private String email_connect;

    public Reservation(int par, int par1, String maissa, String mm, LocalDateTime dateR1) {
    }

public Reservation(int id, int numero, int numeroTel, String email, String user_name, LocalDateTime dateR, Service id_service, String email_connect) {
    this.id = id;
    this.numero = numero;
    this.numeroTel = numeroTel;
    this.email = email;
    this.user_name = user_name;
    this.dateR = dateR;
    this.id_service = id_service;
    this.email_connect = email_connect;
}


    public Reservation(int numero, int numeroTel, String email, String user_name, LocalDateTime dateR, Service id_service, String email_connect) {
        this.numero = numero;
        this.numeroTel = numeroTel;
        this.email = email;
        this.user_name = user_name;
        this.dateR = dateR;
        this.id_service = id_service;
        this.email_connect = email_connect;
    }

    public String getEmail_connect() {
        return email_connect;
    }

    public void setEmail_connect(String email_connect) {
        this.email_connect = email_connect;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public int getNumeroTel() {
        return numeroTel;
    }

    public void setNumeroTel(int numeroTel) {
        this.numeroTel = numeroTel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

   
    public LocalDateTime getDateR() {
        return dateR;
    }

    public void setDateR(LocalDateTime dateR) {
        this.dateR = dateR;
    }

    public Service getId_service() {
        return id_service;
    }

    public void setId_service(Service id_service) {
        this.id_service = id_service;
    }

    
    @Override
    public String toString() {
        return "Reservation{" + "id=" + id + ", numero=" + numero + ", numeroTel=" + numeroTel + ", email=" + email + ", user_name=" + user_name + "dateR=" + dateR + ", id_service=" + id_service + '}';
    }
    
       
       
        
        
}
