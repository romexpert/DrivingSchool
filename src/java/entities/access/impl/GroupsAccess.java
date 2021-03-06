/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entities.access.impl;

import entities.StudentGroup;
import entities.util.HibernateUtil;
import java.sql.SQLException;
import java.util.List;
import org.hibernate.Session;

/**
 *
 * @author Ivan
 */
public class GroupsAccess extends ItemsAccess<StudentGroup> {
    @Override
    public StudentGroup getItem(int id) throws SQLException {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            StudentGroup group = (StudentGroup)session.load(StudentGroup.class, id);
            group.getStudents().toArray();
            return group;
        }
        finally {
            session.close();
        }
    }
    
    @Override
    public List<StudentGroup> getAllItems() throws SQLException {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return session.createCriteria(StudentGroup.class).list();
        }
        finally {
            session.close();
        }
    }
}
