/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entities.access;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Екатерина
 * @param <T>
 */
public interface IItemsAccess<T> {
    public void addOrUpdateItem(T item) throws SQLException;
    public void addOrUpdateItemsList(List<T> items) throws SQLException;
    public T getItem(int id) throws SQLException;
    public List<T> getAllItems() throws SQLException;
    public void removeItem(T item) throws SQLException;
}
