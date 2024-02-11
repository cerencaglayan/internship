package com.example.demo.myproject.controller;

import com.example.demo.myproject.models.CompanyType;
import com.example.demo.myproject.service.CompanyTypeService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/companyType")
@PreAuthorize("hasAnyRole('ADMIN')")
public class CompanyTypeController {

    private final CompanyTypeService companyTypeService;

    @GetMapping
    public List<CompanyType> getCompanyTypes(){
        return  companyTypeService.getCompanyTypes();
    }
}
