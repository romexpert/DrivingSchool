/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entities.access.impl;

import entities.Account;
import entities.util.HibernateUtil;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Ivan
 */
public class SessionAccess extends ItemsAccess<Account> {
    
    public Account getItem(UUID sessionId) throws SQLException {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Criteria cr = session.createCriteria(Account.class);
            cr.add(Restrictions.eq("sessionId", sessionId));
            return (Account)cr.uniqueResult();
        }
        finally {
            session.close();
        }
    }
    
    @Override
    public List<Account> getAllItems() throws SQLException {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return session.createCriteria(Account.class).list();
        }
        finally {
            session.close();
        }
    }

    @Override
    public Account getItem(int id) throws SQLException {
        throw new UnsupportedOperationException("Not supported.");
    }
}