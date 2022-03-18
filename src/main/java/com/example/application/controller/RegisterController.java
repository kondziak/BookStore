package com.example.application.controller;

import com.example.application.model.User;
import com.example.application.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    public User user_registration() {
        return new User();
    }

    @GetMapping
    public String show_register() {
        return "register";
    }

    @PostMapping
    public String register_user(@ModelAttribute("user") User user, Model model) {
        try {
            userService.createUser(user);
        } catch (Exception e) {
            model.addAttribute("error", "User already exists");
            return "register";
        }
        return "index";
    }
}
