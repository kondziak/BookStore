package com.example.application.controller;

import com.example.application.model.User;
import com.example.application.service.UserService;
import lombok.NonNull;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/userData")
public class UserController {

    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserController(@NonNull @Lazy UserService userService, @NonNull @Lazy BCryptPasswordEncoder bcrypt) {
        this.userService = userService;
        this.bCryptPasswordEncoder = bcrypt;
    }

    @PostMapping("/changeName")
    public String changeName(@RequestParam(name = "first_name") String firstName){
        User user = userService.findUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        user.setFirst_name(firstName);
        return "redirect:/editData";
    }

    @PostMapping("/changeLastName")
    public String changeLastName(@RequestParam(name = "last_name") String lastName){
        User user = userService.findUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        user.setLast_name(lastName);
        return "redirect:/editData";
    }

    @PostMapping("/changePassword")
    public String changePassword(@RequestParam(name = "currentPassword") String currentPassword,
                                 @RequestParam(name = "newPassword") String newPassword,
                                 @RequestParam(name = "confirmPassword") String confirmPassword, Model model){
        User user = userService.findUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        boolean passwordCorrect = bCryptPasswordEncoder.matches(currentPassword,user.getPassword());
        if(!passwordCorrect){
            model.addAttribute("error","Incorrect old password");
        }
        else if(newPassword.compareTo(confirmPassword) != 0){
            model.addAttribute("error", "Both password need to be the same");
        }
        else{
            user.setPassword(bCryptPasswordEncoder.encode(newPassword));
        }
        return "edit_data";
    }


}
