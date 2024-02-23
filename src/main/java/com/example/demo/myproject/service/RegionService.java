package com.example.demo.myproject.service;

import com.example.demo.myproject.controller.dto.RegionDto;
import com.example.demo.myproject.models.Region;
import com.example.demo.myproject.models.Region;
import com.example.demo.myproject.repository.RegionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RegionService {
    private final RegionRepository regionRepository;

    public List<Region> getRegions(){
        return  regionRepository.findAll();
    }

    public ResponseEntity<Region> getRegionById(Integer id) {
        Optional<Region> region = regionRepository.findById(id);
        if (region.isPresent()) {
            return ResponseEntity.ok()
                    .body(region.get());
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(null);

    }

    public ResponseEntity<Region> createRegion(RegionDto regionDto) {
        Region region = new Region(regionDto.getName());

        regionRepository.save(region);

        return ResponseEntity
                .ok()
                .body(region);
    }


    public ResponseEntity<Region> deleteRegion(Integer id) {
        Optional<Region> region = regionRepository.findById(id);
        if (region.isPresent()) {

            regionRepository.delete(region.get());

            return ResponseEntity.ok()
                    .body(region.get());
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(null);
    }


    public ResponseEntity<Region> updateRegion(Integer id, RegionDto regionDetails) {

        Optional<Region> optionalRegion = regionRepository.findById(id);

        if (optionalRegion.isPresent()) {
            Region region = optionalRegion.get();
            region.setName(regionDetails.getName());
            regionRepository.save(region);
            return ResponseEntity.ok()
                    .body(region);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null);
        }

    }
    
    
    
    
    
}
