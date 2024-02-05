package com.example.demo.myproject.repository;

import com.example.demo.myproject.models.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company,Integer> {
    Company findByName(String name);

}
