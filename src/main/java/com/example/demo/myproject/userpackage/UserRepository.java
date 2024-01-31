package com.example.demo.myproject.userpackage;

import com.example.demo.myproject.departmentpackage.Department;
import com.example.demo.myproject.userrolepackage.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByName(String name);

    User findByEmail(String email);

    User findByDepartment(Department department);

    User findByUserRole(UserRole userRole);
}
