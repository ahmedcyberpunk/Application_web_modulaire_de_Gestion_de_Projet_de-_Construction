package com.example.microservice2.controller;

import com.example.microservice2.entity.*;

import com.example.microservice2.repository.CommandeRepo;
import com.example.microservice2.repository.FournisseursRepo;
import com.example.microservice2.repository.RessourceRepo;
import com.example.microservice2.service.CommandeService;
import com.example.microservice2.service.FournisseurService;
import com.example.microservice2.service.RessourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@RestController
@RequestMapping("/commande")
@CrossOrigin("*")
public class RestApi {
    @Autowired
    CommandeService commandeService;
    @Autowired
    FournisseurService fournisseurService;
    @Autowired
    RessourceService ressourceService;
    @Autowired
    FournisseursRepo fournisseursRepo;
    @Autowired
    CommandeRepo commandeRepo;
    @Autowired
    RessourceRepo ressourceRepo;



@PostMapping("/commandeajouter")
public Commande Ajouter(@RequestBody Commande commande){
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

    @PostMapping("/fournisseurajouter")
    public Fournisseurs ajouterFournisseur(@RequestBody Fournisseurs fournisseur) {
        System.out.println("Données reçues : " + fournisseur);
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
    public ResponseEntity<?> updateRessource(@RequestBody Ressource ressource) {
        System.out.println("Données reçues : " + ressource);
        try {
            ressourceService.ModifierRessource(ressource);
            return ResponseEntity.ok("Ressource mise à jour");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur interne");
        }
    }


    @GetMapping("/ressourceafficher/{id}")
    public Ressource getRessource(@PathVariable long id) {
        return ressourceService.getRessource(id);
    }

    @GetMapping("/commandeget")
    public ResponseEntity<List<Commande>> getAllCommandes() {
        List<Commande> commandes = commandeService.getAllCommande();
        return ResponseEntity.ok(commandes); // ✅ Bien retourner directement la liste
    }
    @GetMapping("/ressourceget")
    public ResponseEntity<List<Ressource>> getAllRessource() {
        List<Ressource> ressources = ressourceService.getAllRessource();
        return ResponseEntity.ok(ressources);
    }
    @GetMapping("/fournisseurget")
    public ResponseEntity<List<Fournisseurs>> getAllFournisseur() {
        List<Fournisseurs> fournisseurs = fournisseurService.getAllFournisseur();
        return ResponseEntity.ok(fournisseurs);
    }

    private static final String UPLOAD_DIR = "C:\\Users\\user\\Desktop\\imagee";

    @PostMapping("/uploadImage")
    public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body("Aucun fichier sélectionné");
            }

            // Générer un nom unique pour le fichier
            String fileName =  file.getOriginalFilename();
            String filePath = UPLOAD_DIR + fileName;

            // Crée le répertoire s'il n'existe pas
            File directory = new File(UPLOAD_DIR);
            if (!directory.exists()) {
                directory.mkdirs(); // Crée le répertoire
            }

            // Transférer le fichier vers le répertoire
            file.transferTo(new File(filePath));

            // Retourner un objet JSON avec l'URL du fichier téléchargé
            String fileUrl =  fileName; // URL du fichier

            return ResponseEntity.ok(new UploadResponse(fileUrl));
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur d'upload: " + e.getMessage());
        }
    }

    // Objet de réponse pour retourner l'URL du fichier téléchargé
    public static class UploadResponse {
        private String fileUrl;

        public UploadResponse(String fileUrl) {
            this.fileUrl = fileUrl;
        }

        public String getFileUrl() {
            return fileUrl;
        }

        public void setFileUrl(String fileUrl) {
            this.fileUrl = fileUrl;
        }

    }
    @GetMapping("/images/{fileName:.+}")
    public ResponseEntity<byte[]> getImage(@PathVariable String fileName) {
        try {
            Path imagePath = Paths.get(UPLOAD_DIR).resolve(fileName).normalize();
            if (Files.exists(imagePath)) {
                byte[] imageBytes = Files.readAllBytes(imagePath); // Lire le fichier image

                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG) // Type MIME correct
                        .body(imageBytes); // Retourne les données de l'image
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/ressources/{id}/updateStock")
    public ResponseEntity<Ressource> updateStock(@PathVariable Long id, @RequestBody Map<String, Integer> request) {
        Optional<Ressource> ressourceOpt =ressourceRepo.findById(id);
        if (ressourceOpt.isPresent()) {
            Ressource ressource = ressourceOpt.get();
            ressource.setQuantiteDisponible(request.get("quantiteDisponible"));
            ressourceRepo.save(ressource);
            return ResponseEntity.ok(ressource);
        } else {
            return ResponseEntity.notFound().build();
        }
    }





    @PutMapping("/commandes/{id}/receptionner")
    public ResponseEntity<?> receptionnerCommande(@PathVariable Long id) {
        Optional<Commande> commandeOpt = commandeRepo.findById(id);

        if (commandeOpt.isPresent()) {
            Commande commande = commandeOpt.get();

            // Récupérer la date actuelle (uniquement la date, sans l'heure)
            Date now = new Date();
            Date dateReception = removeTime(now); // Supprimer l'heure pour éviter les erreurs
            commande.setDateReception(dateReception);

            // Vérifier si la date de livraison prévue existe et comparer uniquement les jours
            if (commande.getDateLivraisonPrevue() != null) {
                Date dateLivraisonPrevue = removeTime(commande.getDateLivraisonPrevue()); // Supprimer l'heure

                if (dateReception.after(dateLivraisonPrevue)) {
                    commande.setEtatCommande(EtatCommande.EN_RETARD);
                } else {
                    commande.setEtatCommande(EtatCommande.RECUE);
                }
            } else {
                commande.setEtatCommande(EtatCommande.RECUE);
            }

            commandeRepo.save(commande);
            return ResponseEntity.ok("Commande reçue et état mis à jour");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Commande non trouvée");
    }

    // ✅ Fonction pour supprimer l'heure d'une date
    private Date removeTime(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }



    @GetMapping("/commandes/en-retard")
    public List<Commande> getCommandesEnRetard() {
        // Sélectionner uniquement les commandes dont l'état est "EN_RETARD"
        return commandeRepo.findByEtatCommande(EtatCommande.EN_RETARD);
    }


    @PutMapping("/commandes/verifier-et-mettre-a-jour")
    public ResponseEntity<?> verifierEtMettreAJourEtatCommandes() {
        List<Commande> commandes = commandeRepo.findAll();

        for (Commande commande : commandes) {
            if (commande.getDateReception() != null) {
                if (commande.getDateLivraisonPrevue() != null
                        && commande.getDateReception().after(commande.getDateLivraisonPrevue())) {
                    // Si la date de réception dépasse la date de livraison prévue, la commande est en retard
                    commande.setEtatCommande(EtatCommande.EN_RETARD);
                } else {
                    // Sinon, elle est reçue normalement
                    commande.setEtatCommande(EtatCommande.RECUE);
                }
            }
            commandeRepo.save(commande);
        }

        return ResponseEntity.ok("État des commandes mis à jour");
    }
    @GetMapping("/api/{id}/position")
    public ResponseEntity<?> getCommandePosition(@PathVariable Long id) {
        Map<String, Object> data = new HashMap<>();
        data.put("clientLat", 36.85);      // position fixe si client non enregistrée
        data.put("clientLng", 10.3);
        data.put("livreurLat", 36.81);     // simuler une progression
        data.put("livreurLng", 10.2);
        data.put("etat", "EN_ROUTE");

        return ResponseEntity.ok(data);
    }

    @GetMapping("/{id}")
    public Commande getCommande(@PathVariable Long id) {
        return commandeRepo.findById(id).orElseThrow();
    }
}

