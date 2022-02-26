package com.example.application.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AppController {

    @GetMapping("/")
    public String get_start_page(){
        return "index";
    }

    @GetMapping("/home")
    public String get_main_page(){
        return "home";
    }
}
