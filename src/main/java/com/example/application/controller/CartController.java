package com.example.application.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/cart_action")
public class CartController {

    @GetMapping("/showCart")
    public String getCart(){
        return "user_cart";
    }

    @PostMapping("/addToCart")
    public String setCart(@RequestParam(name = "quantity")Integer quantity, @RequestParam(name = "id")Long id, Model model){

        return "redirect:/book_action/book_detail?id=" + id.toString();
    }


}
