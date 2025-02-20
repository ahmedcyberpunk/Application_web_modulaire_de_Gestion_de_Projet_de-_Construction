package com.example.microservice2.service;

import com.example.microservice2.entity.Fournisseurs;
import com.example.microservice2.repository.CommandeRepo;
import com.example.microservice2.repository.FournisseursRepo;
import com.example.microservice2.repository.RessourceRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
@Service
public class FournisseurService {
    CommandeRepo commandeRepo;
    FournisseursRepo fournisseursRepo;
    RessourceRepo ressourceRepo;
    public FournisseurService(CommandeRepo commandeRepo, FournisseursRepo fournisseursRepo, RessourceRepo ressourceRepo) {
        this.commandeRepo = commandeRepo;
        this.fournisseursRepo = fournisseursRepo;
        this.ressourceRepo = ressourceRepo;
    }
    Fournisseurs Adder(Fournisseurs fournisseurs){
        return fournisseursRepo.save(fournisseurs);
    }
    public Fournisseurs getFournisseur(long id){return fournisseursRepo.findById(id).get();}
    public void Supprimerfournisseur(long id){fournisseursRepo.deleteById(id);}
    public void Modifier(Fournisseurs fournisseurs){fournisseursRepo.save(fournisseurs);}

}
