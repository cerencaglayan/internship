package com.example.demo.myproject.controller;

import com.example.demo.myproject.models.User;
import com.example.demo.myproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.regex.Pattern;


@RestController
public class UserController {

    @Autowired
    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public User createUser(@RequestBody User user) {

        return userService.createUser(user);
    }

    @PostMapping("/signin")
    public String signIn(@RequestBody User user) {
        return userService.signIn(user);
    }


    @GetMapping("/signin")
    public String signIn() {
        return "sign in page";
    }

    @GetMapping("/login")
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView("login");
        return modelAndView;
    }

    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }

    @GetMapping("/logout")
    public String logout() {
        return "redirect:/login";
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("/login")
    public String logIn(User user) {
        /*
         * will implement later
         * */
        return "login page";
    }


    @PostMapping("/activate/{id}")
    public String activateUser(@PathVariable Integer userid) {
        return "";
    }


    @PostMapping("/resetpassword")
    public String resetPassword(@RequestBody User user) {
        if (user.isActive()) {

        }
        return "aktif değilsen naş";
    }

    /*
     * will implement later
     * */
    private boolean passwordValid(String password) {

        // Rule 1: Must have at least one uppercase character
        if (!password.equals(password.toLowerCase())) {
            return false;
        }

        // Rule 2: Must have at least one lowercase character
        if (!password.equals(password.toUpperCase())) {
            return false;
        }

        // Rule 3: Must have at least one numeric character
        if (!password.matches(".*\\d.*")) {
            return false;
        }

        // Rule 4: Must have at least one special symbol among @$.!-+
        if (!Pattern.compile("[@$.!\\-+]").matcher(password).find()) {
            return false;
        }

        // Rule 5: Password length should be between 8 and 20
        int length = password.length();
        return length >= 8 && length <= 20;
    }


}
