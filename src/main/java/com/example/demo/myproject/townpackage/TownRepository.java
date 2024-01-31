package com.example.demo.myproject.townpackage;

import com.example.demo.myproject.citypackage.City;
import com.example.demo.myproject.regionpackage.Region;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TownRepository extends JpaRepository<Town,Integer> {
    Town findByName(String name);

    Town findByRegion(Region region);
    Town findByCity(City city);
}
