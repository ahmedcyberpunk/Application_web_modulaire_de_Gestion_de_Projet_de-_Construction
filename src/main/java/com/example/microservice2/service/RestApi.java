package com.example.microservice2.service;

import com.example.microservice2.entity.Commande;
import com.example.microservice2.entity.Fournisseurs;
import com.example.microservice2.entity.Ressource;
import com.example.microservice2.repository.CommandeRepo;
import com.example.microservice2.repository.FournisseursRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class RestApi {
    @Autowired
    CommandeService commandeService;
    @Autowired
    FournisseurService fournisseurService;
    @Autowired
    RessourceService ressourceService;
    FournisseursRepo fournisseursRepo;
    CommandeRepo commandeRepo;



@PostMapping("/commandeajouter")
    public Commande Ajouter(@RequestBody Commande commande) {
        return commandeService.Ajouter(commande);
    }

    @PutMapping("/commandeupdate")
    public void Modifier(@RequestBody Commande commande) {
        commandeService.Modifier(commande);
    }

    @DeleteMapping("/commandesupprimer/{id}")
    public void Supprimer(@PathVariable long id) {
        commandeService.Supprimer(id);
    }

    @GetMapping("/commandeafficher/{id}")
    public Commande getCommande(@PathVariable long id) {
        return commandeService.getCommande(id);
    }

    @PostMapping("/ajouterfournisseur")
    public Fournisseurs Adder(@RequestBody Fournisseurs fournisseur) {
        return fournisseurService.Adder(fournisseur);
    }

    @PostMapping("/Resourceajouter")
    Ressource Adder(@RequestBody Ressource ressource) {
        return ressourceService.adderr(ressource);
    }
    @GetMapping("/fournisseurafficher/{id}")
    public Fournisseurs getFournisseur(@PathVariable long id) {
        return fournisseurService.getFournisseur(id);
    }
    @DeleteMapping("/fournisseursupprimer/{id}")
    public void  Supprimerfournisseur(@PathVariable long id) {
        fournisseurService.Supprimerfournisseur(id);
    }
    @PutMapping("/fournisseurupdate")
    public void Modifier(@RequestBody Fournisseurs fournisseur) {
        fournisseurService.Modifier(fournisseur);
    }
    @DeleteMapping("/ressourcesupprimer/{id}")
    public void Supprimerressource(@PathVariable long id) {
        ressourceService.SupprimerRessource(id);
    }
    @PutMapping("/ressourceupdate")
    public void Modifierressource(@RequestBody Ressource ressource) {
        ressourceService.ModifierRessource(ressource);
    }
    @GetMapping("/ressourceafficher/{id}")
    public Ressource getRessource(@PathVariable long id) {
        return ressourceService.getRessource(id);
    }
}

