package com.example.microservice5.controller;

import com.example.microservice5.entity.Employee;
import com.example.microservice5.entity.Performance;
import com.example.microservice5.service.PerfermonceService;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/performance")
@CrossOrigin("*")
public class PerformanceController {

    PerfermonceService performanceService;
/*
    // Ajouter une nouvelle performance
    @PostMapping
    public Performance addPerformance(@RequestBody Performance performance) {
        return performanceService.addPerformance(performance);
    }

    // Récupérer une performance par ID
    @GetMapping("/{id}")
    public Performance getPerformance(@PathVariable Long id) {
        return performanceService.getPerformance(id);
    }

    // Récupérer toutes les performances
    @GetMapping
    public List<Performance> getAllPerformances() {
        return performanceService.getAllPerformances();
    }

    // Mettre à jour une performance existante
    @PutMapping
    public Performance updatePerformance(@RequestBody Performance performance) {
        return performanceService.updatePerformance(performance);
    }

    // Supprimer une performance
    @DeleteMapping("/{id}")
    public void deletePerformance(@PathVariable Long id) {
        performanceService.deletePerformance(id);
    }
    */


    @PostMapping(path = "/add_performance/{employeeId}")
    Performance addPerformance(@PathVariable Long employeeId, @RequestBody Performance performance) {
        return performanceService.affecterPerformance(employeeId, performance);
    }


    @PutMapping(path = "/update_performance")
    Performance updatePerformance(@RequestBody Performance performance) {
        return performanceService.modifierPerformance(performance.getId(), performance);
    }

    @GetMapping(path = "/get_performances_by_employee/{employeeId}")
    List<Performance> getPerformancesByEmployee(@PathVariable Long employeeId) {
        return performanceService.getPerformancesByEmployeeId(employeeId);
    }


    @GetMapping(path = "/get_all_performances")
    public List<Map<String, Object>> getAllPerformances() {
        List<Performance> performances = performanceService.getAllPerformances();
        Map<Long, Map<String, Object>> groupedPerformances = new HashMap<>();

        for (Performance perf : performances) {
            Employee employee = perf.getEmployee();
            if (employee == null) continue;

            Long employeeId = employee.getId();

            if (!groupedPerformances.containsKey(employeeId)) {
                Map<String, Object> perfData = new HashMap<>();
                perfData.put("employeeId", employeeId);
                perfData.put("employeeName", employee.getNom());
                perfData.put("employeePrenom", employee.getPrenom());
                perfData.put("notes", new ArrayList<String>());
                perfData.put("dates", new ArrayList<String>());
                perfData.put("commentaires", new ArrayList<String>());

                groupedPerformances.put(employeeId, perfData);
            }

            Map<String, Object> perfData = groupedPerformances.get(employeeId);
            ((List<String>) perfData.get("notes")).add(String.valueOf(perf.getNote()));
            ((List<String>) perfData.get("dates")).add(perf.getDateEvaluation().toString());
            ((List<String>) perfData.get("commentaires")).add(perf.getCommentaire());
        }

        return new ArrayList<>(groupedPerformances.values());
    }




    @DeleteMapping(path = "/delete_performance/{id}")
    void deletePerformance(@PathVariable Long id) {
        performanceService.desaffecterPerformance(id);
    }


}
