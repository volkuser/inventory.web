package com.inventory.web.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Equipment {
    private Long equipmentId;
    private String modelName;
    private EquipmentType equipmentType;
    private String manufacturer;
}
