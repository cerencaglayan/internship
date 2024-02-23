package com.example.demo.myproject.service;

import com.example.demo.myproject.controller.dto.TownDto;
import com.example.demo.myproject.models.City;
import com.example.demo.myproject.models.Region;
import com.example.demo.myproject.models.Town;
import com.example.demo.myproject.models.Town;
import com.example.demo.myproject.repository.CityRepository;
import com.example.demo.myproject.repository.TownRepository;
import com.example.demo.myproject.repository.RegionRepository;
import com.example.demo.myproject.repository.TownRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TownService {

    private final TownRepository townRepository;
    private final CityRepository cityRepository;
    private final RegionRepository regionRepository;
    public List<Town> getTowns() {
        return townRepository.findAll();
    }

    public ResponseEntity<Town> getTownById(Integer id) {
        Optional<Town> town = townRepository.findById(id);
        if (town.isPresent()) {
            return ResponseEntity.ok()
                    .body(town.get());
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(null);

    }

    public ResponseEntity<Town> createTown(TownDto townDto) {
        Town town = new Town();

        City city = cityRepository.findByName(townDto.getCity());
        Region region = regionRepository.findByName(townDto.getRegion());

        town.setName(townDto.getName());
        town.setCity(city);
        town.setRegion(region);

        townRepository.save(town);

        return ResponseEntity
                .ok()
                .body(town);
    }


    public ResponseEntity<Town> deleteTown(Integer id) {
        Optional<Town> town = townRepository.findById(id);
        if (town.isPresent()) {

            townRepository.delete(town.get());

            return ResponseEntity.ok()
                    .body(town.get());
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(null);
    }


    public ResponseEntity<Town> updateTown(Integer id, TownDto townDetails) {

        Optional<Town> optionalTown = townRepository.findById(id);

        if (optionalTown.isPresent()) {
            Town town = optionalTown.get();
            town.setName(townDetails.getName());
            town.setRegion(regionRepository.findByName(townDetails.getRegion()));
            town.setCity(cityRepository.findByName(townDetails.getCity()));
            townRepository.save(town);
            return ResponseEntity.ok()
                    .body(town);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null);
        }

    }
    
    
    
}
