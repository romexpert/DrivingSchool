/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entities.access.impl;

import entities.access.IItemsAccess;
import entities.util.HibernateUtil;
import java.sql.SQLException;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Екатерина
 */
public abstract class ItemsAccess<T> implements IItemsAccess<T>{

    @Override
    public void addOrUpdateItem(T lecture) throws SQLException {
        Transaction tran = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            tran = session.beginTransaction();
            session.saveOrUpdate(lecture);
            tran.commit();
        }
        catch(Exception ex) {
            if(tran != null)
                tran.rollback();
            throw ex;
        }
        finally {
            session.close();
        }    
    }

    @Override
    public void removeItem(T lecture) throws SQLException {
        Transaction tran = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            tran = session.beginTransaction();
            session.delete(lecture);
            tran.commit();
        }
        catch(Exception ex) {
            if(tran != null)
                tran.rollback();
            throw ex;
        }
        finally {
            session.close();
        }
    }

    @Override
    public void addOrUpdateItemsList(List<T> items) throws SQLException {
        Transaction tran = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        tran = session.beginTransaction();
        try {
            for(T item : items)
                session.saveOrUpdate(item);
            
            tran.commit();
        }
        catch(Exception ex) {
            if(tran != null)
                tran.rollback();
            throw ex;
        }
        finally {
            session.close();
        }
    }
    
}
