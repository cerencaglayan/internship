package com.example.demo.myproject.controller;

import com.example.demo.myproject.controller.dto.TownDto;
import com.example.demo.myproject.models.Town;
import com.example.demo.myproject.models.Town;
import com.example.demo.myproject.service.TownService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/town")
@PreAuthorize("hasAnyRole('ADMIN')")
public class TownController {
    private final TownService townService;

    @GetMapping
    public List<Town> getTowns(){
        return  townService.getTowns();
    }


    @GetMapping("/{id}")
    public ResponseEntity<Town> getTownById(@PathVariable Integer id) {
        return townService.getTownById(id);
    }

    @PostMapping
    public ResponseEntity<Town> createTown(@RequestBody TownDto townDto) {
        return townService.createTown(townDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Town> updateTown(@PathVariable Integer id, @RequestBody TownDto townDetails) {
        return townService.updateTown(id, townDetails);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Town> deleteTown(@PathVariable Integer id) {
        return townService.deleteTown(id);
    }




}
