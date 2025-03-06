package tn.esprit.pigc.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ConstructionEquipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int equipment_id;
    private String type;
    private String serialNumber;
    private Date purchaseDate;
    private Date lastMaintenance;
    private Date maintenanceDate;
    //private double healthScore;

    @Enumerated(EnumType.STRING)
    private StatusEquipment statusEquipment;
   // private Date lastInspectionDate;

    @OneToMany(mappedBy = "equipment", cascade = CascadeType.ALL)
     List<EquipmentInspection> inspections = new ArrayList<>();



}
