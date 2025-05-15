package tn.esprit.pigc.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.pigc.Entity.PPE;
import tn.esprit.pigc.Service.SMServiceImpl;

import java.util.List;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/ppe")
public class PPERestController {
    @Autowired
    private SMServiceImpl ppeService;

    @PostMapping("/addPPE/{employeeId}")
    public PPE addPPE(@RequestBody PPE ppe, @PathVariable int employeeId) {
        return ppeService.AddPPE(ppe, employeeId);
    }




    @GetMapping("/listppe")
    public List<PPE> getAllPPE() {
        return ppeService.getAllPPE();
    }

    @DeleteMapping("/delete/{id}")
    public void deletePPE(@PathVariable Integer id) {
        ppeService.deletePPE(id);
    }
}
