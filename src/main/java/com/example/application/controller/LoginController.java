package com.example.application.controller;

import com.example.application.model.Role;
import com.example.application.model.ShoppingCart;
import com.example.application.model.User;
import com.example.application.service.ShoppingCartService;
import com.example.application.service.UserService;
import lombok.NonNull;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class LoginController {

    private final UserService userService;

    private final BCryptPasswordEncoder passwordEncoder;

    public LoginController(@NonNull @Lazy UserService userService, @NonNull @Lazy BCryptPasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/login")
    public String getLoginPage(){
        return "login";
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public String checkAccount(@RequestParam(value = "username",required = false) String email,
                                @RequestParam(value = "password",required = false) String password,
                                Model model){
        User user = userService.findUserByEmail(email);
        if(user == null || !passwordEncoder.matches(password,user.getPassword())){
            model.addAttribute("error", "Wrong email or password");
            return "login";
        }
        List<Role> roles = user.getRoles().stream().toList();
        Role role = roles.get(0);
        if(role.getName().compareTo("USER_ROLE") == 0){
            return "redirect:/home";
        }
        return "redirect:/adminPanel";
    }

    @GetMapping("/logout")
    public String getLogout(){
        return "index";
    }

}
