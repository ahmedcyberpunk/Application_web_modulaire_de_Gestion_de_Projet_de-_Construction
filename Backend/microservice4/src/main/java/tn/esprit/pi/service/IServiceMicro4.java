package tn.esprit.pi.service;

import tn.esprit.pi.entity.Contrat_Terrain;
import tn.esprit.pi.entity.Papier_autorisation;
import tn.esprit.pi.entity.SearchHistory;
import tn.esprit.pi.entity.Terrain;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.MailException;

import java.io.File;


public interface IServiceMicro4 {


    ///////////////////CRUD TERRAIN////////////////////////
    public Terrain addTerrain(Terrain terrain);
    public Terrain updateTerrain(Terrain terrain);
    public void deleteTerrain(Integer id);
    List<Terrain> getAllTerrains();
    public List<Papier_autorisation> getPapierByTerrainId(Long terrainId);
    public List<Terrain> getAllTerrainsFromContracts();
    Terrain findByIdTerrain(Long id);

Contrat_Terrain findContratByTerrainId(Long id);
    Contrat_Terrain findContratById(Integer id);
   List <Contrat_Terrain> findContratByNomProprietaire(String nom);
    ///////////////////CRUD AUTORISATION////////////////////////

    public Papier_autorisation addPapier(Papier_autorisation papier, Long id);
    public void updatePapier(Papier_autorisation papier,Long id);
    public void deletePapier(Integer id);
    List<Papier_autorisation> getAllPapier();
///////////////////CRUD CONTRAT////////////////////////

    public Contrat_Terrain addContrat(Contrat_Terrain contrat,Long id);
    public void updateContrat(Contrat_Terrain contart);
    public void deleteContrat(Integer id);
    List<Contrat_Terrain> getAllContrats();



    public void saveSearchHistory(String username, String location);

    public List<SearchHistory> getRecommendations(String username);

}
