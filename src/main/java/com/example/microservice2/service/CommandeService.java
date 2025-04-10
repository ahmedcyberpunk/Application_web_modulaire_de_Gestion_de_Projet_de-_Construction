package com.example.microservice2.service;

import com.example.microservice2.entity.Commande;
import com.example.microservice2.entity.EtatCommande;
import com.example.microservice2.repository.CommandeRepo;
import com.example.microservice2.repository.FournisseursRepo;
import com.example.microservice2.repository.RessourceRepo;
import jakarta.mail.MessagingException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service

public class CommandeService  implements CommandeInter{

    CommandeRepo commandeRepo;
    FournisseursRepo fournisseursRepo;
    RessourceRepo ressourceRepo;
    private final MailService mailService;

    public CommandeService(CommandeRepo commandeRepo, FournisseursRepo fournisseursRepo, RessourceRepo ressourceRepo, MailService mailService) {
        this.commandeRepo = commandeRepo;
        this.fournisseursRepo = fournisseursRepo;
        this.ressourceRepo = ressourceRepo;
        this.mailService = mailService;
    }



    public Commande Ajouter(Commande commande) {
        commande.setEtatCommande(EtatCommande.EN_COURS);
        Commande nouvelleCommande = commandeRepo.save(commande);

        // 📧 Envoyer un email après la commande
        String destinataire = "mohamedkhaled.tebourbi@esprit.tn"; // À remplacer par l'email réel du client
        String sujet = "Confirmation de votre commande";
        String message = "<h2>Merci pour votre commande !</h2>"
                + "<p>Votre commande a été bien reçue.</p>"
                + "<p>Détails :</p>"
                + "<pre>" + commande.getRessourcesJson() + "</pre>";

        try {
            mailService.envoyerEmailCommande(destinataire, sujet, message);
        } catch (MessagingException e) {
            System.err.println("Erreur d'envoi de l'email : " + e.getMessage());
        }

        return nouvelleCommande;
    }
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
    public List<Commande> getAllCommande() {

     return commandeRepo.findAll();


}
    // ✅ Réceptionner une commande (mettre à jour la date de réception)
    public void receptionnerCommande(Long idCommande) {
        Optional<Commande> commandeOpt = commandeRepo.findById(idCommande);
        if (commandeOpt.isPresent()) {
            Commande commande = commandeOpt.get();
            commande.setDateReception(new Date()); // Met la date actuelle
            commande.setEtatCommande(EtatCommande.RECUE); // Marquer comme reçue
            commandeRepo.save(commande);
        } else {
            throw new RuntimeException("Commande introuvable");
        }
    }

    // ✅ Lister les commandes en retard
    public List<Commande> getCommandesEnRetard() {
        return commandeRepo.findAll().stream()
                .filter(commande -> commande.getDateLivraisonPrevue() != null &&
                        commande.getDateReception() == null &&
                        new Date().after(commande.getDateLivraisonPrevue()))
                .toList();
    }

    // ✅ Vérifier et mettre à jour l'état des commandes
    public void verifierEtMettreAJourEtatCommandes() {
        List<Commande> commandes = commandeRepo.findAll();
        for (Commande commande : commandes) {
            if (commande.getDateReception() != null) {
                commande.setEtatCommande(EtatCommande.RECUE);
            } else if (commande.getDateLivraisonPrevue() != null && new Date().after(commande.getDateLivraisonPrevue())) {
                commande.setEtatCommande(EtatCommande.EN_RETARD);
            }
            commandeRepo.save(commande);
        }
    }
}
