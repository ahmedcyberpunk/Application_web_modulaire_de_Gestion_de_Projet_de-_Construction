package tn.esprit.pi.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import tn.esprit.pi.entity.Contrat_Terrain;
import tn.esprit.pi.entity.Papier_autorisation;
import tn.esprit.pi.entity.Terrain;
import tn.esprit.pi.repository.AutorisationRepo;
import tn.esprit.pi.repository.ContratRepo;
import tn.esprit.pi.repository.TerrainRepo;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class ServiceMicro4 implements IServiceMicro4 {
 TerrainRepo terrainRepo;
 AutorisationRepo autorisationRepo;
 ContratRepo contratRepo;


 public Terrain addTerrain(Terrain terrain){
  return terrainRepo.save(terrain);
 }
 public Terrain  updateTerrain(Terrain terrain) {
  Optional<Terrain> existingTerrainOpt = terrainRepo.findById(terrain.getIdTerrain());

  if (existingTerrainOpt.isPresent()) {
   Terrain existingTerrain = existingTerrainOpt.get();

   // Update the existing terrain's fields
   existingTerrain.setNom(terrain.getNom());
   existingTerrain.setLocalisation(terrain.getLocalisation());
   existingTerrain.setSuperficie(terrain.getSuperficie());
   existingTerrain.setStatutJuridique(terrain.getStatutJuridique());
   existingTerrain.setTypeSol(terrain.getTypeSol());


   return terrainRepo.save(existingTerrain);


  }

  return null;


 }
 public List<Papier_autorisation> getPapierByTerrainId(Long terrainId) {
  // Find terrain by ID
  Terrain terrain = terrainRepo.findByIdTerrain(terrainId);


  // Return list of papers associated with the found terrain
  return terrain.getPapierAutorisationConstructions();
 }

 @Override
 public Terrain findByIdTerrain(Long id) {
  return terrainRepo.findByIdTerrain(id);
 }

 @Override
 public Papier_autorisation addPapier(Papier_autorisation papier, Long id) {
  Terrain terrain = terrainRepo.findByIdTerrain(id);
  papier.setTerrain(terrain);

  return autorisationRepo.save(papier);
 }


 public void deleteTerrain(Integer id){
  terrainRepo.deleteById(id);
 }

 @Override
 public List<Terrain> getAllTerrains() {
  return terrainRepo.findAll();
 }



 @Override
 public void updatePapier(Papier_autorisation papier) {
  if (autorisationRepo.findById(papier.getId_Autorisation()).isPresent()) {
   autorisationRepo.save(papier);
  } else {
   throw new RuntimeException("Terrain not found");
  }
 }

 @Override
 public void deletePapier(Integer id) {
  autorisationRepo.deleteById(id);
 }

 @Override
 public List<Papier_autorisation> getAllPapier() {
  return autorisationRepo.findAll();
 }

 @Override
 public Contrat_Terrain addContrat(Contrat_Terrain contrat) {
  return  contratRepo.save(contrat);
 }

 @Override
 public void updateContrat(Contrat_Terrain contart) {
 if (contratRepo.findById(contart.getId_Contrat()).isPresent()) {
  contratRepo.save(contart);
 } else {
  throw new RuntimeException("Terrain not found");
 }

 }

 @Override
 public void deleteContrat(Integer id) {
contratRepo.deleteById(id);
 }

 @Override
 public List<Contrat_Terrain> getAllContrats() {
  return contratRepo.findAll();
 }


}
