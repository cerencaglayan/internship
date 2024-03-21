package com.example.demo.myproject.service;

import com.example.demo.myproject.models.Company;
import com.example.demo.myproject.models.CompanyType;
import com.example.demo.myproject.repository.CompanyTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CompanyTypeService {

    private final CompanyTypeRepository companyTypeRepository;

    public ResponseEntity<List<CompanyType>> getCompanyTypes() {
        return ResponseEntity.status(200)
                .body(companyTypeRepository.findAll());
    }


    public ResponseEntity<CompanyType> getCompanyTypeById(Integer id) {

        Optional<CompanyType> companyType = companyTypeRepository.findById(id);

        return companyType.map(
                type -> ResponseEntity.status(HttpStatus.OK).body(type)).orElseGet(()
                -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));

    }
}
