package tn.esprit.pigc.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class EquipmentInspection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int  inspectionId;
    private Date inspectionDate;

    @Enumerated(EnumType.STRING)
    private OverallResult result;

    private String remarks;

    @Enumerated(EnumType.STRING)
    private ItemType itemType;

//    public EquipmentInspection(ItemType itemType, ConstructionEquipment equipment, PPE ppe) {
//        this.itemType = itemType;
//        if (itemType == ItemType.EQUIPMENT) {
//            this.equipment = equipment;
//            this.ppe = null;  // Ensure PPE is null
//        } else {
//            this.ppe = ppe;
//            this.equipment = null;  // Ensure Equipment is null
//        }
//    }


    // private int itemId;

    @JsonIgnore
    @ManyToOne
    ConstructionEquipment equipment;

    @JsonIgnore
    @ManyToOne
    PPE ppe;




}
