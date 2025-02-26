package com.example.microservice3.service;

import com.example.microservice3.entity.Echeance;

import java.util.List;

public interface IEcheanceService {
    Echeance addEcheance(Echeance echeance);
    void deleteEcheance(Long id);
    Echeance getEcheance(Long id);
    List<Echeance> getallEcheance();
    Echeance updateEcheance(Long id, Echeance echeance);
}
