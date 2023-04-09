/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connexionbd3a43;

import entite.User;
import service.UserService;
import util.DataSource;

/**
 *
 * @author wiemhjiri
 */
public class ConnexionBD3A43 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
//        DataSource ds1 = DataSource.getInstance();
//        System.out.println(ds1);
//        DataSource ds2 = DataSource.getInstance();
//        System.out.println(ds2);
//
//        DataSource ds3 = DataSource.getInstance();
//        System.out.println(ds3);

            //User p1=new User("3a43", "3A43", 20);
            UserService ps=new UserService();
           // ps.insert(p1);
          // ps.insertPst(p1);
          ps.readAll().forEach(System.out::println);


    }

}
