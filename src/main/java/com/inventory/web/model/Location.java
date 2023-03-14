package com.inventory.web.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "location")
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "location_id")
    private Long locationId;

    @NotBlank
    @Column(name = "location_number", nullable = false)
    private String locationNumber;

    @ManyToOne
    @JoinColumn(name = "training_center_id", referencedColumnName = "training_center_id")
    private TrainingCenter trainingCenter;
}
