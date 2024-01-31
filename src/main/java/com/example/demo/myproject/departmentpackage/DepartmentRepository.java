package com.example.demo.myproject.departmentpackage;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department,Integer> {

    Department findByName(String name);

}
