package com.example.demo.myproject.controller.dto;

import com.example.demo.myproject.models.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;
/**
 * user adding request for admin. adding photo is not exist for now.
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserAddRequest {
    private String name;
    private String surname;
    private String email;
    private String password;
    private String role;
    private String department;
    private String company;


}
