package com.example.microservice3.controller;

import com.example.microservice3.entity.Echeance;
import com.example.microservice3.service.IEcheanceService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/echeance")
@AllArgsConstructor
public class EcheanceController {

    private final IEcheanceService echeanceService;

    @PostMapping("/addEcheance")
    public Echeance addEcheance(@RequestBody Echeance echeance) {
        return echeanceService.addEcheance(echeance);
    }

    @GetMapping("/{id}")
    public Echeance getEcheance(@PathVariable Long id) {
        return echeanceService.getEcheance(id);
    }

    @DeleteMapping("/deleteEcheance/{id}")
    public void deleteEcheance(@PathVariable Long id) {
        echeanceService.deleteEcheance(id);
    }

    @PutMapping("/updateEcheance/{id}")
    public Echeance updateEcheance(@PathVariable Long id, @RequestBody Echeance updatedEcheance) {
        return echeanceService.updateEcheance(id, updatedEcheance);
    }
}
