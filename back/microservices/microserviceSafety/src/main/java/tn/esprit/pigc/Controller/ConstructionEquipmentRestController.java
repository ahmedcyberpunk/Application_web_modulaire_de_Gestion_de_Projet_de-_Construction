package tn.esprit.pigc.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.pigc.Entity.ConstructionEquipment;
import tn.esprit.pigc.Service.SMServiceImpl;

import java.util.Date;
import java.util.List;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/constructionequip")
public class ConstructionEquipmentRestController {

 @Autowired
    SMServiceImpl ConstrcutionEquipmentService;

    @PostMapping("/add")
    public ConstructionEquipment AddConstructionEquipment(@RequestBody ConstructionEquipment constructionEquipment) {
        return ConstrcutionEquipmentService.AddConstructionEquipment(constructionEquipment);
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateConstructionEquipment(@PathVariable int id,
                                                         @RequestBody ConstructionEquipment updatedEquipment) {

        ConstructionEquipment updated = ConstrcutionEquipmentService.updateConstructionEquipment(id,
                updatedEquipment.getLastMaintenance(), updatedEquipment.getMaintenanceDate());

        if (updated == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Construction Equipment not found with ID: " + id);
        }

        return ResponseEntity.ok(updated);
    }

    @GetMapping("/list")
    public List<ConstructionEquipment> getAllConstructionEquipment() {
              return ConstrcutionEquipmentService.getAllConstructionEquipment();
  }

  @DeleteMapping("/delete/{id}")
    public void deleteConstructionEquipment(@PathVariable int id) {
        ConstrcutionEquipmentService.deleteConstructionEquipment(id);

  }

}
