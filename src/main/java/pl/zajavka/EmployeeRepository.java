package pl.zajavka;

import org.hibernate.Session;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class EmployeeRepository {

    Employee insert(final Employee employee) {
        try (Session session = HibernateUtil.getSession()) {

            // sprawdzenie czy sesja nie jest nullem
            Objects.requireNonNull(session);

            session.beginTransaction();
            session.persist(employee);
            session.getTransaction().commit();
            return employee;
        }
    }

    List<Employee> employeeList() {
        try (Session session = HibernateUtil.getSession()) {

            // sprawdzenie czy sesja nie jest nullem
            Objects.requireNonNull(session);

            session.beginTransaction();
            String query = "select emp from Employee emp";
            List<Employee> employees = session.createQuery(query, Employee.class).list();
            session.getTransaction().commit();
            return employees;
        }
    }

    Optional<Employee> getEmployee(Integer employeeId) {
        try (Session session = HibernateUtil.getSession()) {

            // sprawdzenie czy sesja nie jest nullem
            Objects.requireNonNull(session);

            session.beginTransaction();
            Optional<Employee> employee = Optional.ofNullable(session.find(Employee.class, employeeId));
            session.getTransaction().commit();
            return employee;
        }
    }

    void updateEmployee(Integer employeeId, BigDecimal newSalary) {
        try (Session session = HibernateUtil.getSession()) {

            // sprawdzenie czy sesja nie jest nullem
            Objects.requireNonNull(session);

            session.beginTransaction();
            Employee employee = session.find(Employee.class, employeeId);
            employee.setSalary(newSalary);
            session.getTransaction().commit();
        }
    }

    void deleteEmployee(Integer employeeId) {
        try (Session session = HibernateUtil.getSession()) {

            // sprawdzenie czy sesja nie jest nullem
            Objects.requireNonNull(session);

            session.beginTransaction();
            Employee employee = session.find(Employee.class, employeeId);
            session.remove(employee);
            session.getTransaction().commit();
        }
    }

    public void deleteAll() {
        try (Session session = HibernateUtil.getSession()) {

            // sprawdzenie czy sesja nie jest nullem
            Objects.requireNonNull(session);

            session.beginTransaction();
            String query = "select emp from Employee emp";
            session.createQuery(query, Employee.class).list().forEach(elem -> session.remove(elem));
            session.getTransaction().commit();
        }
    }
}
