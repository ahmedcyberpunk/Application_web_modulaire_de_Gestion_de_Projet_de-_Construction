package com.example.microservice3.service;

import com.example.microservice3.entity.Echeance;
import com.example.microservice3.repository.EcheanceRepository;
import com.example.microservice3.repository.FactureRepository;
import com.example.microservice3.repository.ModePaiementRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class EcheanceService implements IEcheanceService {

    private final FactureRepository factureRepository;
    private final EcheanceRepository echeanceRepository;
    private final ModePaiementRepository modePaiementRepository;

    @Override
    public Echeance addEcheance(Echeance echeance) {
        return echeanceRepository.save(echeance);
    }

    @Override
    public void deleteEcheance(Long id) {
        if (echeanceRepository.existsById(id)) {
            echeanceRepository.deleteById(id);
        } else {
            throw new RuntimeException("Echeance not found with id: " + id);
        }
    }

    @Override
    public Echeance getEcheance(Long id) {
        return echeanceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Echeance not found with id: " + id));
    }

    @Override
    public Echeance updateEcheance(Long id, Echeance updatedEcheance) {
        return echeanceRepository.findById(id)
                .map(existingEcheance -> {
                    existingEcheance.setMontantEcheance(updatedEcheance.getMontantEcheance());
                    existingEcheance.setDateEcheance(updatedEcheance.getDateEcheance());
                    existingEcheance.setMontantInteret(updatedEcheance.getMontantInteret());
                    existingEcheance.setMontantTotalAPayer(updatedEcheance.getMontantTotalAPayer());
                    existingEcheance.setStatut(updatedEcheance.getStatut());
                    existingEcheance.setTauxInteret(updatedEcheance.getTauxInteret());
                    existingEcheance.setFacture(updatedEcheance.getFacture());
                    existingEcheance.setModePaiement(updatedEcheance.getModePaiement());
                    return echeanceRepository.save(existingEcheance);
                })
                .orElseThrow(() -> new RuntimeException("Echeance not found with id: " + id));
    }
}
