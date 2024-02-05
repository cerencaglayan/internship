package com.example.demo.myproject.repository;

import com.example.demo.myproject.models.DepartmentHierarchy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentHierarchyRepository extends JpaRepository<DepartmentHierarchy,Integer> {
}
