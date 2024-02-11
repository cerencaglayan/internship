package com.example.demo.myproject.service;

import com.example.demo.myproject.controller.dto.AuthenticationResponse;
import com.example.demo.myproject.controller.dto.UserAddRequest;
import com.example.demo.myproject.controller.dto.UserDto;
import com.example.demo.myproject.models.Department;
import com.example.demo.myproject.models.User;
import com.example.demo.myproject.models.UserRole;
import com.example.demo.myproject.repository.DepartmentRepository;
import com.example.demo.myproject.repository.UserRepository;
import com.example.demo.myproject.repository.UserRoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class AdminService {
    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final UserRoleRepository userRoleRepository;
    @Autowired
    private final DepartmentRepository departmentRepository;

    @Autowired
    private final PasswordEncoder passwordencoder;

    /*
    *  1- create new user
    *  2- find role
    *  3- find department
    *  4- add others
    *  5- save repository
    *
    * */
    public String addUser(UserAddRequest user) {
        User newUser = new User();

        UserRole userRole = userRoleRepository.findByName(user.getRole());
        Department department = departmentRepository.findByName(user.getDepartment());

        newUser.setUserRole(userRole);
        newUser.setDepartment(department);
        newUser.setActive(false);
        newUser.setEmail(user.getEmail());
        newUser.setName(user.getName());
        newUser.setPassword(null);
        newUser.setSurname(user.getSurname());
        userRepository.save(newUser);
        return "Success!";
    }

    /*
    * todo null değeri handle etme oonarılacak.
    *
    * */
    public ResponseEntity<List<UserDto>> getAllUsers() {


        List<User> users = userRepository.findAll();
        List<UserDto> userDtos = new ArrayList<>();

        for (User user : users) {
            // Do something with each user
            userDtos.add(
                    UserDto.builder()
                            .name(user.getName())
                            .surname(user.getSurname())
                            .role(user.getUserRole().getName())
                            .department(user.getDepartment().getName())
                            .build()
            );

        }


        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userDtos);
    }
}
