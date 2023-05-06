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
public class Facture {
    int id,commande_id;
    String date_facturation;
    boolean est_sup;

    public Facture(int id, int commande_id, String date_facturation, boolean est_sup) {
        this.id = id;
        this.commande_id = commande_id;
        this.date_facturation = date_facturation;
        this.est_sup = est_sup;
    }

    public Facture(int commande_id, String date_facturation, boolean est_sup) {
        this.commande_id = commande_id;
        this.date_facturation = date_facturation;
        this.est_sup = est_sup;
    }

    public Facture(int commande_id, boolean est_sup) {
        this.commande_id = commande_id;
        this.est_sup = est_sup;
    }
    

    public Facture() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCommande_id() {
        return commande_id;
    }

    public void setCommande_id(int commande_id) {
        this.commande_id = commande_id;
    }

    public String getDate_facturation() {
        return date_facturation;
    }

    public void setDate_facturation(String date_facturation) {
        this.date_facturation = date_facturation;
    }

    public boolean isEst_sup() {
        return est_sup;
    }

    public void setEst_sup(boolean est_sup) {
        this.est_sup = est_sup;
    }

    @Override
    public String toString() {
        return "Facture{" + "id=" + id + ", commande_id=" + commande_id + ", date_facturation=" + date_facturation + ", est_sup=" + est_sup + '}';
    }
    
}
