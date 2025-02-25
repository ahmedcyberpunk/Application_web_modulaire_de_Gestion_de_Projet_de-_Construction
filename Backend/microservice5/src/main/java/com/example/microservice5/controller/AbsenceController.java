package com.example.microservice5.controller;


import com.example.microservice5.entity.Absence;
import com.example.microservice5.entity.AbsenceType;
import com.example.microservice5.entity.Employee;
import com.example.microservice5.service.AbsenceService;
import com.example.microservice5.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/absences")
@CrossOrigin("*")
public class AbsenceController {
    AbsenceService absenceService;
    EmployeeService employeeService;


    @PostMapping("/add/{employeeId}")
    public Absence addAbsence(@RequestBody Absence absence,
                              @PathVariable Long employeeId) {
        return absenceService.affecterAbsenceEmployee(absence, employeeId);
    }


    @PutMapping("/update/{id}")
    public Absence updateAbsence(@PathVariable Long id,
                                 @RequestParam AbsenceType newType,
                                 @RequestParam LocalDate newDateDebut,
                                 @RequestParam LocalDate newDateFin) {
        return absenceService.updateAbsence(id, newType, newDateDebut, newDateFin);
    }



    @GetMapping("/all")
    public List<Map<String, Object>> getAllAbsences() {
        return absenceService.getAllAbsencesWithEmployeeInfo();
    }


    @GetMapping("/employees/{employeeId}/absences")
    public List<Absence> getAbsencesByEmployeeId(@PathVariable Long employeeId) {
        return absenceService.getAbsencesByEmployee(employeeId);
    }

    @GetMapping("/employees/with-absences")
    public List<Employee> getAllEmployeesWithAbsences() {
        List<Employee> employees = employeeService.getAllEmployees();

        for (Employee employee : employees) {
            List<Absence> absences = absenceService.getAbsencesByEmployee(employee.getId());
            employee.setAbsences(absences);
        }

        return employees;
    }

    @DeleteMapping("/delete/{id}")
    public void deleteAbsence(@PathVariable Long id) {
        absenceService.deleteAbsence(id);
    }

}
