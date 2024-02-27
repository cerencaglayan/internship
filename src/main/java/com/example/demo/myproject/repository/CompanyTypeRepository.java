package com.example.demo.myproject.repository;

import com.example.demo.myproject.models.CompanyType;
import com.example.demo.myproject.models.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyTypeRepository extends JpaRepository<CompanyType,Integer> {
    CompanyType findByName(String name);

}
