package com.example.demo.myproject.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

import java.util.ArrayList;
import java.util.List;

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

    @ManyToMany(mappedBy = "parentDepartments")
    private List<Department> childDepartments;

    @ManyToMany
    @JoinTable(
            name = "department_hierarchy",
            joinColumns = @JoinColumn(name = "child_department_id"),
            inverseJoinColumns = @JoinColumn(name = "parent_department_id")
    )
    private List<Department> parentDepartments;

    @ManyToOne
    @JoinColumn(name = "companyId", nullable = false)
    private Company companyId;

    @ManyToOne
    @JoinColumn(name = "departmentTypeId", nullable = false)
    private DepartmentType departmentType;


    private String addressStreet;

    @ManyToOne
    @JoinColumn(name = "addressTown", nullable = true)
    @Nullable
    private Town addressTown;

}
