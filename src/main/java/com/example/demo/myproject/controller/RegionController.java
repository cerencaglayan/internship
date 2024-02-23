package com.example.demo.myproject.controller;


import com.example.demo.myproject.controller.dto.RegionDto;
import com.example.demo.myproject.models.Region;
import com.example.demo.myproject.service.RegionService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/region")
@PreAuthorize("hasAnyRole('ADMIN')")
public class RegionController {
    private final RegionService regionService;

    @GetMapping
    public List<Region> getRegions(){
        return  regionService.getRegions();
    }
    @GetMapping("/{id}")
    public ResponseEntity<Region> getRegionById(@PathVariable Integer id) {
        return regionService.getRegionById(id);
    }

    @PostMapping
    public ResponseEntity<Region> createRegion(@RequestBody RegionDto regionDto) {
        return regionService.createRegion(regionDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Region> updateRegion(@PathVariable Integer id, @RequestBody RegionDto regionDetails) {
        return regionService.updateRegion(id, regionDetails);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Region> deleteRegion(@PathVariable Integer id) {
        return regionService.deleteRegion(id);
    }
    
    
}
