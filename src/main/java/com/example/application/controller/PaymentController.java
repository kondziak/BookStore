package com.example.application.controller;

import com.example.application.configuration.CardValidation;
import com.example.application.model.Payment;
import com.example.application.model.User;
import com.example.application.service.PaymentService;
import com.example.application.service.UserService;
import lombok.NonNull;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class PaymentController {

    private final PaymentService paymentService;
    private final UserService userService;

    public PaymentController(@NonNull @Lazy PaymentService paymentService, @NonNull @Lazy UserService userService) {
        this.paymentService = paymentService;
        this.userService = userService;
    }

    @GetMapping("/addCard")
    public String getCard(Model model){
        model.addAttribute("payment", new Payment());
        return "add_card";
    }

    @ModelAttribute("payment")
    public Payment getPayment(){
        return new Payment();
    }

    @PostMapping("/addCard")
    public String saveCard(Model model, @ModelAttribute("payment") Payment payment, Principal principal){
        boolean error = false;
        String message = "";
        if(payment.getExpiryYear() < 2022){
            error = true;
            message = "Wrong expiry year";
        }
        else if(!(payment.getExpiryMonth() > 0 && payment.getExpiryMonth() < 13)){
            error = true;
            message = "Wrong expiry month";
        }
        else if(!(payment.getCvc() > 99 && payment.getCvc() < 1000)){
            error = true;
            message = "Invalid cvc";
        }
        else if(!CardValidation.isValid(payment.getCardNumber())){
            error = true;
            message = "Invalid card number";
        }
        model.addAttribute("payment", new Payment());

        if(error){
            model.addAttribute("error",message);
            return "add_card";
        }
        User user = userService.findUserByEmail(principal.getName());
        payment.setUser(user);
        paymentService.save(payment);
        return "redirect:/add_card";
    }

    @GetMapping("/showCards")
    public String showCards(Model model, Principal principal){
        User user = userService.findUserByEmail(principal.getName());
        List<Payment> paymentList = paymentService.findAllByUser(user);
        model.addAttribute("payments" ,paymentList);
        return "show_cards";
    }




}
