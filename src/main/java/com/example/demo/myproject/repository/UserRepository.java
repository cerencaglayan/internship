package com.example.demo.myproject.repository;

import com.example.demo.myproject.models.Department;
import com.example.demo.myproject.models.User;
import com.example.demo.myproject.models.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByName(String name);

    Optional<User> findByEmail(String email);

    List<User> findAllByUserRole(UserRole userRole);


}
