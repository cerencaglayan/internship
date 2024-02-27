package com.example.demo.myproject.controller.dto;

import com.example.demo.myproject.models.CompanyType;
import com.example.demo.myproject.models.Town;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CompanyDto {

    private String name;

    private String shortName;

    private String companyType;

    private String addressStreet;

    private String addressTown;
}
