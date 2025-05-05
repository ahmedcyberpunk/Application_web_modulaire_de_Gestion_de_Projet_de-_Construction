package com.example.microservice2.service;

import com.example.microservice2.entity.Ressource;

import java.util.List;

public interface RessourceInter {
    Ressource adderr(Ressource ressource);
    public Ressource getRessource(long id);
    public void SupprimerRessource(long idProduit);
    public void ModifierRessource(Ressource ressource);
    public List<Ressource> getAllRessource();
}
