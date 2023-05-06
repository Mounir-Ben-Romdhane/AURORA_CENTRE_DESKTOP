/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entite;

/**
 *
 * @author Samar
 */
public class Produit {
    private int id;
    private String nom,description,image;
    private int prix;
    private int categoryName;

    public Produit() {
    }

    public Produit(int id, String nom, String description, String image, int prix, int category) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.image = image;
        this.prix = prix;
        this.categoryName = category;
    }

    public Produit(String nom, String description, String image, int prix, int categoryName) {
        this.nom = nom;
        this.description = description;
        this.image = image;
        this.prix = prix;
        this.categoryName = categoryName;
    }
    
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    public int getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(int categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public String toString() {
        return "Produit{" + "nom=" + nom + ", description=" + description + ", image=" + image + ", prix=" + prix + ", categoryName=" + categoryName + '}';
    }

    
    
    
}
