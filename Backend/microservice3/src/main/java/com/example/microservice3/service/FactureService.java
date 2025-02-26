package com.example.microservice3.service;

import com.example.microservice3.entity.Facture;
import com.example.microservice3.repository.EcheanceRepository;
import com.example.microservice3.repository.FactureRepository;
import com.example.microservice3.repository.PaiementRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class FactureService implements IFactureService {

    private final FactureRepository factureRepository;
    private final EcheanceRepository echeanceRepository;
    private final PaiementRepository modePaiementRepository;

    @Override
    public Facture getFacture(Long id) {
        return factureRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Facture not found with id: " + id));
    }

    @Override
    public Facture addFacture(Facture facture) {
        return factureRepository.save(facture);
    }

    @Override
    public Facture updateFacture(Long id, Facture facture) {
        log.info("Updating facture with id: {}", id);
        return factureRepository.findById(id)
                .map(existingFacture -> {
                    existingFacture.setDateEmission(facture.getDateEmission());
                    existingFacture.setMontantTotal(facture.getMontantTotal());
                    existingFacture.setReference(facture.getReference());
                    existingFacture.setStatut(facture.getStatut());
                    // Ajoutez d'autres mises à jour nécessaires ici
                    return factureRepository.save(existingFacture);
                })
                .orElseThrow(() -> new EntityNotFoundException("Facture not found with id: " + id));
    }

    @Override
    public void deleteFacture(Long id) {
        if (factureRepository.existsById(id)) {
            factureRepository.deleteById(id);
        } else {
            throw new RuntimeException("Facture not found");
        }
    }
    @Override
    public List<Facture> getAllFactures() {
        return factureRepository.findAll();
    }

}
