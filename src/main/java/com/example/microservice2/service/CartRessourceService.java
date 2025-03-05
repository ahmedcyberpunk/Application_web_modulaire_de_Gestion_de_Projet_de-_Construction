package com.example.microservice2.service;

import com.example.microservice2.entity.CartRessource;
import com.example.microservice2.repository.CartRessourceRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartRessourceService {

    private final CartRessourceRepository cartRessourceRepository;

    @Autowired
    public CartRessourceService(CartRessourceRepository cartRessourceRepository) {
        this.cartRessourceRepository = cartRessourceRepository;
    }

    // Récupérer toutes les ressources du panier
    public List<CartRessource> findAll() {
        return cartRessourceRepository.findAll();
    }

    // Supprimer une ressource du panier par ID
    public void deleteById(Long id) {
        cartRessourceRepository.deleteById(id);
    }
    @Transactional
    public void viderPanier() {
        cartRessourceRepository.deleteAll();  // Supprime toutes les entrées
    }
}
