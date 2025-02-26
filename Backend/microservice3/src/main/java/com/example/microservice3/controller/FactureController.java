package com.example.microservice3.controller;

import com.example.microservice3.entity.Facture;
import com.example.microservice3.service.IFactureService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
}
