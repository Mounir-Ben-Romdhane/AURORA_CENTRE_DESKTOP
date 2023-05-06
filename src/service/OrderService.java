/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entite.Order;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.DataSource;

/**
 *
 * @author Samar
 */


public class OrderService implements IServiceO<Order> {

    private Connection cnx;

    public OrderService() {
        cnx = DataSource.getInstance().getCnx();
    }

    @Override
    public void insert(Order t) throws SQLException {
        try {
            String req = "INSERT INTO `order` (`id`, `user_id`, `created_at`, `is_paid`, `reference`, `quantity`, `prix`, `total`) VALUES (NULL, '"+t.getUser_id()+"', Now(), "+t.isIs_paid()+", '"+t.getReference()+"', '"+t.getQuantity()+"', '"+t.getPrix()+"', '"+t.getTotal()+"');";
            System.out.println(req);
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public boolean delete(int id) throws SQLException {
        Boolean ok = false;
        try {
            String req = "DELETE FROM `order` WHERE `id` = " + id;
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            ok = true;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return ok;
    }

    @Override
    public boolean update(Order t) throws SQLException {
        
        try {
            String req = "UPDATE `order` SET `quantity` = ?,`total` = ? where `id` = ?";
            PreparedStatement ps = cnx.prepareStatement(req);
            //ps.setBoolean(1, t.isIs_paid());
            ps.setInt(1, t.getQuantity());
            //ps.setDouble(2, t.getPrix());
            ps.setDouble(2, t.getTotal());
            ps.setInt(3, t.getId());
            ps.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(OrderService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public List<Order> readAll() throws SQLException {
        List<Order> Orders = new ArrayList<>();
        String s = "select * from `Order`";
        Statement st = cnx.createStatement();
        ResultSet rs = st.executeQuery(s);
        while (rs.next()) {
            Order u = new Order();
            u.setId(rs.getInt("id"));
            u.setQuantity(rs.getInt("quantity"));
            u.setReference(rs.getString("reference"));
            u.setCreatedAt(rs.getString("created_at"));
            u.setPrix(rs.getDouble("prix"));
            u.setTotal(rs.getDouble("total"));
            u.setIs_paid(rs.getBoolean("is_paid"));
            u.setUser_id(rs.getInt("user_id"));
            Orders.add(u);
        }
        return Orders;
    }
    
    public List<Order> readByIdUser(int id) throws SQLException {
        List<Order> Orders = new ArrayList<>();
        String s = "select * from `Order` where `user_id`="+id;
        Statement st = cnx.createStatement();
        ResultSet rs = st.executeQuery(s);
        while (rs.next()) {
            Order u = new Order();
            u.setId(rs.getInt("id"));
            u.setQuantity(rs.getInt("quantity"));
            u.setReference(rs.getString("reference"));
            u.setCreatedAt(rs.getString("created_at"));
            u.setPrix(rs.getDouble("prix"));
            u.setTotal(rs.getDouble("total"));
            u.setIs_paid(rs.getBoolean("is_paid"));
            u.setUser_id(rs.getInt("user_id"));
            Orders.add(u);
        }
        return Orders;
    }
    
    public Order readById(int id) throws SQLException {
        Order u = new Order();
        String s = "select * from `Order` where `id`="+id;
        Statement st = cnx.createStatement();
        ResultSet rs = st.executeQuery(s);
        while (rs.next()) {
            u.setId(rs.getInt("id"));
            u.setQuantity(rs.getInt("quantity"));
            u.setReference(rs.getString("reference"));
            u.setCreatedAt(rs.getString("created_at"));
            u.setPrix(rs.getDouble("prix"));
            u.setTotal(rs.getDouble("total"));
            u.setIs_paid(rs.getBoolean("is_paid"));
            u.setUser_id(rs.getInt("user_id"));
        }
        return u;
    }

    /**
     *
     * @param a
     * @return
     */
    public Order ChercherCommande(int a){
         Order u=new Order();
        try {       
        String requete3="SELECT * FROM Order where id="+a;
        Statement st;
       
            st = cnx.createStatement();
            ResultSet rs=st.executeQuery(requete3);
            while(rs.next()){
            u.setQuantity(rs.getInt("quantity"));
            u.setReference(rs.getString("reference"));
            u.setCreatedAt(rs.getString("created_at"));
            u.setPrix(rs.getDouble("prix"));
            u.setTotal(rs.getDouble("total"));
            u.setIs_paid(rs.getBoolean("is_paid"));
            u.setUser_id(rs.getInt("user_id"));

            
            }
              System.out.println("voici la commande!");
            
          
        } catch (SQLException ex) {
System.err.println(ex.getMessage());       
        }
        return u;
        
    }
}
