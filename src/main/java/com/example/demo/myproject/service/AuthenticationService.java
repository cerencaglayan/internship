package com.example.demo.myproject.service;

import com.example.demo.myproject.controller.dto.AuthenticationRequest;
import com.example.demo.myproject.controller.dto.AuthenticationResponse;
import com.example.demo.myproject.controller.dto.RegisterRequest;
import com.example.demo.myproject.models.User;
import com.example.demo.myproject.models.UserRole;
import com.example.demo.myproject.repository.UserRepository;
import com.example.demo.myproject.repository.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final UserRoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        var role = roleRepository.findByName("USER");
        System.out.println(role.getName());
        var user = User.builder()
                .name(request.getName())
                .surname(request.getSurname())
                .email(request.getEmail())
                .password( passwordEncoder.encode(request.getPassword()))
                .userRole(role)
                .build();
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();

    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {

        var user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        System.out.println(user.getName());
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );


        var jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

}
