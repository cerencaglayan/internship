package com.example.demo.myproject.service;

import com.example.demo.myproject.models.Region;
import com.example.demo.myproject.repository.RegionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RegionService {
    private final RegionRepository regionRepository;

    public List<Region> getRegions(){
        return  regionRepository.findAll();
    }
}
