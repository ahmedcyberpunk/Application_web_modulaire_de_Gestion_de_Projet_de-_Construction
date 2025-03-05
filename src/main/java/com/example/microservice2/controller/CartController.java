package com.example.microservice2.controller;

import com.example.microservice2.entity.Cart;
import com.example.microservice2.service.CartService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@AllArgsConstructor
@CrossOrigin("*")
public class CartController {

    private final CartService cartService;

    // Créer un panier
    @PostMapping("/create")
    public ResponseEntity<Cart> createCart() {
        Cart cart = cartService.createCart();
        return ResponseEntity.ok(cart);
    }

    // Ajouter un produit au panier
    @PostMapping("/add/{cartId}/{ressourceId}/{quantite}")
    public ResponseEntity<Cart> addRessourceToCart(@PathVariable Long cartId, @PathVariable Long ressourceId, @PathVariable int quantite) {
        try {
            Cart cart = cartService.addRessourceToCart(cartId, ressourceId, quantite);
            return ResponseEntity.ok(cart);  // Renvoi du panier mis à jour
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(null);  // Si le panier ou la ressource n'est pas trouvé
        }
    }

    // Supprimer un produit du panier
    @DeleteMapping("/remove/{cartId}/{ressourceId}")
    public ResponseEntity<Cart> removeRessourceFromCart(@PathVariable Long cartId, @PathVariable Long ressourceId) {
        try {
            Cart updatedCart = cartService.removeRessourceFromCart(cartId, ressourceId);
            return ResponseEntity.ok(updatedCart);  // Renvoi du panier mis à jour après suppression
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(null);  // Si le panier ou la ressource n'est pas trouvé
        }
    }

    // Obtenir un panier
    @GetMapping("/{cartId}")
    public ResponseEntity<String> getCart(@PathVariable Long cartId) {
        Cart cart = cartService.getCart(cartId);

        // Si nécessaire, convertir en JSON avant d'envoyer la réponse
        try {
            String jsonResponse = new ObjectMapper().writeValueAsString(cart);
            return ResponseEntity.ok(jsonResponse);
        } catch (JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur lors de la sérialisation JSON");
        }
    }

}
