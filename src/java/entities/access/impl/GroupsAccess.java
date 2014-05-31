/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entities.access.impl;

import entities.Group;
import entities.Lecture;
import entities.util.HibernateUtil;
import java.sql.SQLException;
import java.util.List;
import org.hibernate.Session;

/**
 *
 * @author Ivan
 */
public class GroupsAccess extends ItemsAccess<Group> {
    @Override
    public Group getItem(int id) throws SQLException {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return (Group)session.load(Group.class, id);
        }
        finally {
            session.close();
        }
    }
    
    @Override
    public List<Group> getAllItems() throws SQLException {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return session.createCriteria(Group.class).list();
        }
        finally {
            session.close();
        }
    }
}
