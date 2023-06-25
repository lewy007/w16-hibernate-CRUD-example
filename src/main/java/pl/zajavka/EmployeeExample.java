package pl.zajavka;

import java.math.BigDecimal;

public class EmployeeExample {
    public static void main(String[] args) {

        EmployeeRepository employeeRepository = new EmployeeRepository();

        employeeRepository.deleteAll();

        Employee employee1 = employeeRepository.insert(EmployeeData.someEmployee1());
        Employee employee2 = employeeRepository.insert(EmployeeData.someEmployee2());
        Employee employee3 = employeeRepository.insert(EmployeeData.someEmployee3());

        System.out.println("###Employee1: " + employeeRepository.getEmployee(employee1.getEmployeeId()));
        System.out.println("###Employee2: " + employeeRepository.getEmployee(employee2.getEmployeeId()));

        employeeRepository.updateEmployee(employee3.getEmployeeId(), new BigDecimal("15000.23"));
        System.out.println("###Employee3: " + employeeRepository.getEmployee(employee3.getEmployeeId()));

        employeeRepository.employeeList().forEach(employee -> System.out.println("###Employee: " + employee));

        employeeRepository.deleteEmployee(employee2.getEmployeeId());

        employeeRepository.employeeList().forEach(employee -> System.out.println("###Employee: " + employee));

        HibernateUtil.closeSessionFactory();
    }
}