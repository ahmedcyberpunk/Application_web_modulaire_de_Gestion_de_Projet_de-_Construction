package com.example.microservice3.controller;

import com.example.microservice3.entity.Paiement;
import com.example.microservice3.service.PaiementService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/modepaiement")
@AllArgsConstructor
public class PaimentController {

    private final PaiementService modePaiementService;

    @PostMapping("/add")
    public ResponseEntity<Paiement> addModePaiement(@RequestBody Paiement modePaiement) {
        Paiement addedModePaiement = modePaiementService.addModePaiement(modePaiement);
        return ResponseEntity.ok(addedModePaiement);
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteModePaiement(@PathVariable Integer id) {
        modePaiementService.deleteModePaiement(id);
        return ResponseEntity.ok("Mode de paiement supprimé avec succès");
    }

    @GetMapping("/all")
    public ResponseEntity<List<Paiement>> getAllModesPaiement() {
        List<Paiement> modesPaiement = modePaiementService.getAllModesPaiement();
        return ResponseEntity.ok(modesPaiement);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Paiement> getModePaiement(@PathVariable Integer id) {
        Paiement modePaiement = modePaiementService.getModePaiement(id);
        return ResponseEntity.ok(modePaiement);
    }
}
