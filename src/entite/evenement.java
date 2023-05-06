/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entite;

import java.sql.Date;
import java.util.List;

/**
 *
 * @author asus
 */
public class evenement {
    private int id;
    private String titreev,descriptionev,imageev;
    private Date dateev;
    private String typeev,color;

    public evenement() {
    }

    public evenement(int id, String titreev, String descriptionev, String imageev, Date dateev, String typeev, String color) {
        this.id = id;
        this.titreev = titreev;
        this.descriptionev = descriptionev;
        this.imageev = imageev;
        this.dateev = dateev;
        this.typeev = typeev;
        this.color = color;
    }

    public evenement(String titreev, String descriptionev, String imageev, Date dateev, String typeev, String color) {
        this.titreev = titreev;
        this.descriptionev = descriptionev;
        this.imageev = imageev;
        this.dateev = dateev;
        this.typeev = typeev;
        this.color = color;
    }

    public evenement(String titreev, String descriptionev) {
        this.titreev = titreev;
        this.descriptionev = descriptionev;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitreev() {
        return titreev;
    }

    public void setTitreev(String titreev) {
        this.titreev = titreev;
    }

    public String getDescriptionev() {
        return descriptionev;
    }

    public void setDescriptionev(String descriptionev) {
        this.descriptionev = descriptionev;
    }

    public String getImageev() {
        return imageev;
    }

    public void setImageev(String imageev) {
        this.imageev = imageev;
    }

    public Date getDateev() {
        return dateev;
    }

    public void setDateev(Date dateev) {
        this.dateev = dateev;
    }

    public String getTypeev() {
        return typeev;
    }

    public void setTypeev(String typeev) {
        this.typeev = typeev;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "evenement{" + "id=" + id + ", titreev=" + titreev + ", descriptionev=" + descriptionev + ", imageev=" + imageev + ", dateev=" + dateev + ", typeev=" + typeev + ", color=" + color + '}';
    }
    
    
    public static evenement getServiceById(List<evenement> evenements, int id) {
    for (evenement s : evenements) {
        if (s.getId() == id) {
            return s;
        }
    }
    return null;
}
   
    
    
    
}
