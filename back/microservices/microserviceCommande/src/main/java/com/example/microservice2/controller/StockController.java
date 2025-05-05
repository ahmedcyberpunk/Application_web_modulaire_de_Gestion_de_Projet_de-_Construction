package com.example.microservice2.controller;

import com.example.microservice2.entity.Stock;
import com.example.microservice2.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/stock")
@RequiredArgsConstructor
@CrossOrigin("*")
public class StockController {

    private final StockService stockService;

    // ✅ Réceptionner une commande et ajouter des quantités au stock
    @PostMapping("/reception/{ressourceId}")
    public ResponseEntity<String> receptionnerCommande(@PathVariable Long ressourceId, @RequestParam int quantiteAjoutee) {
        stockService.receptionnerCommande(ressourceId, quantiteAjoutee);
        return ResponseEntity.ok("Stock mis à jour après réception de la commande.");
    }

    // ✅ Diminuer le stock après une commande
    @PostMapping("/commandee/{ressourceId}")
    public ResponseEntity<String> traiterCommande(@PathVariable Long ressourceId, @RequestParam int quantiteDemandee) {
        try {
            stockService.traiterCommande(ressourceId, quantiteDemandee);
            return ResponseEntity.ok("Stock mis à jour après la commande.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/commandee/stock/{ressourceId}")
    public ResponseEntity<Stock> getStockAfterCommande(@PathVariable Long ressourceId) {
        Stock stock = stockService.findByRessourceId(ressourceId);
        if (stock == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(stock);
    }

}
