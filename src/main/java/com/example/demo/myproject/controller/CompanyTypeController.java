package com.example.demo.myproject.controller;

import com.example.demo.myproject.models.Company;
import com.example.demo.myproject.models.CompanyType;
import com.example.demo.myproject.service.CompanyTypeService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    public ResponseEntity<List<CompanyType>> getCompanyTypes() {
        return companyTypeService.getCompanyTypes();
    };

    @GetMapping("/{id}")
    public ResponseEntity<CompanyType> getCompanyById(@PathVariable Integer id) {
        return companyTypeService.getCompanyTypeById(id);
    }

}
