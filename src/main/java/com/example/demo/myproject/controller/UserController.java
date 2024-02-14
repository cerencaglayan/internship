package com.example.demo.myproject.controller;

import com.example.demo.myproject.models.User;
import com.example.demo.myproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.regex.Pattern;


@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/hello")
    public String signIn() {
        return "hello user";
    }


    @GetMapping("/logout")
    public String logout() {
        return "redirect:/login";
    }





    /*
     * will implement later
     * */



}
