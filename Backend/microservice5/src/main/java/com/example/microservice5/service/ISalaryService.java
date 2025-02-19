package com.example.microservice5.service;

import com.example.microservice5.entity.Salary;

import java.time.LocalDate;

public interface ISalaryService {

    Salary calculateSalary(Long employeeId, LocalDate mois);
}
