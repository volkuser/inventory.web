package com.inventory.web.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id")
    private Long employeeId;

    @Column(name = "first_name", nullable = false)
    @NotNull
    @Size(max = 50)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    @NotNull
    @Size(max = 50)
    private String lastName;

    @Column(name = "patronymic", nullable = false)
    @NotNull
    @Size(max = 50)
    private String patronymic;

    @Column(name = "view_position", nullable = false)
    @NotNull
    @Size(max = 100)
    private String viewPosition;

    @Column(name = "role", nullable = false)
    @NotNull
    @Size(max = 50)
    private String role;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    @NotNull
    private User user;
}
