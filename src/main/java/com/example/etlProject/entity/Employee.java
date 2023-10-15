package com.example.etlProject.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.util.Date;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long employeeId;

    @NonNull
    private String firstName;

    @NonNull
    private String lastName;

    @NonNull
    private Date dateOfBirth;

    private String emailAddress;

    private String department;

    private int age;

}
