/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Samar
 */
public interface IServiceO<T> {

    void insert(T t 
    ) throws SQLException;

    boolean delete(int id) throws SQLException;

    boolean update(T t ) throws SQLException;

    List<T> readAll() throws SQLException;
}
