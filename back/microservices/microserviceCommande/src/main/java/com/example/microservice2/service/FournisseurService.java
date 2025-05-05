package com.example.microservice2.service;

import com.example.microservice2.entity.Fournisseurs;
import com.example.microservice2.repository.CommandeRepo;
import com.example.microservice2.repository.FournisseursRepo;
import com.example.microservice2.repository.RessourceRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class FournisseurService implements FournisseurInter {

    private final CommandeRepo commandeRepo;
    private final FournisseursRepo fournisseursRepo;
    private final RessourceRepo ressourceRepo;

    public Fournisseurs Adder(Fournisseurs fournisseurs) {
        return fournisseursRepo.save(fournisseurs);
    }

    public Fournisseurs getFournisseur(long id) {
        return fournisseursRepo.findById(id).orElseThrow(() -> new RuntimeException("Fournisseur non trouvé"));
    }

    public void Supprimerfournisseur(long id) {
        fournisseursRepo.deleteById(id);
    }

    public void Modifier(Fournisseurs fournisseurs) {
        fournisseursRepo.save(fournisseurs);
    }

    @Override
    public List<Fournisseurs> getAllFournisseur() {
        return fournisseursRepo.findAll();
    }
}
