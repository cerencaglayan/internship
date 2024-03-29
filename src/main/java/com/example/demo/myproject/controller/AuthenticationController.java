package com.example.demo.myproject.controller;

import com.example.demo.myproject.controller.dto.*;
import com.example.demo.myproject.exception.TokenExpiredException;
import com.example.demo.myproject.models.User;
import com.example.demo.myproject.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/*
 * everyone can achieve this area.
 *
 * */
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;


    @PostMapping("/activate-user")
    public String activate(@RequestBody ActivateRequest activateRequest) throws Exception {

        return authenticationService.activate(activateRequest.getEmail());
    }

    @GetMapping("/activate-user")
    public String activate() {

        return "activate-user page";
    }

    @PutMapping("/set-password/{token}")
    public String setPassword(@PathVariable("token") String token, @RequestBody PasswordRequest
            passwordRequest) throws TokenExpiredException {
        return authenticationService.setPassword(token, passwordRequest.getPassword());
    }

    @GetMapping("/set-password/{token}")
    public String setPassword(@PathVariable("token") String token) {
        return "set-password page";
    }

    @GetMapping("/is-token-active/{token}")
    public String isTokenActive(@PathVariable("token") String token) {
        return authenticationService.isTokenActive(token);
    }

    @GetMapping("/reset-password")
    public String resetPassword() {
        return "reset password page;";
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody ActivateRequest email)  {

          return  authenticationService.resetPassword(email.getEmail());

    }

    @GetMapping("/signin")
    public String login() {

        return "login page";
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest request) {

        return authenticate(request);
    }

    private ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        return authenticationService.authenticate(request);
    }


}