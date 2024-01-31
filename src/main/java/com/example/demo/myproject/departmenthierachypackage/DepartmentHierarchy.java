package com.example.demo.myproject.departmenthierachypackage;

import com.example.demo.myproject.departmentpackage.Department;
import jakarta.persistence.*;

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

    public DepartmentHierarchy() {
    }

    public DepartmentHierarchy(Integer id, Department childDepartment, Department parentDepartment) {
        this.id = id;
        this.childDepartment = childDepartment;
        this.parentDepartment = parentDepartment;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Department getChildDepartment() {
        return childDepartment;
    }

    public void setChildDepartment(Department childDepartment) {
        this.childDepartment = childDepartment;
    }

    public Department getParentDepartment() {
        return parentDepartment;
    }

    public void setParentDepartment(Department parentDepartment) {
        this.parentDepartment = parentDepartment;
    }
}

