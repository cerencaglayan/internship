package com.example.demo.myproject.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth/demo")
@RequiredArgsConstructor
public class DemController {

    @GetMapping
    public ResponseEntity<String> sayHi(){
        return  ResponseEntity.ok("hi its secured endpoint");
    }
}
