package com.example.microservice2.controller;

import com.example.microservice2.entity.CartRessource;
import com.example.microservice2.entity.Commande;
import com.example.microservice2.entity.Fournisseurs;
import com.example.microservice2.entity.Ressource;
import com.example.microservice2.repository.CommandeRepo;
import com.example.microservice2.repository.FournisseursRepo;
import com.example.microservice2.service.CommandeService;
import com.example.microservice2.service.FournisseurService;
import com.example.microservice2.service.RessourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

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
    FournisseursRepo fournisseursRepo;
    CommandeRepo commandeRepo;



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



}

