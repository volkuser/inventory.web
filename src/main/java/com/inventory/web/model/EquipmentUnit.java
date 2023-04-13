package com.inventory.web.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EquipmentUnit {
    private Long equipmentUnitId;
    private String inventoryNumber;
    private Equipment equipment;
    private Location location;
    private boolean onState;
    private UUID guidCode;
}

