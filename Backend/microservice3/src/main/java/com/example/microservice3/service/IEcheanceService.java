package com.example.microservice3.service;

import com.example.microservice3.entity.Echeance;

public interface IEcheanceService {
    Echeance addEcheance(Echeance echeance);
    void deleteEcheance(Long id);
    Echeance getEcheance(Long id);
    Echeance updateEcheance(Long id, Echeance updatedEcheance);
}
