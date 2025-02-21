package tn.esprit.pi.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tn.esprit.pi.entity.Contart_Terrain;
import tn.esprit.pi.entity.Papier_autorisation;
import tn.esprit.pi.entity.Terrain;
import tn.esprit.pi.repository.AutorisationRepo;
import tn.esprit.pi.repository.ContratRepo;
import tn.esprit.pi.repository.TerrainRepo;
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
 public void updateTerrain(Terrain terrain) {
  if (terrainRepo.findById(terrain.getIdTerrain()).isPresent()) {
   terrainRepo.save(terrain);
  } else {
   throw new RuntimeException("Terrain not found");
  }
 }

 public void deleteTerrain(Integer id){
  terrainRepo.deleteById(id);
 }

 @Override
 public Papier_autorisation addPapier(Papier_autorisation papier) {
  return autorisationRepo.save(papier);
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
 public Contart_Terrain addContrat(Contart_Terrain contrat) {
  return  contratRepo.save(contrat);
 }

 @Override
 public void updateContrat(Contart_Terrain contart) {
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


}
