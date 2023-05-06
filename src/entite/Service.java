/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entite;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author mfmma
 */
public class Service {

    private int id ;
    private String titreS,descrptionS,typeS,image;
    private LocalDateTime dateS;

    
    public Service(){}
    
   
      public Service(int id, String titreS, String descrptionS, String typeS,String image,LocalDateTime dateS) {
        this.id = id;
        this.titreS = titreS;
        this.descrptionS = descrptionS;
        this.typeS = typeS;
          this.image = image;
           this.dateS = dateS;
        
        
    }
      
      
      public Service(String titreS, String descrptionS, String typeS,String image,LocalDateTime dateS) {
        
        this.titreS = titreS;
        this.descrptionS = descrptionS;
        this.typeS = typeS;
          this.image = image;
           this.dateS = dateS;
        
        
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitreS() {
        return titreS;
    }

    public void setTitreS(String titreS) {
        this.titreS = titreS;
    }

    public String getDescrptionS() {
        return descrptionS;
    }

    public void setDescrptionS(String descrptionS) {
        this.descrptionS = descrptionS;
    }

    public String getTypeS() {
        return typeS;
    }

    public void setTypeS(String typeS) {
        this.typeS = typeS;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public LocalDateTime getDateS() {
        return dateS;
    }

    public void setDateS(LocalDateTime dateS) {
        this.dateS = dateS;
    }

  @Override
    public String toString() {
        return "Service{" + "id=" + id + ", titreS=" + titreS + ", descrptionS=" + descrptionS + ", typeS=" + typeS + ", image=" + image + ", dateS=" + dateS + '}';
    }

public static Service getServiceById(List<Service> service, int id) {
    for (Service s : service) {
        if (s.getId() == id) {
            return s;
        }
    }
    return null;
}
   


}
