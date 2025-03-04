package com.example.microservice3.controller;

import com.example.microservice3.entity.Paiement;
import com.example.microservice3.service.PaiementService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/paiement")
@CrossOrigin(origins = "http://localhost:4200")
@AllArgsConstructor
public class PaimentController {

    private final PaiementService modePaiementService;

    @PostMapping("/addpaiement")
    public ResponseEntity<Paiement> addModePaiement(@RequestBody Paiement modePaiement) {
        Paiement addedModePaiement = modePaiementService.addModePaiement(modePaiement);
        return ResponseEntity.ok(addedModePaiement);
    }


    @DeleteMapping("/deletepaiement/{id}")
    public ResponseEntity<String> deletePaiement(@PathVariable("id") Integer id) {
        modePaiementService.deleteModePaiement(id);
        return new ResponseEntity<>("Paiement supprimé avec succès", HttpStatus.NO_CONTENT);
    }

    @GetMapping("/paiementall")
    public ResponseEntity<List<Paiement>> getAllModesPaiement() {
        List<Paiement> modesPaiement = modePaiementService.getAllModesPaiement();
        return ResponseEntity.ok(modesPaiement);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Paiement> getModePaiement(@PathVariable Integer id) {
        Paiement modePaiement = modePaiementService.getModePaiement(id);
        return ResponseEntity.ok(modePaiement);
    }
    @PutMapping("/updatepaiement/{id}")
    public ResponseEntity<Paiement> updatePaiement(@PathVariable("id") Integer id, @RequestBody Paiement paiement) {
        Paiement updatedPaiement = modePaiementService.updatePaiement(id, paiement);
        return new ResponseEntity<>(updatedPaiement, HttpStatus.OK);
    }
}
