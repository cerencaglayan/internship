package com.example.demo.myproject.repository;

import com.example.demo.myproject.models.City;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<City,Integer> {
    City findByName(String name);

}
