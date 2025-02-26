package com.example.microservice3.service;

import com.example.microservice3.entity.Echeance;
import com.example.microservice3.repository.EcheanceRepository;
import com.example.microservice3.repository.FactureRepository;
import com.example.microservice3.repository.PaiementRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class EcheanceService implements IEcheanceService {

    private final FactureRepository factureRepository;
    private final EcheanceRepository echeanceRepository;
    private final PaiementRepository modePaiementRepository;

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
    public List<Echeance> getallEcheance() {
        return echeanceRepository.findAll();
    }
    @Override
    public Echeance updateEcheance(Long id, Echeance echeance) {
        return echeanceRepository.findById(id).map(existingEcheance -> {
            existingEcheance.setDateLimite(echeance.getDateLimite());
            existingEcheance.setMontantDu(echeance.getMontantDu());
            existingEcheance.setStatut(echeance.getStatut());
            return echeanceRepository.save(existingEcheance);
        }).orElseThrow(() -> new RuntimeException("Echeance not found with id: " + id));
    }


}
