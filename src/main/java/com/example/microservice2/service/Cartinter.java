package com.example.microservice2.service;

import com.example.microservice2.entity.Cart;

public interface Cartinter {
    public Cart getCart(Long id);
    public Cart removeRessourceFromCart(Long cartId, Long ressourceId);
    public Cart addRessourceToCart(Long cartId, Long ressourceId, int quantite);
    public Cart createCart();
}
