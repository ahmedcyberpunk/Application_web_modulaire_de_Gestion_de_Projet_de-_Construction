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
    @NoArgsConstructor
    @AllArgsConstructor

    public class PPE {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int ppe_id;
        private String type;
        private String serialNumber;

        @Enumerated(EnumType.STRING)
        private StatusPPE status = StatusPPE.ACTIVE;

        @JsonIgnore
        @ManyToOne(cascade = CascadeType.ALL)
        Employee employee;


        @OneToMany(mappedBy = "ppe", cascade = CascadeType.ALL)
        List<EquipmentInspection> inspectionsforppe = new ArrayList<>();


             }
