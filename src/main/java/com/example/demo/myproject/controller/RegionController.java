package com.example.demo.myproject.controller;

import com.example.demo.myproject.models.Region;
import com.example.demo.myproject.service.RegionService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
