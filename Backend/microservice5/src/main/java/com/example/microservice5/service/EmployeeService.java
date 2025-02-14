package com.example.microservice5.service;


import com.example.microservice5.entity.Employee;
import com.example.microservice5.repository.AbsenceRepository;
import com.example.microservice5.repository.EmployeeRepository;
import com.example.microservice5.repository.PerformanceRepository;
import com.example.microservice5.repository.SalaryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class EmployeeService {
    SalaryRepository salaryRepository;
    AbsenceRepository absenceRepository;
    EmployeeRepository employeeRepository;
    PerformanceRepository performanceRepository;

    public Employee getEmployee(Long id) {
        return employeeRepository.findById(id).get();
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();

    }
    public Employee addEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public void deleteEmployee(Long id) {
    if (employeeRepository.existsById(id)){
    employeeRepository.deleteById(id);
    }else {
    throw new RuntimeException("Employee not found");
}
    }

    public Employee updateEmployee(Employee employee) {
        if (employeeRepository.existsById(employee.getId())) {
          return   employeeRepository.save(employee);
        }else {
       throw new RuntimeException("Employee not found");
        }
    }
}
