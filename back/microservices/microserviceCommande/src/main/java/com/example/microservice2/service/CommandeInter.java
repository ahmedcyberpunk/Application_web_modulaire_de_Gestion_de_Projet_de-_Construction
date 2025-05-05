package com.example.microservice2.service;

import com.example.microservice2.entity.CartRessource;
import com.example.microservice2.entity.Commande;

import java.util.List;

public interface CommandeInter {
    public Commande Ajouter(Commande commande);
    public void Modifier(Commande commande);
    public void Supprimer(long id);
    public Commande getCommande(long id);
    public List<Commande> getAllCommande();
}
