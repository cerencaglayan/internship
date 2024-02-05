package com.example.demo.myproject.repository;

import com.example.demo.myproject.models.City;
import com.example.demo.myproject.models.Region;
import com.example.demo.myproject.models.Town;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TownRepository extends JpaRepository<Town,Integer> {
    Town findByName(String name);

    Town findByRegion(Region region);
    Town findByCity(City city);
}
