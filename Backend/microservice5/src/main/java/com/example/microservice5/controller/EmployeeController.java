package com.example.microservice5.controller;


import com.example.microservice5.entity.Employee;
import com.example.microservice5.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/employee")
public class EmployeeController {
    EmployeeService employeeService;

    @DeleteMapping(path ="/delete_employee/{id}")
     void deleteEmployee(@PathVariable Long id){
        employeeService.deleteEmployee(id);
    }

    @PutMapping(path ="/update_employee")
    Employee updateEmployee(@RequestBody Employee employee){
      return   employeeService.updateEmployee(employee);
    }

    @GetMapping(path ="/get_employee/{id}")
     Employee getEmployee(@PathVariable Long id){
        return employeeService.getEmployee(id);
    }

    @GetMapping(path ="/get_all_employees")
    List<Employee> getAllEmployees(){
        return employeeService.getAllEmployees();
    }

    @PostMapping(path ="/add_employee")
    Employee addEmployee(@RequestBody Employee employee){
        return employeeService.addEmployee(employee);
    }
    @GetMapping("/sorted-by-name")
    public List<Employee> getEmployeesSortedByName() {
        return employeeService.getEmployeesSortedByName();
    }

    @GetMapping("/sorted-by-salary")
    public List<Employee> getEmployeesSortedBySalary() {
        return employeeService.getEmployeesSortedBySalary();
    }
    @GetMapping("/search")
    public List<Employee> searchEmployees(@RequestParam String name) {
        return employeeService.searchEmployeesByName(name);
    }
}
