package com.example.demo.myproject.userpackage;

import com.example.demo.myproject.userpackage.User;
import com.example.demo.myproject.userpackage.UserService;
import com.example.demo.myproject.userrolepackage.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;


@RestController
public class UserController {

    @Autowired
    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public User createUser(@RequestBody User user){

        return userService.createUser(user);
    }

    @PostMapping("/signin")
    public String signIn(@RequestBody User user){
        return userService.signIn(user);
    }


    @GetMapping("/signin")
    public String signIn(){
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
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @PostMapping("/login")
    public String logIn(User user){
        /*
        * will implement later
        * */
        return "login page";
    }


    @PostMapping("/activate/{id}")
    public String activateUser(@PathVariable Integer userid){
        return "";
    }



    @PostMapping("/resetpassword")
    public String resetPassword(@RequestBody User user){
        if (user.isActive()){

        }
        return "aktif değilsen naş";
    }

    /*
    * will implement later
    * */
    private String passwordValid(String password){
        return "";
    }







}
