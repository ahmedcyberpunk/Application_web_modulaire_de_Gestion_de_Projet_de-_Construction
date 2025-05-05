package com.example.microservice2.service;

import com.example.microservice2.entity.Fournisseurs;

import java.util.List;

public interface FournisseurInter {
    Fournisseurs Adder(Fournisseurs fournisseurs);
    Fournisseurs getFournisseur(long id);
    void Supprimerfournisseur(long id);
    void Modifier(Fournisseurs fournisseurs);
    public List<Fournisseurs> getAllFournisseur();
}
