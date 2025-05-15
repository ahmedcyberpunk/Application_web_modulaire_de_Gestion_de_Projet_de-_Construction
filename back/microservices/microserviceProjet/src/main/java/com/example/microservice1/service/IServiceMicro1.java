package com.example.microservice1.service;

import com.example.microservice1.entity.*;

import java.util.List;
import java.util.Map;

public interface IServiceMicro1 {

    Projet addProjet(Projet projet);

    List<Projet> findAll();
    void delete(Integer id);
    Projet updateProjet(Integer id , Projet projetDetails);


    Rapport addRapportAndAffectToProject(Integer id, Rapport rapport);
    List<Rapport> findAllRapport();
    void deleteRapport(Integer id);
    Rapport updateRapport(Integer id , Rapport rapportDetails);


    Tache addTacheAndAffectToProject(Integer id, Tache tache);

    ProjetDTO getProjetById(Integer id);

    List<Tache> findAllTache();

    void deleteTache(Integer id);
    Tache updateTache(Integer id , Tache tacheDetails);
    List<Tache> getTachesByProjetId(Integer id) ;

   void checkAndCreateRapportForCompletedProjects();

    List<KPI> calculerAvancementProjet(Integer idProjet);


}
