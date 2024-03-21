package com.example.demo.myproject.controller;

import com.example.demo.myproject.controller.dto.CityDto;
import com.example.demo.myproject.models.City;
import com.example.demo.myproject.service.CityService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/city")
public class CityController {

    private final CityService cityService;

    @GetMapping
    public List<City> getCities(){
        return  cityService.getCities();
    }

    @GetMapping("/{id}")
    public ResponseEntity<City> getCityById(@PathVariable Integer id) {
        return cityService.getCityById(id);
    }

    @PostMapping
    public ResponseEntity<City> createCity(@RequestBody CityDto cityDto) {
        return cityService.createCity(cityDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<City> updateCity(@PathVariable Integer id, @RequestBody CityDto cityDetails) {
        return cityService.updateCity(id, cityDetails);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<City> deleteCity(@PathVariable Integer id) {
        return cityService.deleteCity(id);
    }




}
