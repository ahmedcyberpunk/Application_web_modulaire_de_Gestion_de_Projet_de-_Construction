package com.example.microservice3.service;

import com.example.microservice3.entity.Facture;

public interface IFactureService {
    Facture getFacture(Long id);
    Facture addFacture(Facture facture);
    void updateFacture(Facture facture);
    void deleteFacture(Long id);
}
