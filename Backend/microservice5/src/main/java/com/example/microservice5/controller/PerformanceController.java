package com.example.microservice3.controller;

import com.example.microservice5.entity.Performance;
import com.example.microservice5.service.PerfermonceService;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class PerformanceController {

    PerfermonceService performanceService;

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
}
