package com.example.microservice2.service;

import com.example.microservice2.entity.Commande;
import com.example.microservice2.repository.CommandeRepo;
import com.example.microservice2.repository.FournisseursRepo;
import com.example.microservice2.repository.RessourceRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CommandeService {
    CommandeRepo commandeRepo;
    FournisseursRepo fournisseursRepo;
    RessourceRepo ressourceRepo;
Commande Ajouter(Commande commande){return commandeRepo.save(commande);};
public void Modifier(Commande commande){if (commandeRepo.existsById((long)
commande.getIdCommande())){commandeRepo.save(commande);}
else { throw new RuntimeException("Commande introuvable");}};
public void Supprimer(long id){if (commandeRepo.existsById((long) id))
{commandeRepo.deleteById( id);}
else { throw new RuntimeException("Commande introuvable");}
};
    public Commande getCommande(long id) {
        if (commandeRepo.existsById(id)) {
            return commandeRepo.findById(id).get();
        } else {
            throw new RuntimeException("Commande introuvable");
        }
    }


}
