package com.example.microservice5.controller;


import com.example.microservice5.entity.Employee;
import com.example.microservice5.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/employee")
@CrossOrigin("*")
public class EmployeeController {
    EmployeeService employeeService;

    @DeleteMapping(path ="/delete_employee/{id}")
     void deleteEmployee(@PathVariable Long id){
        employeeService.deleteEmployee(id);
    }

    @PutMapping("/update_employee/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employee) {
        employee.setId(id);
        Employee updatedEmployee = employeeService.updateEmployee(employee);
        return ResponseEntity.ok(updatedEmployee);
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
    public List<Employee> searchEmployees(@RequestParam String keyword) {
        return employeeService.searchEmployees(keyword);
    }

    @PutMapping("/heuresSupp/{employeeId}/{heuresSupp}")
    public void ajouterHeuresSupp(@PathVariable Long employeeId, @PathVariable Long heuresSupp){
        employeeService.ajouterHeuresSupp(employeeId, heuresSupp);
    }

    @PutMapping("/avanceSalaire/{employeeId}/{montant}")
    public void demanderAvanceSalaire(@PathVariable Long employeeId, @PathVariable Long montant) {
        employeeService.demanderAvanceSalaire(employeeId, montant);
    }
}
