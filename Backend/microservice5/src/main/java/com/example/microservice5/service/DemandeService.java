package com.example.microservice5.service;


import com.example.microservice5.entity.Demande;
import com.example.microservice5.repository.DemandeRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class DemandeService {

    DemandeRepository demandeRepository;


    public Demande addDemande(Demande demande) {
        return demandeRepository.save(demande);
    }

    public List<Demande> getAllDemandes() {
        return demandeRepository.findAll();
    }
    public void deleteDemande(Long id) {
        if (demandeRepository.existsById(id)) {
            demandeRepository.deleteById(id);
        } else {
            throw new RuntimeException("Demande introuvable avec l'ID : " + id);
        }
    }



    private JavaMailSender mailSender;

    public void sendEmail(String clientEmail, String subject, String message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setTo("rezguiiheb06@gmail.com");
        mailMessage.setSubject(subject);
        mailMessage.setText("De : " + clientEmail + "\n\n" + message);

        mailSender.send(mailMessage);
    }




}
