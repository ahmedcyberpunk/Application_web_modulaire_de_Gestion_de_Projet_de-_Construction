package com.example.microservice2.service;

import com.example.microservice2.entity.Ressource;
import com.example.microservice2.repository.CommandeRepo;
import com.example.microservice2.repository.FournisseursRepo;
import com.example.microservice2.repository.RessourceRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
@AllArgsConstructor
@Service
public class RessourceService {
    CommandeRepo commandeRepo;
    FournisseursRepo fournisseursRepo;
    RessourceRepo ressourceRepo;
    Ressource adderr(Ressource ressource){return ressourceRepo.save(ressource);}
    public Ressource getRessource(long id){return ressourceRepo.findById(id).get();}
    public void SupprimerRessource(long id){ressourceRepo.deleteById(id);}
    public void ModifierRessource(Ressource ressource){ressourceRepo.save(ressource);}
}
