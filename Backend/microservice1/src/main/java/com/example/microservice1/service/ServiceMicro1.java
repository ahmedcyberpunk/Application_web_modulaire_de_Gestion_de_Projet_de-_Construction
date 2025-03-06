package com.example.microservice1.service;

import com.example.microservice1.entity.*;
import com.example.microservice1.repository.EquipeRepo;
import com.example.microservice1.repository.ProjetRepo;
import com.example.microservice1.repository.RapportRepo;
import com.example.microservice1.repository.TacheRepo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class ServiceMicro1 implements IServiceMicro1{

    EquipeRepo equipeRepo;
    ProjetRepo projetRepo;
    RapportRepo rapportRepo;
    TacheRepo tacheRepo;


//**********************CRUD_Project**********************
    @Override
    public Projet addProjet(Projet projet) {
        return projetRepo.save(projet);
    }
    @Override
    public List<Projet> findAll() {
        return projetRepo.findAll();
    }
    @Override
    public void delete(Integer id) {
        projetRepo.deleteById(id);
    }

    @Override
    public Projet updateProjet(Integer id, Projet projetDetails) {
        Projet projet = projetRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Projet not found with ID: " + id));
        projet.setNomProjet(projetDetails.getNomProjet());
        projet.setDescriptionProjet(projetDetails.getDescriptionProjet());
        projet.setDateDebutProjet(projetDetails.getDateDebutProjet());
        projet.setDateFinProjet(projetDetails.getDateFinProjet());
        projet.setBudgetProjet(projetDetails.getBudgetProjet());
        projet.setStatut(projetDetails.getStatut());
        return projetRepo.save(projet);
    }



    //**********************CRUD_Rapport**********************
    @Override
    public Rapport addRapportAndAffectToProject(Integer id, Rapport rapport) {
        Projet projet = projetRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Projet not found with ID: " + id));
        rapport.setProjet(projet);
        return rapportRepo.save(rapport);
    }

    public List<Rapport> findAllRapport(){
        return rapportRepo.findAll();
    }

    @Override
    public void deleteRapport(Integer id) {
        rapportRepo.deleteById(id);
    }


    @Override
    public Rapport updateRapport(Integer id, Rapport RapportDetails) {
        Rapport rapport = rapportRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Projet not found with ID: " + id));
        rapport.setDateRapport(RapportDetails.getDateRapport());
        rapport.setDescriptionRapport(RapportDetails.getDescriptionRapport());

        return rapportRepo.save(rapport);
    }



    //**********crud taches*************
    @Override
    public Tache addTacheAndAffectToProject(Integer id, Tache tache) {
        Projet projet = projetRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Projet not found with ID: " + id));
        tache.setProjet(projet);
        return tacheRepo.save(tache);
    }

    public List<Tache> findAllTache(){
        return tacheRepo.findAll();
    }

    public List<Tache> getTachesByProjetId(Integer id) {
        Projet projet = projetRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Projet not found with ID: " + id));
        return projet.getTaches();
    }



    @Override
    public void deleteTache(Integer id) {
        tacheRepo.deleteById(id);
    }



    @Override
    public Tache updateTache(Integer id, Tache tacheDetails) {
        Tache tache = tacheRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("tache not found with ID: " + id));
        tache.setNomTache(tacheDetails.getNomTache());
        tache.setDescriptionTache(tacheDetails.getDescriptionTache());
        tache.setDateDebutTache(tacheDetails.getDateDebutTache());
        tache.setDateFinTache(tacheDetails.getDateFinTache());
        tache.setProgresTache(tacheDetails.getProgresTache());
        tache.setStatut(tacheDetails.getStatut());

        return tacheRepo.save(tache);
    }


    //////*****DTO*****///////
    public ProjetDTO getProjetById(Integer id) {
        return projetRepo.findById(id)
                .map(p -> {
                    ProjetDTO dto = new ProjetDTO();
                    dto.setIdProjet(p.getIdProjet());
                    dto.setNomProjet(p.getNomProjet());
                    dto.setDescriptionProjet(p.getDescriptionProjet());
                    dto.setDateDebutProjet(p.getDateDebutProjet());
                    dto.setDateFinProjet(p.getDateFinProjet());
                    dto.setBudgetProjet(p.getBudgetProjet());
                    dto.setStatut(p.getStatut());

                    // Convert tasks to DTO
                    dto.setTaches(p.getTaches().stream().map(t -> {
                        TacheDTO tDto = new TacheDTO();
                        tDto.setIdTache(t.getIdTache());
                        tDto.setNomTache(t.getNomTache());
                        tDto.setDescriptionTache(t.getDescriptionTache());
                        tDto.setDateDebutTache(t.getDateDebutTache());
                        tDto.setDateFinTache(t.getDateFinTache());
                        tDto.setProgresTache(t.getProgresTache());
                        tDto.setStatut(t.getStatut());
                        return tDto;
                    }).collect(Collectors.toList()));

                    return dto;
                }).orElseThrow(() -> new RuntimeException("Projet not found"));
    }


    @Scheduled(cron = "0 * * * * ?")
    public void checkAndCreateRapportForCompletedProjects() {
        List<Projet> projets = projetRepo.findAll();

        for (Projet projet : projets) {
            if (projet.getStatut() == Projet.Statut.COMPLETED) {
                // Check if a rapport already exists for this project
                boolean rapportExists = rapportRepo.existsByProjet(projet);

                if (!rapportExists) {
                    // Create a new Rapport only if it does not exist
                    Rapport rapport = new Rapport();
                    rapport.setDateRapport(LocalDate.now());
                    rapport.setDescriptionRapport("Project completed on " + LocalDate.now());
                    rapport.setProjet(projet);

                    // Save the Rapport
                    rapportRepo.save(rapport);
                }
            }
        }
    }


    @Override
    public List<KPI> calculerAvancementProjet(Integer idProjet) {
        List<KPI> kpis = new ArrayList<>();

        // Fetch project
        Optional<Projet> projetOpt = projetRepo.findById(idProjet.intValue());
        if (projetOpt.isEmpty()) {
            kpis.add(new KPI("Error", "Projet not found"));
            return kpis;
        }

        Projet projet = projetOpt.get();
        List<Tache> taches = projet.getTaches();

        if (taches.isEmpty()) {
            kpis.add(new KPI("Avancement", "0% - No tasks available"));
            return kpis;
        }

        // Calculate task completion and breakdown by status
        int totalProgress = taches.stream().mapToInt(Tache::getProgresTache).sum();
        int averageProgress = totalProgress / taches.size();

        long completedTasks = taches.stream().filter(t -> t.getProgresTache() == 100).count();
        long inProgressTasks = taches.stream().filter(t -> t.getProgresTache() > 0 && t.getProgresTache() < 100).count();
        long pendingTasks = taches.stream().filter(t -> t.getProgresTache() == 0).count();

        // Calculate construction KPIs
        kpis.add(new KPI("Projet", projet.getNomProjet()));
        kpis.add(new KPI("Total Tasks", String.valueOf(taches.size())));
        kpis.add(new KPI("Completed Tasks", String.valueOf(completedTasks)));
        kpis.add(new KPI("In Progress Tasks", String.valueOf(inProgressTasks)));
        kpis.add(new KPI("Pending Tasks", String.valueOf(pendingTasks)));
        kpis.add(new KPI("Average Task Progress", averageProgress + "%"));
        kpis.add(new KPI("Statut", projet.getStatut().toString()));

        // Budget KPI (if available)
        if (projet.getBudgetProjet() > 0) {
            // Assuming you have some way to track actual spending on the project, e.g., through expenses
            // For simplicity, let’s assume you track actual budget spent somewhere
            double actualSpent = calculateActualSpent(projet); // A method to calculate the actual spent budget
            double budgetStatus = (actualSpent / projet.getBudgetProjet()) * 100;
            kpis.add(new KPI("Budget Status", String.format("%.2f%%", budgetStatus)));
        }

        return kpis;
    }

    private double calculateActualSpent(Projet projet) {
        // Implement logic to calculate the actual amount spent on the project
        // Example: you could fetch the actual expenses related to the project from another service or database
        return 500000;  // For example purposes, assume $500,000 is spent.
    }



}
