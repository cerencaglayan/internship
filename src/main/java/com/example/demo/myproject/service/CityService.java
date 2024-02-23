package com.example.demo.myproject.service;

import com.example.demo.myproject.controller.dto.CityDto;
import com.example.demo.myproject.models.City;
import com.example.demo.myproject.repository.CityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CityService {

    private final CityRepository cityRepository;

    public List<City> getCities() {
        return cityRepository.findAll();
    }

    public ResponseEntity<City> getCityById(Integer id) {
        Optional<City> city = cityRepository.findById(id);
        if (city.isPresent()) {
            return ResponseEntity.ok()
                    .body(city.get());
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(null);

    }

    public ResponseEntity<City> createCity(CityDto cityDto) {
        City city = new City();
        city.setName(cityDto.getName());

        cityRepository.save(city);

        return ResponseEntity
                .ok()
                .body(city);
    }


    public ResponseEntity<City> deleteCity(Integer id) {
        Optional<City> city = cityRepository.findById(id);
        if (city.isPresent()) {

            cityRepository.delete(city.get());

            return ResponseEntity.ok()
                    .body(city.get());
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(null);
    }


    public ResponseEntity<City> updateCity(Integer id, CityDto cityDetails) {

        Optional<City> optionalCity = cityRepository.findById(id);

        if (optionalCity.isPresent()) {
            City city = optionalCity.get();
            city.setName(cityDetails.getName());
            cityRepository.save(city);
            return ResponseEntity.ok()
                    .body(city);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null);
        }

    }
}
