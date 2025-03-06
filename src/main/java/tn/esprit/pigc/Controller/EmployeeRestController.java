package tn.esprit.pigc.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.pigc.Entity.Employee;
import tn.esprit.pigc.Entity.PPE;
import tn.esprit.pigc.Service.SMServiceImpl;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/employee")
public class EmployeeRestController {
    @Autowired
    private SMServiceImpl employeeService;

    @PostMapping("/addEmployee")
    public Employee AddEmployee(@RequestBody Employee employee) {
        return employeeService.AddEmployee(employee);
    }


    @GetMapping("/listEmployee")
    public List<Employee> ListEmployee() {
        return employeeService.getAllEmployee();
    }




}
