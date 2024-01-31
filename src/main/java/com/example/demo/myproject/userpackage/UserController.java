package com.example.demo.myproject.userpackage;

import com.example.demo.myproject.userrolepackage.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("/activate/{id}")
    public String activateUser(@PathVariable Integer userid){
        return "";
    }

    @PostMapping("/resetpassword")
    public String resetPassword(@RequestBody User user){
        return "";
    }







}
