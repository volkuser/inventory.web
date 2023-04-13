package com.inventory.web.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Location {
    private Long locationId;
    private String locationNumber;
    private TrainingCenter trainingCenter;
}
