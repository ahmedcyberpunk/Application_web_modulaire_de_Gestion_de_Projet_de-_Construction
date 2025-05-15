package com.example.microservice1.controller;

import com.example.microservice1.entity.*;
import com.example.microservice1.service.IServiceMicro1;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
public class RestControllerMicro1 {
    @Autowired
    IServiceMicro1 service;

    //***********PROJET************
    @PostMapping("/ajouterProjet")
    Projet addProjet(@RequestBody Projet projet) {
        return  service.addProjet(projet);
    }


    @GetMapping("/project_list")
    public List<Projet> findAll() {
        return service.findAll();
    }


    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        service.delete(id);
        return "Projet deleted successfully";
    }


    @PutMapping ("/updateProjet/{id}")
    Projet updateProjet(@PathVariable Integer id, @RequestBody Projet projet) {
        return  service.updateProjet(id, projet);
    }

    //***********RAPPORT************

    @PostMapping ("/ajouterRapport/{id}")
    Rapport addRapportAndAffectToProject(@PathVariable Integer id, @RequestBody Rapport rapport) {
        return  service.addRapportAndAffectToProject(id, rapport);
    }
    @GetMapping("/rapport_list")
    public List<Rapport> findAllRapport() {
        return service.findAllRapport();
    }

    @DeleteMapping("/deleteRapport/{id}")
    public String deleteRapport(@PathVariable Integer id) {
        service.deleteRapport(id);
        return "Rapport deleted successfully";
    }
    @PutMapping("/updateRapport/{id}")
    Rapport updateRapport(@PathVariable Integer id, @RequestBody Rapport rapport) {
        return  service.updateRapport(id, rapport);
    }

    //***********TACHE************
    @PostMapping("/ajouterTache/{id}")
    Tache addTacheAndAffectToProject(@PathVariable Integer id, @RequestBody Tache tache) {
        return  service.addTacheAndAffectToProject(id, tache);
    }
    @GetMapping("/tache_list")
    public List<Tache> findAllTache() {
        return service.findAllTache();}




    @DeleteMapping("/deleteTache/{id}")
    public String deleteTache(@PathVariable Integer id) {
        service.deleteTache(id);
        return "Tache deleted successfully";
    }
    @PutMapping("/updateTache/{id}")
    Tache updateTache(@PathVariable Integer id, @RequestBody Tache tache) {
        return  service.updateTache(id, tache);
    }

    @GetMapping("/list_projet&tasks{id}")
    public ProjetDTO getProjet(@PathVariable Integer id) {
        return service.getProjetById(id);
    }

    @GetMapping("/listtasks/{id}")
    public  List<Tache> getTachesByProjetId(@PathVariable Integer id){
        return service.getTachesByProjetId(id);
    }

    @GetMapping("/check-and-create-rapport")
    public String checkAndCreateRapport() {
        service.checkAndCreateRapportForCompletedProjects();
        return "Rapport creation checked for completed projects.";
    }



    @GetMapping("/kpis/{id}")
    public List<KPI> getKpis(@PathVariable Integer id) {
        return service.calculerAvancementProjet(id);
    }





}
