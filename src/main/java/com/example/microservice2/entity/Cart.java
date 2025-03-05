package com.example.microservice2.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<CartRessource> cartRessources;

    private double totalPrice;

    // Calculer le prix total en fonction des produits et des quantités
    public void calculateTotalPrice() {
        this.totalPrice = cartRessources.stream()
                .mapToDouble(CartRessource::getTotalPrice)
                .sum();
    }

    // Ajouter une ressource au panier
    public void addRessource(Ressource ressource, int quantite) {
        CartRessource cartRessource = new CartRessource();
        cartRessource.setCart(this);
        cartRessource.setRessource(ressource);
        cartRessource.setQuantite(quantite);
        this.cartRessources.add(cartRessource);
        calculateTotalPrice();
    }

    // Retirer une ressource du panier
    public void removeRessource(CartRessource cartRessource) {
        this.cartRessources.remove(cartRessource);
        calculateTotalPrice();
    }
}
