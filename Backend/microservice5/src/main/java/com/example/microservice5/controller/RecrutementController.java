package com.example.microservice5.controller;


import com.example.microservice5.entity.Recrutement;
import com.example.microservice5.service.RecrutementService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/recrutement")
@AllArgsConstructor
@CrossOrigin("*")
public class RecrutementController {

     RecrutementService recrutementService;


    @PostMapping("/add")
    public ResponseEntity<Recrutement> saveRecrutement(
            @RequestParam("nom") String nom,
            @RequestParam("prenom") String prenom,
            @RequestParam("commentaire") String commentaire,
            @RequestParam("fichier") MultipartFile fichier,
            @RequestParam("dateNaissance") String dateNaissance,
            @RequestParam("email") String email,
            @RequestParam("telephone") String telephone,
            @RequestParam("poste") String poste,
            @RequestParam("typeDemande") String typeDemande) {
        try {
            byte[] fichierBytes = fichier.getBytes();

            Recrutement recrutement = new Recrutement();
            recrutement.setNom(nom);
            recrutement.setPrenom(prenom);
            recrutement.setCommentaire(commentaire);
            recrutement.setFichier(fichierBytes);
            recrutement.setDateNaissance(LocalDate.parse(dateNaissance));  // Conversion de la date
            recrutement.setEmail(email);
            recrutement.setTelephone(telephone);
            recrutement.setPoste(poste);
            recrutement.setTypeDemande(Recrutement.TypeDemande.valueOf(typeDemande.toUpperCase()));

            Recrutement savedRecrutement = recrutementService.saveRecrutement(recrutement);
            return ResponseEntity.ok(savedRecrutement);
        } catch (Exception e) {
            e.printStackTrace();  // Log de l'exception
            return ResponseEntity.status(400).body(null); // Retourne une erreur en cas d'échec
        }
    }


    @GetMapping("/all")
    public ResponseEntity<List<Recrutement>> getAllRecrutements() {
        List<Recrutement> recrutements = recrutementService.getAllRecrutements();
        return ResponseEntity.ok(recrutements);
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteRecrutement(@PathVariable Long id) {
        recrutementService.deleteRecrutement(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable Long id) {
        Recrutement recrutement = recrutementService.getRecrutementById(id); // Trouver le recrutement par ID
        if (recrutement != null) {
            byte[] fileContent = recrutement.getFichier();  // Récupérer le fichier
            return ResponseEntity.ok()
                    .header("Content-Type", "application/octet-stream")
                    .header("Content-Disposition", "attachment; filename=\"" + recrutement.getNom() + ".pdf\"")
                    .body(fileContent);  // Envoyer le fichier en réponse
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    // Mettre à jour le statut d'une demande
    @PutMapping("/updateStatus/{id}")
    public ResponseEntity<String> updateStatus(@PathVariable Long id, @RequestBody Map<String, Boolean> status) {
        boolean updated = recrutementService.updateRecrutementStatus(id, status.get("traiter"));
        if (updated) {
            return ResponseEntity.ok("Statut mis à jour avec succès !");
        } else {
            return ResponseEntity.badRequest().body("Erreur : demande non trouvée");
        }
    }
}
