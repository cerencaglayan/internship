package com.example.demo.myproject.service;

import com.example.demo.myproject.models.Department;
import com.example.demo.myproject.models.UserRole;
import com.example.demo.myproject.repository.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final UserRoleRepository roleRepository;

    public ResponseEntity<List<UserRole>> getCompanyTypes() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(roleRepository.findAll());
    }

    public ResponseEntity<UserRole> getCompanyTypeById(Integer id) {
        Optional<UserRole> role = roleRepository.findById(id);

        return role.map(
                type -> ResponseEntity.status(HttpStatus.OK).body(type)).orElseGet(()
                -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }
}
