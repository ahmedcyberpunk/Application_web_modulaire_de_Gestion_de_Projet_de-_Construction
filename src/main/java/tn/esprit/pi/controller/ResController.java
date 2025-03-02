package tn.esprit.pi.controller;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.pi.entity.Contrat_Terrain;
import tn.esprit.pi.entity.Papier_autorisation;
import tn.esprit.pi.entity.Terrain;
import tn.esprit.pi.service.IServiceMicro4;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Map;


@AllArgsConstructor
@RestController
@RequestMapping("/api4/terrain")
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:49548"})
public class ResController {
    IServiceMicro4 serviceMicro4;
    private JavaMailSender emailSender;


    @PostMapping("/addterrain")
    public Terrain addTerrain(@RequestBody Terrain terrain) {
        return serviceMicro4.addTerrain(terrain);
    }

    ;

    @PutMapping("/updateterrain")
    public Terrain updateTerrain(@RequestBody Terrain terrain) {
        // Fetch the existing terrain by ID
        return serviceMicro4.updateTerrain(terrain);
    }

    @GetMapping("/seuleterrain/{id}")
    public Terrain getTerrain(@PathVariable("id") Long id) {
        return serviceMicro4.findByIdTerrain(id);
    }


    @DeleteMapping("/deleteterrain/{id}")
    public void deleteTerrain(@PathVariable("id") int id) {
        serviceMicro4.deleteTerrain(id);
    }


    @PostMapping("/addPapier/{id}")
    public Papier_autorisation addPapier(@RequestBody Papier_autorisation papier_autorisation, @PathVariable("id") Long id) {
        return serviceMicro4.addPapier(papier_autorisation, id);
    }


    @PutMapping("/updatePapier/{id}")
    public void updatePapier(@RequestBody Papier_autorisation papier_autorisation, @PathVariable("id") Long id) {
        serviceMicro4.updatePapier(papier_autorisation, id);
    }

    @DeleteMapping("/deletePapier/{id}")
    public void deletePapier(@PathVariable("id") int id) {
        serviceMicro4.deletePapier(id);
    }


    @PostMapping("/addContrat/{id}")
    public Contrat_Terrain addContrat(@RequestBody Contrat_Terrain contart_terrain, @PathVariable("id") Long id) {
        return serviceMicro4.addContrat(contart_terrain, id);
    }

    @PutMapping("/updateContrat")
    public void updateContrat(@RequestBody Contrat_Terrain contart_terrain) {
        serviceMicro4.updateContrat(contart_terrain);
    }

    @PutMapping("/deleteContrat/{id}")
    public void deleteContrat(@PathVariable("id") int id) {

        serviceMicro4.deleteContrat(id);

    }

    @GetMapping("/allterrain")
    List<Terrain> getAllTerrains() {
        return serviceMicro4.getAllTerrains();
    }

    @GetMapping("/papiers/{terrainId}")
    public List<Papier_autorisation> getPapierByTerrainId(@PathVariable("terrainId") Long terrainId) {
        return serviceMicro4.getPapierByTerrainId(terrainId);
    }

    @GetMapping("/allpapier")
    List<Papier_autorisation> getAllPapier() {
        return serviceMicro4.getAllPapier();
    }

    @GetMapping("/contrat")
    List<Contrat_Terrain> getAllContrats() {
        return serviceMicro4.getAllContrats();
    }

    @PostMapping("/images")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            Path path = Paths.get("src/main/resources/static/images/" + file.getOriginalFilename());
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            return ResponseEntity.ok("Image uploaded successfully");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading image");
        }
    }

    @GetMapping("/contrat/{id}")
    Contrat_Terrain findContratByTerrainId(@PathVariable("id") Long id) {
        return serviceMicro4.findContratByTerrainId(id);
    }


    @PostMapping("/send")
    public ResponseEntity<String> sendEmailWithPdf(@RequestParam String to, @RequestParam("file") MultipartFile file) {
        try {
            // Check if the file is empty
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body("Aucun fichier sélectionné.");
            }

            // Create the email message
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);  // true for multipart

            // Set the email properties
            helper.setTo(to);
            helper.setSubject("Email avec PDF (image)");


            // Add the PDF as an attachment

            // Send the email
            emailSender.send(message);

            return ResponseEntity.ok().body("E-mail envoyé avec succès avec le PDF.");
        } catch (MessagingException e) {
            return ResponseEntity.status(500).body("Erreur lors de l'envoi de l'e-mail avec PDF: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erreur interne: " + e.getMessage());
        }
    }
}