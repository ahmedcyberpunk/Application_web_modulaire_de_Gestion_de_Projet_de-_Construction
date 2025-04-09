package tn.esprit.pigc.Service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.pigc.Entity.*;
import tn.esprit.pigc.Repository.*;

import java.util.*;

@Service
@Slf4j
@AllArgsConstructor

public  class SMServiceImpl implements ISM {

    @Autowired
    private PPERepository ppeRepository;
    private EmployeeRepository employeeRepository;
    private ConstructionEquipmentRepository constructionEquipmentRepository;
    private EquipmentInspectionRepository equipmentInspectionRepository;
    private SafetyAlertRepository safetyAlertRepository;

    public List<PPE> getAllPPE() {
        return ppeRepository.findAll();
    }

    public PPE getPPEById(Integer id) {
        return ppeRepository.findById(id).orElse(null);
    }

//    public PPE savePPE(PPE ppe) {
//        return ppeRepository.save(ppe);
//    }


    @Override
    public PPE AddPPE(PPE ppe, int employeeId) {
        // Fetch the employee using employeeId
        Employee existingEmployee = employeeRepository.findById(employeeId).orElse(null);

        if (existingEmployee == null) {
            throw new RuntimeException("Employee not found with ID: " + employeeId);
        }

        // Assign the fetched employee to the PPE
        ppe.setEmployee(existingEmployee);

        // Save PPE
        return ppeRepository.save(ppe);
    }




    @Override
    public void deletePPE(Integer id) {
        if(ppeRepository.existsById(id)) {
            ppeRepository.deleteById(id);
        }
    }

    @Override
    public Employee AddEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public List<Employee> getAllEmployee() {
        return employeeRepository.findAll();
    }

    @Override
    public ConstructionEquipment AddConstructionEquipment(ConstructionEquipment conequadd) {
        return constructionEquipmentRepository.save(conequadd);
    }

    @Override
    @Transactional

    public void finalizeInspection(int inspectionId) {
        EquipmentInspection inspection = equipmentInspectionRepository.findById(inspectionId)
                .orElseThrow(() -> new RuntimeException("Inspection not found with id: " + inspectionId));

        if (inspection.getResult() == OverallResult.FAIL) {
            if (inspection.getItemType() == ItemType.EQUIPMENT) {
                ConstructionEquipment equipment = inspection.getEquipment();
                if (equipment != null) {
                    equipment.setStatusEquipment(StatusEquipment.NEEDS_MAINTENANCE);
                    constructionEquipmentRepository.save(equipment);
                }
            }
            else if (inspection.getItemType() == ItemType.PPE) {
                PPE ppe = inspection.getPpe();
                if (ppe != null) {
                    ppe.setStatus(StatusPPE.NEEDS_REPLACEMENT);
                    ppeRepository.save(ppe);
                }
            }
        } else {
            if (inspection.getItemType() == ItemType.EQUIPMENT) {
                ConstructionEquipment equipment = inspection.getEquipment();
                if (equipment != null) {
                    equipment.setStatusEquipment(StatusEquipment.OPERATIONAL);
                    constructionEquipmentRepository.save(equipment);
                }
            }
            else if (inspection.getItemType() == ItemType.PPE) {
                PPE ppe = inspection.getPpe();
                if (ppe != null) {
                    ppe.setStatus(StatusPPE.ACTIVE);
                    ppeRepository.save(ppe);
                }
            }
        }

        // Send a notification once the inspection is finalized
        sendNotification(inspection);
    }

    private void sendNotification(EquipmentInspection inspection) {
        // Construct the message based on the inspection result and item type
        String message = "Inspection for " + (inspection.getItemType() == ItemType.EQUIPMENT ? "equipment" : "PPE")
                + " with ID: " + inspection.getInspectionId()
                + " has been finalized. Result: " + inspection.getResult    ();

        // In a real-world scenario, you might send this as an email or push notification
        // For simplicity, here we just print it to the console
        System.out.println("Notification: " + message);

        // You can extend this logic to send emails, push notifications, etc.
    }

