package com.inventory.web.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "equipment_unit")
public class EquipmentUnit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "equipment_unit_id")
    private Long equipmentUnitId;

    @Column(name = "inventory_number")
    private String inventoryNumber;

    @ManyToOne
    @JoinColumn(name = "equipment_id")
    private Equipment equipment;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;


    @Column(name = "on_state")
    private boolean onState;

    @Column(name = "guid_code")
    private UUID guidCode;
}

