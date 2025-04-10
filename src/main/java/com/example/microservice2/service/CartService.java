package com.example.microservice2.service;

import com.example.microservice2.entity.Cart;
import com.example.microservice2.entity.CartRessource;
import com.example.microservice2.entity.Ressource;
import com.example.microservice2.repository.CartRepo;
import com.example.microservice2.repository.RessourceRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public void removeRessourceFromCart(Long ressourceId) {
        // Trouver tous les paniers qui contiennent la ressource
        List<Cart> cartsContainingRessource = cartRepo.findAll().stream()
                .filter(cart -> cart.getCartRessources().stream()
                        .anyMatch(cr -> cr.getRessource().getIdProduit().equals(ressourceId)))
                .collect(Collectors.toList());

        // Pour chaque panier contenant cette ressource, supprimer la ressource
        for (Cart cart : cartsContainingRessource) {
            // Trouver la CartRessource associée à la ressource à supprimer
            CartRessource cartRessource = cart.getCartRessources().stream()
                    .filter(cr -> cr.getRessource().getIdProduit().equals(ressourceId))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Ressource not found in the cart"));

            // Retirer la ressource du panier
            cart.removeRessource(cartRessource);  // Utilise la méthode existante removeRessource dans Cart

            // Sauvegarder les changements dans le panier
            cartRepo.save(cart);
        }
    }



    // Obtenir un panier par son ID
    public Cart getCart(Long id) {
        return cartRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Panier non trouvé"));
    }
}
