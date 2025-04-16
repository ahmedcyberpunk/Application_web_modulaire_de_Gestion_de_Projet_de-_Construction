package com.example.microservice3.controller;

import com.example.microservice3.entity.Facture;
import com.example.microservice3.service.IFactureService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/facture")
@AllArgsConstructor
public class FactureController {

 IFactureService factureService;

    @PostMapping("/addfacture")
    public Facture addFacture(@RequestBody Facture facture) {
        return factureService.addFacture(facture);
    }

    @GetMapping("/{id}")
    public Facture getFacture(@PathVariable Long id) {
        return factureService.getFacture(id);
    }

    @PutMapping("/updatefacture")
    public Facture updateFacture(@RequestBody Facture facture) {
        factureService.updateFacture(facture);
        return facture;
    }

    @DeleteMapping("/deletefacture/{id}")
    public void deleteFacture(@PathVariable Long id) {
        factureService.deleteFacture(id);
    }
}
