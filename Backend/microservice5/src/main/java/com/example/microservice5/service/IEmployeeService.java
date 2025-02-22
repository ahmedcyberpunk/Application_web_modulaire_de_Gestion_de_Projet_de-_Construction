package com.example.microservice5.service;

import com.example.microservice5.entity.Employee;

import java.util.List;

public interface IEmployeeService {

    Employee getEmployee(Long id);

    List<Employee> getAllEmployees();

    Employee addEmployee(Employee employee);

    void deleteEmployee(Long id);

    Employee updateEmployee(Employee employee);

    List<Employee> getEmployeesSortedByName();

    List<Employee> getEmployeesSortedBySalary();

   // List<Employee> searchEmployeesByName(String name);
   public List<Employee> searchEmployees(String keyword);
}
