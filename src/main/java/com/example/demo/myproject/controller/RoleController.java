package com.example.demo.myproject.controller;

import com.example.demo.myproject.models.Department;
import com.example.demo.myproject.models.UserRole;
import com.example.demo.myproject.service.RoleService;
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
@RequestMapping("/api/v1/role")
@PreAuthorize("hasAnyRole('ADMIN')")
public class RoleController {

    private final RoleService roleService;

    @GetMapping
    public ResponseEntity<List<UserRole>> getCompanyTypes() {
        return roleService.getCompanyTypes();
    };

    @GetMapping("/{id}")
    public ResponseEntity<UserRole> getCompanyById(@PathVariable Integer id) {
        return roleService.getCompanyTypeById(id);
    }


}
