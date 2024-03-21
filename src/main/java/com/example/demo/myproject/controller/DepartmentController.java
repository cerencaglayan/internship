package com.example.demo.myproject.controller;

import com.example.demo.myproject.models.CompanyType;
import com.example.demo.myproject.models.Department;
import com.example.demo.myproject.service.DepartmentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/department")
@PreAuthorize("hasAnyRole('ADMIN')")
public class DepartmentController {

    private final DepartmentService departmentService;

    @GetMapping
    public ResponseEntity<List<Department>> getCompanyTypes() {
        return departmentService.getCompanyTypes();
    };

    @GetMapping("/{id}")
    public ResponseEntity<Department> getCompanyById(@PathVariable Integer id) {
        return departmentService.getCompanyTypeById(id);
    }
}
