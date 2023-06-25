package pl.zajavka;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtilWithXml {

    private static SessionFactory sessionFactory = null;

    static {
        try {
            loadSessionFactory();
        } catch (Exception ex) {
            System.err.println("Exception while initializing: " + ex);

        }

    }

    private static void loadSessionFactory() {
        Configuration configuration = new Configuration();
        configuration.configure("/META-INF/hibernate.cfg.xml");
        sessionFactory = configuration.buildSessionFactory();
    }

    static void closeSessionFactory() {
        try {
            sessionFactory.close();
        } catch (Exception ex) {
            System.err.println("Exception while closing session factory: " + ex);
        }
    }

    static Session getSession() {
        try {
            return sessionFactory.openSession();
        } catch (Exception ex) {
            System.err.println("Exception while opening session: " + ex);
        }
        return null;
    }
}
