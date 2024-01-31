package com.example.demo.myproject.regionpackage;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RegionRepository extends JpaRepository<Region,Integer> {
    Region findByName(String name);
}
