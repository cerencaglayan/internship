package com.example.demo.myproject.controller;

import com.example.demo.myproject.models.City;
import com.example.demo.myproject.service.CityService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/city")
@PreAuthorize("hasAnyRole('ADMIN')")
public class CityController {

    private final CityService cityService;

    @GetMapping
    public List<City> getCities(){
        return  cityService.getCities();
    }
}
