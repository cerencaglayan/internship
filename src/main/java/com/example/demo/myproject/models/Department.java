package com.example.demo.myproject.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "Department")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "companyId", nullable = false)
    private Company companyId;

    @ManyToOne
    @JoinColumn(name = "departmentTypeId", nullable = false)
    private DepartmentType departmentType;


    private String addressStreet;

    @ManyToOne
    @JoinColumn(name = "addressTown", nullable = false)
    private Town addressTown;

}
