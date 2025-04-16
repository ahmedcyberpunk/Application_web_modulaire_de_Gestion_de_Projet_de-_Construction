package com.example.microservice3.controller;

import com.example.microservice3.entity.Facture;
import com.example.microservice3.service.IFactureService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/facture")
@CrossOrigin(origins = "http://localhost:4200")
@AllArgsConstructor
public class FactureController {

 IFactureService factureService;
    @CrossOrigin(origins = "http://localhost:4200")

    @PostMapping("/addfacture")
    public Facture addFacture(@RequestBody Facture facture) {
        return factureService.addFacture(facture);
    }

    @GetMapping("/{id}")
    public Facture getFacture(@PathVariable Long id) {
        return factureService.getFacture(id);
    }

    @PutMapping("/updatefacture/{id}")
    public Facture updateFacture(@PathVariable Long id, @RequestBody Facture facture) {
        return factureService.updateFacture(id, facture);
    }


    @GetMapping( "/facturesall")
    public List<Facture> getAllFactures() {
        return factureService.getAllFactures();
    }


    @DeleteMapping("/deletefacture/{id}")
    public void deleteFacture(@PathVariable Long id) {
        factureService.deleteFacture(id);
    }
   /* @GetMapping("/{id}/montant-restant")
    public ResponseEntity<BigDecimal> calculerMontantRestant(@PathVariable Long id) {
        BigDecimal montantRestant = factureService.calculerMontantRestant(id);
        return ResponseEntity.ok(montantRestant);
}*/

    @GetMapping("/{factureId}/totalToPay")
    public BigDecimal getTotalAmountToPay(@PathVariable Long factureId) {
        return factureService.calculateTotalAmountToPay(factureId);
    }

    // Endpoint to get a message with the remaining total to pay for a specific facture
    @GetMapping("/{factureId}/remainingMessage")
    public String getRemainingMessage(@PathVariable Long factureId) {
        return factureService.getMessageForRemainingAmount(factureId);
    }
    @GetMapping("/montant-par-mois")
    public List<Map<String, Object>> getMontantParMois() {
        return factureService.getMontantParMois();  // Appel de la méthode du service
    }
}

