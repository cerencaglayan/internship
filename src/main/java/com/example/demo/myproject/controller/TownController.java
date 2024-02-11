package com.example.demo.myproject.controller;

import com.example.demo.myproject.models.Town;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
