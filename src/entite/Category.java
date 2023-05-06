/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entite;

import java.util.List;

/**
 *
 * @author winxspace
 */
public class Category {
    private int idcat;
    private String nomcat;
    private List<Product> products;


    public Category() {
    }

    public Category(int idcat, String nomcat, List<Product> products) {
        this.idcat = idcat;
        this.nomcat = nomcat;
        this.products = products;
    }

    public int getIdcat() {
        return idcat;
    }

    public void setIdcat(int idcat) {
        this.idcat = idcat;
    }

    public String getNomcat() {
        return nomcat;
    }

    public void setNomcat(String nomcat) {
        this.nomcat = nomcat;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> projects) {
        this.products = projects;
    }

    @Override
    public String toString() {
        return "Category{" +
                "idcat=" + idcat +
                ", nomcat='" + nomcat + '\'' +
                ", products=" + products +
                '}';
    }
}
