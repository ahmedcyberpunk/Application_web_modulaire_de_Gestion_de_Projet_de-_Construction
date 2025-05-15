package tn.esprit.pigc.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.pigc.Entity.EquipmentInspection;
import tn.esprit.pigc.Service.SMServiceImpl;

import java.util.List;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/equipinspection")
public class EquipmentInspectionRestController {

    @Autowired
    SMServiceImpl EquipementInspectionService;

//     @PostMapping("/add")
//    public EquipmentInspection AddEquipmentInspection(@RequestBody EquipmentInspection equipmentinspection) {
//         return EquipementInspectionService.AddEquipmentInspection(equipmentinspection);
//     }

    @GetMapping("/{id}")
    public ResponseEntity<EquipmentInspection> getEquipmentInspectionById(@PathVariable int id) {
        EquipmentInspection inspection = EquipementInspectionService.getEquipmentInspectionById(id);
        if (inspection != null) {
            return ResponseEntity.ok(inspection);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Return 404 if not found
        }
    }
    @PostMapping("/add/{itemId}")
    public EquipmentInspection addEquipmentInspection(
            @RequestBody EquipmentInspection equipmentInspection,
            @PathVariable int itemId) {
        return EquipementInspectionService.AddEquipmentInspection(equipmentInspection, itemId);
    }
     @GetMapping("/list")
    public List<EquipmentInspection> listEquipmentInspection() {
        return EquipementInspectionService.getAllEquipmentInspection();
     }
     @DeleteMapping("/delete/{id}")
       public void deleteEquipmentInspection(@PathVariable int id) {
         EquipementInspectionService.deleteEquipmentInspection(id);
     }
    @PutMapping("/finalize/{inspectionId}")
    public String finalizeInspection(@PathVariable int inspectionId) {
        EquipementInspectionService.finalizeInspection(inspectionId);
        return "Inspection finalized successfully for ID: " + inspectionId;
    }

    @PutMapping("/update/{id}")
    public EquipmentInspection updateEquipmentInspection(@PathVariable int id, @RequestBody EquipmentInspection updatedInspection) {
        return EquipementInspectionService.updateEquipmentInspection(id, updatedInspection);
    }


}
