package tn.esprit.pigc.Service;

import tn.esprit.pigc.Entity.ConstructionEquipment;
import tn.esprit.pigc.Entity.Employee;
import tn.esprit.pigc.Entity.EquipmentInspection;
import tn.esprit.pigc.Entity.PPE;

import java.util.Date;

public interface ISM {

    PPE AddPPE(PPE ppeadd, int employeeId);
    void deletePPE(Integer id);
    Employee AddEmployee(Employee employeadd);
    ConstructionEquipment AddConstructionEquipment(ConstructionEquipment conequadd);
    void finalizeInspection(int inspectionId);
    ConstructionEquipment updateConstructionEquipment(int id, Date lastMaintenance , Date maintenanceDate);
    EquipmentInspection AddEquipmentInspection(EquipmentInspection equipmentInspection, int itemId);
    void deleteEquipmentInspection(Integer id);
    public double calculateEquipmentHealthScore(int equipementId);
    public EquipmentInspection updateEquipmentInspection(int id, EquipmentInspection updatedInspection);
    public EquipmentInspection getEquipmentInspectionById(int id);
    void deleteConstructionEquipment(int id);

}
