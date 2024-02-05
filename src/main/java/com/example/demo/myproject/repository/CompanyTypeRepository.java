package com.example.demo.myproject.repository;

import com.example.demo.myproject.models.CompanyType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyTypeRepository extends JpaRepository<CompanyType,Integer> {
}
