/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package service;

import entite.Service;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mfmma
 */
public interface IService<T> {
    
    void insert(T t);
    void delete(int id)throws SQLException;
    void update(T t)throws SQLException;
    List<T>readAll();
    T readById(int id);
}
