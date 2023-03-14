package com.inventory.web.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "equipment_status")
public class EquipmentStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "equipment_status_id")
    private Long equipmentStatusId;

    @NotNull
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private EquipmentStatusEnum status;

    public enum EquipmentStatusEnum {
        AT_THE_PLACE, NOT_AT_THE_PLACE, IN_ANOTHER_PLACE
    }
    /*public enum EquipmentStatusEnum {
        "На месте", "Нет на месте", "В другом месте"
    }*/
}
