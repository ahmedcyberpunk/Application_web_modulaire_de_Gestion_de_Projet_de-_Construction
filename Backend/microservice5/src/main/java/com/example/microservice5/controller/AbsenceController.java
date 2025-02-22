package com.example.microservice5.controller;


import com.example.microservice5.entity.Absence;
import com.example.microservice5.entity.AbsenceType;
import com.example.microservice5.service.AbsenceService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/absences")
@CrossOrigin("*")
public class AbsenceController {
    AbsenceService absenceService;


    @PostMapping("/add/{employeeId}")
    public Absence addAbsence(@RequestBody Absence absence,
                              @PathVariable Long employeeId) {
        return absenceService.affecterAbsenceEmployee(absence, employeeId);
    }


    @PutMapping("/update/{id}")
    public Absence updateAbsence(@PathVariable Long id,
                                 @RequestParam AbsenceType newType) {
        return absenceService.updateAbsenceType(id, newType);
    }


    @GetMapping("/all")
    public List<Absence> getAllAbsences() {
        return absenceService.getAllAbsences();
    }


    @GetMapping("/employee/{employeeId}")
    public List<Absence> getAbsencesByEmployee(@PathVariable Long employeeId) {
        return absenceService.getAbsencesByEmployee(employeeId);
    }


    @DeleteMapping("/delete/{id}")
    public void deleteAbsence(@PathVariable Long id) {
        absenceService.deleteAbsence(id);
    }
}
