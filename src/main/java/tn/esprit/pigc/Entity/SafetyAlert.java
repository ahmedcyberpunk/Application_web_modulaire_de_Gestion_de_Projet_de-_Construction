package tn.esprit.pigc.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class SafetyAlert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer safetyalert_id;

    @Enumerated(EnumType.STRING)
    private AlertType alertType; // Type of alert (e.g., Equipment Failure, Missing PPE)

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = true) // Nullable because some alerts might be equipment-related
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "equipment_id", nullable = true) // Nullable because some alerts might be PPE-related
    private ConstructionEquipment equipment;

    private LocalDateTime timestamp; // Time when the alert was triggered

    private boolean resolved = false; // Track whether the alert has been dismissed

}
