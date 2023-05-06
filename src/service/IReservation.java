
package service;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author mfmma
 */
public interface IReservation<R>  {
     void insert(R r) throws SQLException;
    void delete(int id)throws SQLException;
    void update(R r,int id)throws SQLException;
    List<R>readAll();
    R readById(int id);
    
}
