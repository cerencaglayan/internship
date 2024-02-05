package com.example.demo.myproject.repository;

import com.example.demo.myproject.models.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserRole,Integer> {
    UserRole findByName(String name);
}
