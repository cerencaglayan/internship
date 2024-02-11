package com.example.demo.myproject.controller;

import com.example.demo.myproject.models.Town;
import com.example.demo.myproject.repository.TownRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TownService {

    private final TownRepository townRepository;
    public List<Town> getTowns() {
        return townRepository.findAll();
    }
}
