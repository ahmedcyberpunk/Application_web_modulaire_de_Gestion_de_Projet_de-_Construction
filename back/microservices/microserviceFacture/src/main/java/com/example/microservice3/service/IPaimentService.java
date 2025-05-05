package com.example.microservice3.service;
import com.example.microservice3.entity.Paiement;

import java.util.List;

public interface IPaimentService {
    public List<Paiement> getAllModesPaiement();
    Paiement getModePaiement(Integer id);
    Paiement addModePaiement(Paiement modePaiement,int nbre);

    void deleteModePaiement(Integer id);
    Paiement updatePaiement(Integer id, Paiement paiement);


}
