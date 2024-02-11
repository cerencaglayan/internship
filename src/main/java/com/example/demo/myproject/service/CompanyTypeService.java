package com.example.demo.myproject.service;

import com.example.demo.myproject.models.CompanyType;
import com.example.demo.myproject.repository.CompanyTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyTypeService {

    private final CompanyTypeRepository companyTypeRepository;

    public List<CompanyType> getCompanyTypes(){
        return  companyTypeRepository.findAll();
    }

}
