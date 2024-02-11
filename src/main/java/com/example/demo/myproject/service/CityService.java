package com.example.demo.myproject.service;

import com.example.demo.myproject.models.City;
import com.example.demo.myproject.repository.CityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CityService {

    private final CityRepository cityRepository;

    public List<City> getCities() {
        return cityRepository.findAll();
    }
}
