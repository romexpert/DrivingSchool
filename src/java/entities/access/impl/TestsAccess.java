/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entities.access.impl;

import entities.TestQuestion;
import entities.util.HibernateUtil;
import java.sql.SQLException;
import java.util.List;
import org.hibernate.Session;

/**
 *
 * @author Ivan
 */
public class TestsAccess extends ItemsAccess<TestQuestion> {

    @Override
    public TestQuestion getItem(int id) throws SQLException {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return (TestQuestion)session.get(TestQuestion.class, id);
        }
        finally {
            session.close();
        }        
    }

    @Override
    public List<TestQuestion> getAllItems() throws SQLException {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            
            List<TestQuestion> questions = session.createCriteria(TestQuestion.class).list();
            for(TestQuestion q : questions){
                q.getVariants().toArray();
            }
            
            return questions;
        }
        finally {
            session.close();
        }
    }
    
}
