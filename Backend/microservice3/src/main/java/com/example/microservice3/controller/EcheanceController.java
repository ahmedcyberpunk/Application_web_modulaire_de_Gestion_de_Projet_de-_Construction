package com.example.microservice3.controller;

import com.example.microservice3.entity.Echeance;
import com.example.microservice3.entity.Facture;
import com.example.microservice3.service.IEcheanceService;
import com.example.microservice3.repository.FactureRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/echeance")
@CrossOrigin(origins = "http://localhost:4200")
@AllArgsConstructor
public class EcheanceController {

    private final IEcheanceService echeanceService;
    private final FactureRepository factureRepository;  // Injection du repository FactureRepository

    @PostMapping("/addEcheance")
    public ResponseEntity<Object> addEcheance(@RequestBody Echeance echeance) {
        try {
            // Code existant
            Facture facture = factureRepository.findById(echeance.getFacture().getIdFacture())
                    .orElseThrow(() -> new RuntimeException("Facture non trouvée"));

            echeance.setFacture(facture);

            Echeance addedEcheance = echeanceService.addEcheance(echeance);
            return ResponseEntity.status(HttpStatus.CREATED).body(addedEcheance);
        } catch (Exception e) {
            // Ajout du message d'erreur pour faciliter le débogage
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erreur lors de l'ajout de l'échéance: " + e.getMessage());
        }
    }
    @GetMapping("/{id}")
    public Echeance getEcheance(@PathVariable Long id) {
        return echeanceService.getEcheance(id);
    }

    @DeleteMapping("/deleteEcheance/{id}")
    public void deleteEcheance(@PathVariable Long id) {
        echeanceService.deleteEcheance(id);
    }

    @GetMapping("/echeancesall")
    public List<Echeance> getAllEcheances() {
        return echeanceService.getallEcheance();
    }

    @PutMapping("/updateEcheance/{id}")
    public Echeance updateEcheance(@PathVariable Long id, @RequestBody Echeance echeance) {
        return echeanceService.updateEcheance(id, echeance);
    }

}
