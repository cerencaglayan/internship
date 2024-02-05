package com.example.demo.myproject.repository;

import com.example.demo.myproject.models.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department,Integer> {

    Department findByName(String name);

}
