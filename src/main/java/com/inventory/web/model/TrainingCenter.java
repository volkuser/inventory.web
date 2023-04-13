package com.inventory.web.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrainingCenter {
    private Long trainingCenterId;
    private String centerAddress;
}
