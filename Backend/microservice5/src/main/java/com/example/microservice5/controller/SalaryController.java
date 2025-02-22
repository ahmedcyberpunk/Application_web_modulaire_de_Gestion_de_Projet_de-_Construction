package com.example.microservice5.controller;

import com.example.microservice5.entity.Salary;
import com.example.microservice5.service.SalaryService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@AllArgsConstructor
@RequestMapping("/salaries")
@CrossOrigin("*")
public class  SalaryController {

    SalaryService salaryService;

    @PostMapping("/calculate/{employeeId}")
    public Salary calculateSalary(@PathVariable Long employeeId,
                                  @RequestParam("mois")
                                  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate mois) {
        return salaryService.calculateSalary(employeeId, mois);
    }

    @PostMapping("/generate-pay-slip/{employeeId}")
    public String generatePaySlip(@PathVariable Long employeeId,
                                  @RequestParam("mois")
                                  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate mois) {
        return salaryService.generatePayslipWithDetails(employeeId, mois);
    }
}

