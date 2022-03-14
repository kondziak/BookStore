package com.example.application.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cart_action")
public class CartController {


    @GetMapping
    public String getCart(){
        return "";
    }

}
