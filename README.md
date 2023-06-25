### HIBERNATE - aplikacja CRUD
### prosta aplikacja typu CRUD do komunikacji z baza danych (w tym przypadku nie wykorzystano framworka SPRING, ktory by skrocil pewne zapisy dotyczace min sesji.)

### korzystamy tutaj z konfiguracji hiberneta zdefinowanej w klasie HibernateUtil
```java
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
```

### iinym sposbem moze byc wykorzystanie pliku xml: ponizej przykladowa konfiguracja pliku plus klasa korzystajaca z niego
```xml
<!DOCTYPE hibernate-configuration PUBLIC
        "-//Hibernate/Hibernate Configuration DTD//EN"
        "http://hibernate.sourceforge.net/hibernate-configuration-3.0.dtd">
<hibernate-configuration>
    <session-factory>
        <property name="hibernate.connection.driver.class">org.postgresql.Driver</property>
        <property name="hibernate.connection.url">jdbc:postgresql://localhost:5432/zajavka</property>
        <property name="hibernate.connection.username">postgres</property>
        <property name="hibernate.connection.password">password</property>
        <property name="hibernate.dialect">org.hibernate.dialect.PostgreSQLDialect</property>
        <property name="hibernate.hbm2ddl.auto">none</property>
        <property name="hibernate.show_sql">true</property>
        <property name="hibernate.format_sql">true</property>
        <property name="hibernate.use_sql_comments">true</property>
        <mapping class="pl.zajavka.Employee"/>
    </session-factory>
</hibernate-configuration>
```
```java
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
```