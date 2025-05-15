package tn.esprit.pigc.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.pigc.Entity.SafetyAlert;
import tn.esprit.pigc.Service.SMServiceImpl;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/safetyalert")
public class SafetyAlertRestController {
    @Autowired
    SMServiceImpl safetyalertService;

    @GetMapping
    public List<SafetyAlert> getActiveAlerts() {
        return safetyalertService.getActiveSafetyAlerts();
    }

    @GetMapping("/active")
    public List<String> getActiveSafetyAlerts() {
        return safetyalertService.getSafetyAlerts();
    }

}
