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
@Table(name = "DepartmentHierarchy")
public class DepartmentHierarchy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "childDepartmentId", nullable = false)
    private Department childDepartment;

    @ManyToOne
    @JoinColumn(name = "parentDepartmentId", nullable = false)
    private Department parentDepartment;

}

