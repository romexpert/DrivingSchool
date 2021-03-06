/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entities.access.impl;

import entities.Lecture;
import entities.util.HibernateUtil;
import java.sql.SQLException;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Ivan
 */
public class LecturesAccess extends ItemsAccess<Lecture> {

    @Override
    public Lecture getItem(int id) throws SQLException {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return (Lecture)session.load(Lecture.class, id);
        }
        finally {
            session.close();
        }
    }
    
    @Override
    public List<Lecture> getAllItems() throws SQLException {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Criteria cr = session.createCriteria(Lecture.class);
            cr.addOrder(Order.asc("number"));
            return cr.list();
        }
        finally {
            session.close();
        }
    }
    
    public Lecture getByNumber(int number) throws SQLException {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Criteria cr = session.createCriteria(Lecture.class);
            cr.add(Restrictions.eq("number", number));
            return (Lecture)cr.uniqueResult();
        }
        finally {
            session.close();
        }
    }
    
    
}
