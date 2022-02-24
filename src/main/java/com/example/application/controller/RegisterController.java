package com.example.application.controller;

import com.example.application.model.UserRegistration;
import com.example.application.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/register")
public class RegisterController {
    private UserService userService;

    public RegisterController(UserService userService) {
        super();
        this.userService = userService;
    }

    @ModelAttribute("user")
    public UserRegistration user_registration(){
        return new UserRegistration();
    }

    @GetMapping
    public String show_register(){
        return "register";
    }

    @PostMapping
    public String register_user(@ModelAttribute("user") UserRegistration user_registration){
        userService.save(user_registration);
        return "index";
    }
}
