package tn.esprit.pi.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.pi.entity.Contart_Terrain;
import tn.esprit.pi.entity.Papier_autorisation;
import tn.esprit.pi.entity.Terrain;
import tn.esprit.pi.service.IServiceMicro4;


@AllArgsConstructor
@RestController
public class ResController {
IServiceMicro4 serviceMicro4;
@PostMapping("/addterrain")
public Terrain addTerrain(@RequestBody Terrain terrain) {
    return serviceMicro4.addTerrain(terrain);
    };

@PutMapping("/updateterrain")
public void updateTerrain(@RequestBody Terrain terrain) {
   serviceMicro4.updateTerrain(terrain);
    };
@PutMapping("/deleteterrain/{id}")
public void deleteTerrain(@PathVariable("id") int id) {
      serviceMicro4.deleteTerrain(id);
}



    @PostMapping("/addPapier")
    public Papier_autorisation addPapier(@RequestBody Papier_autorisation papier_autorisation) {
        return serviceMicro4.addPapier(papier_autorisation);
    }
    @PutMapping("/updatePapier")
    public void updatePapier(@RequestBody Papier_autorisation papier_autorisation) {
        serviceMicro4.updatePapier(papier_autorisation);
    }
    @PutMapping("/deletePapier/{id}")
    public void deletePapier(@PathVariable("id") int id) {
        serviceMicro4.deletePapier(id);
    }



    @PostMapping("/addContrat")
    public Contart_Terrain addContrat(@RequestBody Contart_Terrain contart_terrain) {
        return serviceMicro4.addContrat(contart_terrain);
    }
    @PutMapping("/updateContrat")
    public void updateContrat(@RequestBody Contart_Terrain contart_terrain) {
        serviceMicro4.updateContrat(contart_terrain);
    }
    @PutMapping("/deleteContrat/{id}")
    public void deleteContrat(@PathVariable("id") int id) {

        serviceMicro4.deleteContrat(id);

    }





}
