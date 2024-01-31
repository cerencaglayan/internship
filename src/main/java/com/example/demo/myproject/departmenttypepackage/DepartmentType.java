package com.example.demo.myproject.departmenttypepackage;

import jakarta.persistence.*;

@Entity
@Table(name = "DepartmentType")
public class DepartmentType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;


    public DepartmentType() {
    }

    public DepartmentType(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}