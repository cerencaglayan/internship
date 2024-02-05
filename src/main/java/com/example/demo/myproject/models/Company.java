package com.example.demo.myproject.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Company")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Column(name = "shortName")
    private String shortName;

    @ManyToOne
    @JoinColumn(name = "companyTypeId", nullable = false)
    private CompanyType companyType;

    private String addressStreet;


    @ManyToOne
    @JoinColumn(name = "addressTown", nullable = false)
    private Town addressTown;


}
