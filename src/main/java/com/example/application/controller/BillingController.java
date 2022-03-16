package com.example.application.controller;

import com.example.application.model.Billing;
import com.example.application.model.User;
import com.example.application.service.BillingService;
import com.example.application.service.UserService;
import lombok.NonNull;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

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
    public String getAddresses(Model model){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findUserByEmail(email);
        List<Billing> billingList = user.getBillingList();
        if(billingList.size() != 0){
            model.addAttribute("billings",billingList);
        }
        return "get_addresses";
    }

    @GetMapping("/addAddress")
    public String addAddress(Model model){
        Billing billing = new Billing();
        model.addAttribute("billing", billing);
        return "add_address";
    }

    @PostMapping("/addAddress")
    public String addAddress(@ModelAttribute("billing") Billing billing){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findUserByEmail(email);
        billing.setUser(user);
        billingService.save(billing);
        return "get_addresses";
    }

    @ModelAttribute("billing")
    public Billing createBilling(){return new Billing();}
}
