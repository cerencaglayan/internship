package com.example.demo.myproject.controller;

import com.example.demo.myproject.controller.dto.UserAddRequest;
import com.example.demo.myproject.controller.dto.UserDto;
import com.example.demo.myproject.models.User;
import com.example.demo.myproject.service.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/admin")
@PreAuthorize("hasAnyRole('ADMIN')")
public class AdminController {


    private final AdminService adminService;

    @GetMapping()
    public String get(){
        return "admin controller";
    }

    @PostMapping("/addUser")
    public String addUser(@RequestBody UserAddRequest userAddRequest){
        return adminService.addUser(userAddRequest);
    }
    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return adminService.getAllUsers();
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable("id") Integer id) {
        return adminService.getUserById(id);
    }

}
