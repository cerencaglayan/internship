package com.example.demo.myproject.departmentpackage;

import com.example.demo.myproject.companypackage.Company;
import com.example.demo.myproject.departmenttypepackage.DepartmentType;
import com.example.demo.myproject.townpackage.Town;
import jakarta.persistence.*;

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

    public Department() {
    }

    public Department(Integer id, String name, Company companyId, DepartmentType departmentType, String addressStreet, Town addressTown) {
        this.id = id;
        this.name = name;
        this.companyId = companyId;
        this.departmentType = departmentType;
        this.addressStreet = addressStreet;
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

    public Company getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Company companyId) {
        this.companyId = companyId;
    }

    public DepartmentType getDepartmentType() {
        return departmentType;
    }

    public void setDepartmentType(DepartmentType departmentType) {
        this.departmentType = departmentType;
    }

    public String getAddressStreet() {
        return addressStreet;
    }

    public void setAddressStreet(String addressStreet) {
        this.addressStreet = addressStreet;
    }

    public Town getAddressTown() {
        return addressTown;
    }

    public void setAddressTown(Town addressTown) {
        this.addressTown = addressTown;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
