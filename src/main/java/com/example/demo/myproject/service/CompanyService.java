package com.example.demo.myproject.service;


import com.example.demo.myproject.controller.dto.CompanyDto;
import com.example.demo.myproject.models.Company;
import com.example.demo.myproject.models.CompanyType;
import com.example.demo.myproject.models.Town;
import com.example.demo.myproject.repository.CompanyRepository;
import com.example.demo.myproject.repository.CompanyRepository;
import com.example.demo.myproject.repository.CompanyTypeRepository;
import com.example.demo.myproject.repository.TownRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CompanyService {
    private final CompanyRepository companyRepository;

    private final CompanyTypeRepository companyTypeRepository;
    private final TownRepository townRepository;



    public List<Company> getCompanies() {
        return companyRepository.findAll();

    }


    public ResponseEntity<Company> getCompanyById(Integer id) {
        Optional<Company> company = companyRepository.findById(id);
        if (company.isPresent()) {
            return ResponseEntity.ok()
                    .body(company.get());
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(null);

    }

    public ResponseEntity<Company> createCompany(CompanyDto companyDto) {
        Company company = new Company();


        CompanyType companyType = companyTypeRepository.findByName(companyDto.getCompanyType());

        Town town = townRepository.findByName(companyDto.getAddressTown());

        company.setName(companyDto.getName());
        company.setShortName(companyDto.getShortName());
        company.setCompanyType(companyType);
        company.setAddressStreet(companyDto.getAddressStreet());
        company.setAddressTown(town);
        companyRepository.save(company);

        return ResponseEntity.ok()
                .body(company);
        }


    public ResponseEntity<Company> deleteCompany(Integer id) {
        Optional<Company> company = companyRepository.findById(id);

        if (company.isPresent()) {

            companyRepository.delete(company.get());

            return ResponseEntity.ok()
                    .body(company.get());
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(null);
    }


    public ResponseEntity<Company> updateCompany(Integer id, CompanyDto companyDetails) {

        Optional<Company> optionalCompany = companyRepository.findById(id);

        CompanyType optionalCompanyType = companyTypeRepository.findByName(companyDetails.getCompanyType());

        Town town = townRepository.findByName(companyDetails.getAddressTown());


        if (optionalCompany.isPresent()) {
            Company company = optionalCompany.get();
            company.setName(companyDetails.getName());
            company.setShortName(companyDetails.getShortName());
            company.setCompanyType(optionalCompanyType);
            company.setAddressStreet(companyDetails.getAddressStreet());
            company.setAddressTown(town);
            companyRepository.save(company);
            return ResponseEntity.ok()
                    .body(company);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null);
        }

    }

}
