/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entite;

import java.time.LocalDateTime;

/**
 *
 * @author malek
 */
public class Reponse {
     int id;
    Reclamation reclamation;
    String response;
    LocalDateTime date_reponse;

    public Reponse() {
    }

    public Reponse(int id, Reclamation reclamation, String response, LocalDateTime date_reponse) {
        this.id = id;
        this.reclamation = reclamation;
        this.response = response;
        this.date_reponse = date_reponse;
    }

    public Reponse(Reclamation reclamation,String response, LocalDateTime date_reponse) {
        this.reclamation = reclamation;
        this.response = response;
        this.date_reponse = date_reponse;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Reclamation getReclamation() {
        return reclamation;
    }

    public void setReclamation(Reclamation reclamation) {
        this.reclamation = reclamation;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public LocalDateTime getDate_reponse() {
        return date_reponse;
    }

    public void setDate_reponse(LocalDateTime date_reponse) {
        this.date_reponse = date_reponse;
    }

    @Override
    public String toString() {
        return "Reponse{" + "id=" + id + ", reclamation=" + reclamation + ", response=" + response + ", date_reponse=" + date_reponse + '}';
    }
    
}
