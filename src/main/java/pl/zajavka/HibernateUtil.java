package pl.zajavka;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.util.Map;

public class HibernateUtil {

    private static final Map<String, Object> SETTINGS = Map.ofEntries(
            Map.entry(Environment.DRIVER, "org.postgresql.Driver"),
            Map.entry(Environment.URL, "jdbc:postgresql://localhost:5432/zajavka"),
            Map.entry(Environment.USER, "postgres"),
            Map.entry(Environment.PASS, "password"),
            Map.entry(Environment.DIALECT, "org.hibernate.dialect.PostgreSQLDialect"),
            Map.entry(Environment.HBM2DDL_AUTO, "none"),
            Map.entry(Environment.SHOW_SQL, true),
            Map.entry(Environment.FORMAT_SQL, true)
    );
    private static SessionFactory sessionFactory = loadSessionFactory();

    private static SessionFactory loadSessionFactory() {
        try {
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(SETTINGS)
                    .build();

            Metadata metadata = new MetadataSources(serviceRegistry)
                    .addAnnotatedClass(Employee.class)
                    .getMetadataBuilder()
                    .build();

            return metadata.getSessionFactoryBuilder().build();

        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
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
