package com.example.demo.myproject.service;

import com.example.demo.myproject.models.CompanyType;
import com.example.demo.myproject.models.Department;
import com.example.demo.myproject.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    public ResponseEntity<List<Department>> getCompanyTypes() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(departmentRepository.findAll());

    }

    public ResponseEntity<Department> getCompanyTypeById(Integer id) {
        Optional<Department> department = departmentRepository.findById(id);

        return department.map(
                type -> ResponseEntity.status(HttpStatus.OK).body(type)).orElseGet(()
                -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));

    }
}
