package com.example.demo.myproject.repository;

import com.example.demo.myproject.models.Region;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegionRepository extends JpaRepository<Region,Integer> {
    Region findByName(String name);
}
