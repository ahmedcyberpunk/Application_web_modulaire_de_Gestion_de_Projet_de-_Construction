package com.example.microservice2.service;

public interface Stockinter {
    public void receptionnerCommande(Long ressourceId, int quantiteAjoutee);
    public void traiterCommande(Long ressourceId, int quantiteDemandee);
}