    @Override
    public ConstructionEquipment updateConstructionEquipment(int id, Date lastMaintenance, Date maintenanceDate) {
        return constructionEquipmentRepository.findById(id).map(equipment -> {
            equipment.setLastMaintenance(lastMaintenance);
            equipment.setMaintenanceDate(maintenanceDate);
            return constructionEquipmentRepository.save(equipment);
        }).orElse(null);
    }


    public List<ConstructionEquipment> getAllConstructionEquipment() {
        return constructionEquipmentRepository.findAll();
    }

//    @Override
//    public EquipmentInspection AddEquipmentInspection(EquipmentInspection equipmentinspection) {
//        return equipmentInspectionRepository.save(equipmentinspection);
//    }

    @Override
    public EquipmentInspection AddEquipmentInspection(EquipmentInspection equipmentInspection, int itemId) {
        if (equipmentInspection.getItemType() == ItemType.EQUIPMENT) {
            // Retrieve Equipment by ID
            ConstructionEquipment equipment = constructionEquipmentRepository.findById(itemId)
                    .orElseThrow(() -> new RuntimeException("Equipment not found"));

            // Link the inspection to the equipment
            equipmentInspection.setEquipment(equipment);

        } else if (equipmentInspection.getItemType() == ItemType.PPE) {
            // Retrieve PPE by ID
            PPE ppe = ppeRepository.findById(itemId)
                    .orElseThrow(() -> new RuntimeException("PPE not found"));

            // Link the inspection to the PPE
            equipmentInspection.setPpe(ppe);
        } else {
            throw new IllegalArgumentException("Invalid itemType");
        }

        // Save the inspection
        return equipmentInspectionRepository.save(equipmentInspection);
    }


//    @Override
//    public EquipmentInspection AddEquipmentInspection(EquipmentInspection equipmentinspection) {
//        // Assuming you have a repository for ConstructionEquipment
//        ConstructionEquipment equipment = constructionEquipmentRepository.findById(
//                equipmentinspection.getEquipment().getEquipment_id()
//        ).orElseThrow(() -> new RuntimeException("Equipment not found"));
//
//        equipmentinspection.setEquipment(equipment);
//        return equipmentInspectionRepository.save(equipmentinspection);
//    }




    @Override
    public void deleteEquipmentInspection(Integer id) {
        if (equipmentInspectionRepository.existsById(id)) {
            equipmentInspectionRepository.deleteById(id);
        }

    }


//////////////////////////////////////////////////////////////////////////

    @Override
    public double calculateEquipmentHealthScore(int equipementId){
        ConstructionEquipment equipment = constructionEquipmentRepository.findById(equipementId).orElse(null);


        double score = 100.0;

        // Deduct 10 points per failed inspection
        int failedInspections = (int) equipment.getInspections().stream()
                .filter(insp -> insp.getResult() == OverallResult.FAIL)
                .count();
        score -= failedInspections * 10;

        // Deduct 0.5 points per overdue day
        Date nextMaintenance = equipment.getMaintenanceDate();
        if (nextMaintenance != null && new Date().after(nextMaintenance)) {
            long daysOverdue = (new Date().getTime() - nextMaintenance.getTime()) / (1000 * 60 * 60 * 24);
            score -= daysOverdue * 0.5;
        }

        return Math.max(score, 0);
    }

    @Override
    public EquipmentInspection updateEquipmentInspection(int id, EquipmentInspection updatedInspection) {
        Optional<EquipmentInspection> existingInspectionOpt = equipmentInspectionRepository.findById(id);

        if (existingInspectionOpt.isPresent()) {
            EquipmentInspection existingInspection = existingInspectionOpt.get();

            // Update only the required fields
            existingInspection.setInspectionDate(updatedInspection.getInspectionDate());
            existingInspection.setResult(updatedInspection.getResult());
            existingInspection.setRemarks(updatedInspection.getRemarks());

            return equipmentInspectionRepository.save(existingInspection);
        } else {
            throw new RuntimeException("EquipmentInspection with ID " + id + " not found.");
        }
    }


