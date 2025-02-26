package com.example.microservice3.service;

import com.example.microservice3.entity.Facture;

import java.util.List;

public interface IFactureService {
    Facture getFacture(Long id);
    Facture addFacture(Facture facture);
    Facture updateFacture(Long id, Facture facture);

    void deleteFacture(Long id);
    List<Facture> getAllFactures();

}
