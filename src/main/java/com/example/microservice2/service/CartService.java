package com.example.microservice2.service;

import com.example.microservice2.entity.Cart;
import com.example.microservice2.entity.CartRessource;
import com.example.microservice2.entity.Ressource;
import com.example.microservice2.repository.CartRepo;
import com.example.microservice2.repository.RessourceRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CartService implements Cartinter {

    private final CartRepo cartRepo;
    private final RessourceRepo ressourceRepo;

    // Créer un panier vide
    public Cart createCart() {
        Cart cart = new Cart();
        return cartRepo.save(cart);
    }

    // Ajouter un produit au panier
    public Cart addRessourceToCart(Long cartId, Long ressourceId, int quantite) {
        Optional<Cart> cartOptional = cartRepo.findById(cartId);
        Optional<Ressource> ressourceOptional = ressourceRepo.findById(ressourceId);

        if (cartOptional.isPresent() && ressourceOptional.isPresent()) {
            Cart cart = cartOptional.get();
            Ressource ressource = ressourceOptional.get();

            // Vérifier si la ressource est déjà dans le panier
            CartRessource cartRessource = cart.getCartRessources().stream()
                    .filter(cr -> cr.getRessource().getIdProduit().equals(ressourceId))
                    .findFirst()
                    .orElse(null);

            if (cartRessource != null) {
                // Si la ressource est déjà dans le panier, on met à jour la quantité
                cartRessource.setQuantite(cartRessource.getQuantite() + quantite);
            } else {
                // Sinon, on l'ajoute au panier
                CartRessource newCartRessource = new CartRessource();
                newCartRessource.setCart(cart);
                newCartRessource.setRessource(ressource);
                newCartRessource.setQuantite(quantite);
                cart.getCartRessources().add(newCartRessource);
            }
            cart.calculateTotalPrice(); // Calculer le prix total du panier
            cartRepo.save(cart); // Sauvegarder le panier mis à jour
            return cart;
        }
        throw new RuntimeException("Cart or Ressource not found");
    }

    // Supprimer un produit du panier
    public Cart removeRessourceFromCart(Long cartId, Long ressourceId) {
        Optional<Cart> cartOptional = cartRepo.findById(cartId);
        Optional<Ressource> ressourceOptional = ressourceRepo.findById(ressourceId);

        if (cartOptional.isPresent() && ressourceOptional.isPresent()) {
            Cart cart = cartOptional.get();
            Ressource ressource = ressourceOptional.get();

            // Récupérer la CartRessource correspondante
            CartRessource cartRessource = cart.getCartRessources().stream()
                    .filter(cr -> cr.getRessource().getIdProduit().equals(ressourceId))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("CartRessource not found"));

            // Retirer la ressource du panier
            cart.getCartRessources().remove(cartRessource);
            cart.calculateTotalPrice(); // Calculer le prix total après suppression
            cartRepo.save(cart); // Sauvegarder le panier mis à jour
            return cart;
        }
        throw new RuntimeException("Cart or Ressource not found");
    }

    // Obtenir un panier par son ID
    public Cart getCart(Long id) {
        return cartRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Panier non trouvé"));
    }
}
