/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entities.access.impl;

import entities.LectureStatus;
import entities.util.HibernateUtil;
import java.sql.SQLException;
import java.util.List;
import org.hibernate.Session;

/**
 *
 * @author Ivan
 */
public class LectureStatusAccess extends ItemsAccess<LectureStatus>{

    @Override
    public LectureStatus getItem(int id) throws SQLException {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return (LectureStatus)session.load(LectureStatus.class, id);
        }
        finally {
            session.close();
        }
    }

    @Override
    public List<LectureStatus> getAllItems() throws SQLException {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return session.createCriteria(LectureStatus.class).list();
        }
        finally {
            session.close();
        }
    }
    
}
