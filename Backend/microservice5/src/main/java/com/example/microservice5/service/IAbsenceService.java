package com.example.microservice5.service;

import com.example.microservice5.entity.Absence;
import com.example.microservice5.entity.AbsenceType;

import java.util.List;

public interface IAbsenceService {
    Absence affecterAbsenceEmployee(Absence absence, Long employeeId);



    List<Absence> getAllAbsences();

    List<Absence> getAbsencesByEmployee(Long employeeId);

    void deleteAbsence(Long id);
}
