package com.example.microservice5.controller;


import com.example.microservice5.entity.Demande;
import com.example.microservice5.service.DemandeService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@CrossOrigin("*")
@RequestMapping("/demande")

public class DemandeController {
    DemandeService demandeService;

    @GetMapping("/alldemande")
    public List<Demande> getAllDemandes(){
        return demandeService.getAllDemandes();
    }
    @PostMapping("/adddemande")
    public Demande addDemande(@RequestBody Demande demande){
        return demandeService.addDemande(demande);
    }


    @PostMapping("/send")
    public String sendEmail(@RequestBody EmailRequest request) {
        demandeService.sendEmail(request.getEmail(), request.getSubject(), request.getMessage());
        return "Email envoyé avec succès !";
    }
}

// DTO pour recevoir la requête
class EmailRequest {
    private String email;
    private String subject;
    private String message;

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}
