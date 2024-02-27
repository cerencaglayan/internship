package com.example.demo.myproject.controller.dto;

import lombok.*;
import org.springframework.lang.Nullable;
/*
* safe user-get without password
* */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private String name;
    private String surname;
    private String email;
    private String role;
    private String department;
    private byte[] image;
}
