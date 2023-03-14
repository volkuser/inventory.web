package com.inventory.web.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
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

    @ManyToOne
    @JoinColumn(name = "equipment_id")
    private Equipment equipment;

    @Column(name = "serial_number", length = 50)
    private String serialNumber;

    @Column(name = "purchase_date")
    private Date purchaseDate;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    @ManyToOne
    @JoinColumn(name = "current_status")
    private EquipmentStatus currentStatus;

    @ManyToOne
    @JoinColumn(name = "responsible_person_id")
    private Employee responsiblePerson;

    @Column(name = "guid_code")
    private UUID guidCode;
}

