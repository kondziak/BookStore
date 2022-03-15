package com.example.application.controller;

import com.example.application.model.User;
import com.example.application.service.BillingService;
import com.example.application.service.UserService;
import lombok.NonNull;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/billing")
public class BillingController {

    private final BillingService billingService;
    private final UserService userService;

    public BillingController(@NonNull @Lazy BillingService billingService,@NonNull @Lazy UserService userService) {
        this.billingService = billingService;
        this.userService = userService;
    }


    @GetMapping("/get_addresses")
    public String getAddresses(){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findUserByEmail(email);


        return "get_addresses";
    }

    @PostMapping("/addAddress")
    public String addAddress(){

        return "add_address";
    }
}
