package com.example.microservice3.service;

import com.example.microservice3.entity.Facture;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface IFactureService {
    Facture getFacture(Long id);
    Facture addFacture(Facture facture);
    Facture updateFacture(Long id, Facture facture);

    void deleteFacture(Long id);
    List<Facture> getAllFactures();
 //   BigDecimal calculerMontantRestant(Long factureId);
 BigDecimal calculateTotalAmountToPay(Long factureId);
    String getMessageForRemainingAmount(Long factureId);
    List<Map<String, Object>> getMontantParMois();
}
