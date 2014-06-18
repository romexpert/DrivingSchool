/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entities.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

/**
 *
 * @author Екатерина
 */
public class HibernateUtil {
    private static SessionFactory sessionFactory = null;
    private static ServiceRegistry serviceRegistry = null;
    static {
        try{
            Configuration config = new Configuration();
            config.configure();
            ServiceRegistryBuilder builder = new ServiceRegistryBuilder();
            builder.applySettings(config.getProperties());
            serviceRegistry = builder.buildServiceRegistry();
            sessionFactory = config.buildSessionFactory(serviceRegistry);
        }
        catch(Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
