package com.example.demo;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class HelloWorldController {

    @RequestMapping("/hello")
    String hello(){
        return "Hello,world!";
    }


    @RequestMapping("/")
    String thePage(){
        return "this is the main page";
    }

}
