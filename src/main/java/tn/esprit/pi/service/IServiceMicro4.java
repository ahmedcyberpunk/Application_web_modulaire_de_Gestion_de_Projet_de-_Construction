package tn.esprit.pi.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tn.esprit.pi.entity.Contart_Terrain;
import tn.esprit.pi.entity.Papier_autorisation;
import tn.esprit.pi.entity.Terrain;


public interface IServiceMicro4 {


    ///////////////////CRUD TERRAIN////////////////////////
    public Terrain addTerrain(Terrain terrain);
    public void updateTerrain(Terrain terrain);
    public void deleteTerrain(Integer id);

    ///////////////////CRUD AUTORISATION////////////////////////

    public Papier_autorisation addPapier(Papier_autorisation papier);
    public void updatePapier(Papier_autorisation papier);
    public void deletePapier(Integer id);

///////////////////CRUD CONTRAT////////////////////////

    public Contart_Terrain addContrat(Contart_Terrain contrat);
    public void updateContrat(Contart_Terrain contart);
    public void deleteContrat(Integer id);


}
