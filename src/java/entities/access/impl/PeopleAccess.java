/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entities.access.impl;

import entities.Person;
import entities.util.HibernateUtil;
import java.sql.SQLException;
import java.util.List;
import org.hibernate.Session;

/**
 *
 * @author Ivan
 */
public class PeopleAccess extends ItemsAccess<Person> {
    @Override
    public Person getItem(int id) throws SQLException {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return (Person)session.load(Person.class, id);
        }
        finally {
            session.close();
        }
    }
    
    @Override
    public List<Person> getAllItems() throws SQLException {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return session.createCriteria(Person.class).list();
        }
        finally {
            session.close();
        }
    }
}
