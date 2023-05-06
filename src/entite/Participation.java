/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entite;

/**
 *
 * @author asus
 */
public class Participation extends evenement{
    
    private int id;
    private String description_pn,usernameev,emailev;
    private int numtelev;
    
    public evenement evenement_id;

    public Participation(int id, String description_pn, String usernameev, String emailev, int numtelev, evenement evenement_id) {
        this.id = id;
        this.description_pn = description_pn;
        this.usernameev = usernameev;
        this.emailev = emailev;
        this.numtelev = numtelev;
        this.evenement_id = evenement_id;
    }

    public Participation(String description_pn, String usernameev, String emailev, int numtelev, evenement evenement_id) {
        this.description_pn = description_pn;
        this.usernameev = usernameev;
        this.emailev = emailev;
        this.numtelev = numtelev;
        this.evenement_id = evenement_id;
    }

    public Participation() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription_pn() {
        return description_pn;
    }

    public void setDescription_pn(String description_pn) {
        this.description_pn = description_pn;
    }

    public String getUsernameev() {
        return usernameev;
    }

    public void setUsernameev(String usernameev) {
        this.usernameev = usernameev;
    }

    public String getEmailev() {
        return emailev;
    }

    public void setEmailev(String emailev) {
        this.emailev = emailev;
    }

    public int getNumtelev() {
        return numtelev;
    }

    public void setNumtelev(int numtelev) {
        this.numtelev = numtelev;
    }

    public evenement getEvenement_id() {
        return evenement_id;
    }

    public void setEvenement_id(evenement evenement_id) {
        this.evenement_id = evenement_id;
    }
    

    
    
    
    
    
}