    public List<EquipmentInspection> getAllEquipmentInspection() {
        return equipmentInspectionRepository.findAll();
    }

    public EquipmentInspection getEquipmentInspectionById(int id) {
        return equipmentInspectionRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteConstructionEquipment(int id) {
        if (constructionEquipmentRepository.existsById(id)) {
            constructionEquipmentRepository.deleteById(id);
        }
    }

    public List<String> getSafetyAlerts() {
        List<String> alerts = new ArrayList<>();

        // ✅ List of required PPE types
        List<String> requiredPPE = Arrays.asList("Helmet", "Gloves", "Suit", "Boots", "Goggles"); // Add more if needed

        // 🔍 Check for missing PPE for employees
        List<Employee> employees = employeeRepository.findAll();
        for (Employee employee : employees) {
            for (String ppeType : requiredPPE) {
                boolean hasPPE = employee.getPPEs().stream()
                        .anyMatch(ppe -> ppe.getType().equalsIgnoreCase(ppeType));
                if (!hasPPE) {
                    // Updated to use employee's name instead of ID
                    alerts.add("⚠ " + employee.getFirstName() + " is missing " + ppeType + "!");
                }
            }
        }

        // 🔍 Check for construction equipment failures
        List<ConstructionEquipment> equipmentList = constructionEquipmentRepository.findAll();
        for (ConstructionEquipment equipment : equipmentList) {
            if (equipment.getStatusEquipment() == StatusEquipment.NEEDS_MAINTENANCE) {
                // Updated to use equipment name instead of ID
                alerts.add("⚠ " + equipment.getType() + " requires maintenance.");
            }
            if (equipment.getStatusEquipment() == StatusEquipment.OUT_OF_SERVICE) {
                // Updated to use equipment name instead of ID
                alerts.add("⛔ " + equipment.getType() + " is out of service!");
            }
        }

        return alerts;
    }
    public List<SafetyAlert> getActiveSafetyAlerts() {
        return safetyAlertRepository.findAll(); // Adjust this if you have a specific query for active alerts
    }





}

























//public void finalizeInspection(int inspectionId) {
//    EquipmentInspection inspection = equipmentInspectionRepository.findById(inspectionId)
//            .orElseThrow(() -> new RuntimeException("Inspection not found with id: " + inspectionId));
//
//    if (inspection.getResult() == OverallResult.FAIL) {
//        if (inspection.getItemType() == ItemType.EQUIPMENT) {
//            ConstructionEquipment equipment = inspection.getEquipment();
//            if (equipment != null) {
//                equipment.setStatusEquipment(StatusEquipment.NEEDS_MAINTENANCE);
//                // ✅ Automatically update health score
//                equipment.setHealthScore(calculateEquipmentHealthScore(equipment.getEquipment_id()));
//                constructionEquipmentRepository.save(equipment);
//            }
//        }
//        else if (inspection.getItemType() == ItemType.PPE) {
//            PPE ppe = inspection.getPpe();
//            if (ppe != null) {
//                ppe.setStatus(StatusPPE.NEEDS_REPLACEMENT);
//                ppeRepository.save(ppe);
//            }
//        }
//    }
//    else { // Successful inspection
//        if (inspection.getItemType() == ItemType.EQUIPMENT) {
//            ConstructionEquipment equipment = inspection.getEquipment();
//            if (equipment != null) {
//                equipment.setStatusEquipment(StatusEquipment.OPERATIONAL);
//                // ✅ Recalculate health score for a successful inspection
//                equipment.setHealthScore(calculateEquipmentHealthScore(equipment.getEquipment_id()));
//                constructionEquipmentRepository.save(equipment);
//            }
//        }
//        else if (inspection.getItemType() == ItemType.PPE) {
//            PPE ppe = inspection.getPpe();
//            if (ppe != null) {
//                ppe.setStatus(StatusPPE.ACTIVE);
//                ppeRepository.save(ppe);
//            }
//        }
//    }
//}
