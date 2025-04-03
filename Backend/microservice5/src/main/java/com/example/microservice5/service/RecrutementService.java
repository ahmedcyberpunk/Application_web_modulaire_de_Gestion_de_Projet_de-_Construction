package com.example.microservice5.service;

import com.example.microservice5.entity.Recrutement;
import com.example.microservice5.repository.RecrutementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class RecrutementService {

    @Autowired
    private RecrutementRepository recrutementRepository;


    public Recrutement saveRecrutement(Recrutement recrutement) {
        return recrutementRepository.save(recrutement);
    }

    // Méthode pour récupérer tous les recrutements
    public List<Recrutement> getAllRecrutements() {
        return recrutementRepository.findAll();
    }

    // Méthode pour supprimer un recrutement par ID
    public void deleteRecrutement(Long id) {
        recrutementRepository.deleteById(id);
    }
    public Recrutement getRecrutementById(Long id) {
        return recrutementRepository.findById(id).orElse(null);
    }

    // Mettre à jour le statut (traité / non traité)
    public boolean updateRecrutementStatus(Long id, boolean status) {
        Optional<Recrutement> recrutementOptional = recrutementRepository.findById(id);
        if (recrutementOptional.isPresent()) {
            Recrutement recrutement = recrutementOptional.get();
            recrutement.setTraiter(status);
            recrutementRepository.save(recrutement);
            return true;
        }
        return false;
    }



}
