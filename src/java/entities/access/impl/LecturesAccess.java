/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entities.access.impl;

import entities.Lecture;
import entities.access.ILecturesAccess;
import entities.util.HibernateUtil;
import java.sql.SQLException;
import java.util.List;
import org.hibernate.Session;

/**
 *
 * @author Екатерина
 */
public class LecturesAccess implements ILecturesAccess{

    @Override
    public void addOrUpdateLecture(Lecture lecture) throws SQLException {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.saveOrUpdate(lecture);
        }
        finally {
            session.close();
        }    
    }

    @Override
    public Lecture getLecture(int id) throws SQLException {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return (Lecture)session.load(Lecture.class, id);
        }
        finally {
            session.close();
        }
    }

    @Override
    public List getAllLectures() throws SQLException {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return session.createCriteria(Lecture.class).list();
        }
        finally {
            session.close();
        }
    }

    @Override
    public void removeLecture(Lecture lecture) throws SQLException {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.delete(lecture);
        }
        finally {
            session.close();
        }
    }
    
}
