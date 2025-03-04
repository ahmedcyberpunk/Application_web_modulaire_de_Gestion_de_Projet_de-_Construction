package com.example.microservice3.service;

import com.example.microservice3.entity.Echeance;
import com.example.microservice3.entity.Facture;
import com.example.microservice3.entity.Paiement;
import com.example.microservice3.entity.StatutEcheance;
import com.example.microservice3.repository.EcheanceRepository;
import com.example.microservice3.repository.FactureRepository;
import com.example.microservice3.repository.PaiementRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

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
    /*
    @Override

    public BigDecimal calculerMontantRestant(Long factureId) {
        // Récupérer la facture
        Optional<Facture> optionalFacture = factureRepository.findById(factureId);
        if (!optionalFacture.isPresent()) {
            return BigDecimal.ZERO; // Si la facture n'existe pas
        }

        Facture facture = optionalFacture.get();
        BigDecimal montantTotal = new BigDecimal(facture.getMontantTotal());

        // Récupérer tous les paiements liés à cette facture
        List<Paiement> paiements = modePaiementRepository.findByFactureId(factureId);

        // Calculer le montant total payé
        BigDecimal montantPaye = paiements.stream()
                .map(Paiement::getMontantPaye)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Calculer et retourner le montant restant à payer
        return montantTotal.subtract(montantPaye);
    }*/

@Override
    public BigDecimal calculateTotalAmountToPay(Long factureId) {
        // Retrieve the facture by ID
        Facture facture = factureRepository.findById(factureId)
                .orElseThrow(() -> new RuntimeException("Facture not found"));

        // Initialize total amount to 0
        BigDecimal total = BigDecimal.ZERO;

        // Loop through associated echeances and sum montantDu for in-progress status
        List<Echeance> echeances = facture.getEcheances();
        for (Echeance echeance : echeances) {
            if (echeance.getStatut() == StatutEcheance.EN_ATTENTE) { // Assuming IN_PROGRESS is the in-progress status
                total = total.add(echeance.getMontantDu());
            }
        }

        return total;
    }





}
