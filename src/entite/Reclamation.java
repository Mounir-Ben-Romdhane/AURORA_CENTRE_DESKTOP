/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entite;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 *
 * @author malek
 */
public class Reclamation {
    private int id;
    private String type;
    private String nom;
    private String description;
    private int status;
    private LocalDateTime date_reclamation;
    private String email_connecte;
    private String email_reclamation;

    public Reclamation() {
    }

    public Reclamation(int id, String type, String nom, String description, int status, LocalDateTime date_reclamation, String email_connecte, String email_reclamation) {
        this.id = id;
        this.type = type;
        this.nom = nom;
        this.description = description;
        this.status = status;
        this.date_reclamation =date_reclamation;
        this.email_connecte = email_connecte;
        this.email_reclamation = email_reclamation;
    }

    public Reclamation(String type, String nom, String description, int status, LocalDateTime date_reclamation, String email_connecte, String email_reclamation) {
        this.type = type;
        this.nom = nom;
        this.description = description;
        this.status = status;
        this.date_reclamation=date_reclamation;
        this.email_connecte = email_connecte;
        this.email_reclamation = email_reclamation;
    }
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public LocalDateTime getDate_reclamation() {
        return date_reclamation;
    }

    public void setDate_reclamation(Date date_reclamation) {
        this.date_reclamation=this.date_reclamation;
    }

    public String getEmail_connecte() {
        return email_connecte;
    }

    public void setEmail_connecte(String email_connecte) {
        this.email_connecte = email_connecte;
    }

    public String getEmail_reclamation() {
        return email_reclamation;
    }

    public void setEmail_reclamation(String email_reclamation) {
        this.email_reclamation = email_reclamation;
    }
    public static Reclamation getReclamationById(List<Reclamation> reclamations, int id) {
    for (Reclamation r : reclamations) {
        if (r.getId() == id) {
            return r;
        }
    }
    return null;
}

    @Override
    public String toString() {
        return "Reclamation{" + "id=" + id + ", type=" + type + ", nom=" + nom + ", description=" + description + ", status=" + status + ", date_reclamation=" + date_reclamation + ", email_connecte=" + email_connecte + ", email_reclamation=" + email_reclamation + '}';
    }
    
    
}
