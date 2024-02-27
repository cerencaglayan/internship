package com.example.demo.myproject.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/locale")
public class LocaleDemoController {

    private final MessageSource messageSource;

    public LocaleDemoController(MessageSource messageSource ) {
        this.messageSource = messageSource;
    }

    @GetMapping
    public String sayHello(HttpServletRequest request){

        System.out.println(LocaleContextHolder.getLocale());
        return messageSource.getMessage("greeting",null,LocaleContextHolder.getLocale());
    }




}
