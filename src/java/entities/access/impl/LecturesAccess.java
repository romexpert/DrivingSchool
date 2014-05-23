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
import org.hibernate.Transaction;

/**
 *
 * @author Екатерина
 */
public class LecturesAccess implements ILecturesAccess{

    @Override
    public void addOrUpdateLecture(Lecture lecture) throws SQLException {
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
    public void addOrUpdateLecturesPack(List<Lecture> lectures) throws SQLException {
        Transaction tran = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            tran = session.beginTransaction();
            lectures.forEach(a->session.saveOrUpdate(a));
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
