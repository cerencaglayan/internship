package com.example.demo.myproject.repository;

import com.example.demo.myproject.models.DepartmentType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentTypeRepository extends JpaRepository<DepartmentType,Integer> {
}
