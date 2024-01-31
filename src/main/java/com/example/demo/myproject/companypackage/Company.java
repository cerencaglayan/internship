package com.example.demo.myproject.companypackage;

import com.example.demo.myproject.companytypepackage.CompanyType;
import com.example.demo.myproject.townpackage.Town;
import jakarta.persistence.*;

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


    public Company() {
    }

    public Company(Integer id, String name, String shortName, CompanyType companyType, Town addressTown) {
        this.id = id;
        this.name = name;
        this.shortName = shortName;
        this.companyType = companyType;
        this.addressTown = addressTown;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public CompanyType getCompanyType() {
        return companyType;
    }

    public void setCompanyType(CompanyType companyType) {
        this.companyType = companyType;
    }

    public Town getAddressTown() {
        return addressTown;
    }

    public void setAddressTown(Town addressTown) {
        this.addressTown = addressTown;
    }
    public String getaddressStreet() {
        return addressStreet;
    }

    public void setaddressStreet(String addressStreet) {
        this.addressStreet = addressStreet;
    }
}
