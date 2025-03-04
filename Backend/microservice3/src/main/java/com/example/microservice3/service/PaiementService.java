package com.example.microservice3.service;

import com.example.microservice3.entity.Paiement;
import com.example.microservice3.repository.PaiementRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PaiementService implements IPaimentService {

    private final PaiementRepository modePaiementRepository;

    @Override
    public List<Paiement> getAllModesPaiement() {
        return modePaiementRepository.findAll();  // Retourner tous les modes de paiement
    }

    @Override
    public Paiement getModePaiement(Integer id) {
        return modePaiementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ModePaiement not found with id: " + id));
    }

    @Override
    public Paiement addModePaiement(Paiement modePaiement) {
        return modePaiementRepository.save(modePaiement);  // Ajouter un nouveau mode de paiement
    }



    @Override
    public void deleteModePaiement(Integer id) {
        if (modePaiementRepository.existsById(id)) {
            modePaiementRepository.deleteById(id);
        } else {
            throw new RuntimeException("ModePaiement not found");
        }
    }
    public Paiement updatePaiement(Integer id, Paiement paiement) {
        if (!modePaiementRepository.existsById(id)) {
            throw new RuntimeException("Paiement not found with id: " + id);
        }

        Paiement existingPaiement = modePaiementRepository.findById(id).orElseThrow();

        existingPaiement.setMontantPaye(paiement.getMontantPaye());
        existingPaiement.setDatePaiement(paiement.getDatePaiement());
        existingPaiement.setModePaiement(paiement.getModePaiement());
        existingPaiement.setFacture(paiement.getFacture());

        return modePaiementRepository.save(existingPaiement);
    }

}
