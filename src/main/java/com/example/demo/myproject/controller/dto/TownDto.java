package com.example.demo.myproject.controller.dto;

import com.example.demo.myproject.models.City;
import com.example.demo.myproject.models.Region;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TownDto {

    private String name;

    private String region;

    private String city;
}
