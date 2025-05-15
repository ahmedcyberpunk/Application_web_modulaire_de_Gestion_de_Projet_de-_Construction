package com.example.microservice2.controller;

import com.example.microservice2.entity.CartRessource;
import com.example.microservice2.service.CartRessourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart-ressources")
@CrossOrigin("*")
public class CartRessourceRestController {

    private final CartRessourceService cartRessourceService;

    @Autowired
    public CartRessourceRestController(CartRessourceService cartRessourceService) {
        this.cartRessourceService = cartRessourceService;
    }

    // Récupérer toutes les ressources du panier
    @GetMapping("/all")
    public ResponseEntity<List<CartRessource>> getAllCartRessources() {
        List<CartRessource> cartRessources = cartRessourceService.findAll();
        for (CartRessource cartRessource : cartRessources) {
            // Vérifiez que la ressource est bien présente
            if (cartRessource.getRessource() != null) {
                // Inclure les informations de ressource comme le nomProduit et prixUnitaire
                cartRessource.setRessource(cartRessource.getRessource()); // Si besoin
            }
        }
        return new ResponseEntity<>(cartRessources, HttpStatus.OK);
    }


    // Supprimer une ressource du panier par ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCartRessource(@PathVariable Long id) {
        cartRessourceService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("/vider-panier")
    public ResponseEntity<?> viderPanier() {
        cartRessourceService.viderPanier();
        return ResponseEntity.ok("Panier vidé avec succès !");
    }

}
