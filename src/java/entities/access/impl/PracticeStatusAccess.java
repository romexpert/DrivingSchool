/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entities.access.impl;

import entities.PracticeStatus;
import entities.util.HibernateUtil;
import java.sql.SQLException;
import java.util.List;
import org.hibernate.Session;

/**
 *
 * @author Ivan
 */
public class PracticeStatusAccess extends ItemsAccess<PracticeStatus>{

    @Override
    public PracticeStatus getItem(int id) throws SQLException {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return (PracticeStatus)session.load(PracticeStatus.class, id);
        }
        finally {
            session.close();
        }
    }

    @Override
    public List<PracticeStatus> getAllItems() throws SQLException {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return session.createCriteria(PracticeStatus.class).list();
        }
        finally {
            session.close();
        }
    }
    
}
