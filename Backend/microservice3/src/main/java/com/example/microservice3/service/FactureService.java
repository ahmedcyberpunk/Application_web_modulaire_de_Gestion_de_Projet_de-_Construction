package com.example.microservice3.service;

import com.example.microservice3.entity.Facture;
import com.example.microservice3.repository.EcheanceRepository;
import com.example.microservice3.repository.FactureRepository;
import com.example.microservice3.repository.ModePaiementRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class FactureService implements IFactureService {

    private final FactureRepository factureRepository;
    private final EcheanceRepository echeanceRepository;
    private final ModePaiementRepository modePaiementRepository;

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
    public void updateFacture(Facture facture) {
        if (factureRepository.existsById(facture.getId())) {
            factureRepository.save(facture);
        } else {
            throw new RuntimeException("Facture not found");
        }
    }

    @Override
    public void deleteFacture(Long id) {
        if (factureRepository.existsById(id)) {
            factureRepository.deleteById(id);
        } else {
            throw new RuntimeException("Facture not found");
        }
    }
}
