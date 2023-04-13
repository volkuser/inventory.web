package com.inventory.web.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    private Long employeeId;
    private String firstName;
    private String lastName;
    private String patronymic;
    private String viewPosition;
    private String role;
    private User user;
}
