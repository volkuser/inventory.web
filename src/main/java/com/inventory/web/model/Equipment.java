package com.inventory.web.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "equipment")
public class Equipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "equipment_id")
    private Long equipmentId;

    @NotBlank
    @Size(max = 100)
    @Column(name = "model_name")
    private String modelName;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "equipment_type_id")
    private EquipmentType equipmentType;

    @Size(max = 50)
    @Column(name = "manufacturer")
    private String manufacturer;
}
