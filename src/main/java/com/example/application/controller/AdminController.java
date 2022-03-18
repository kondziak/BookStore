package com.example.application.controller;

import com.example.application.service.UserService;
import lombok.NonNull;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AdminController {

    private UserService userService;

    public AdminController(@NonNull @Lazy UserService userService) {
        super();
        this.userService = userService;
    }

    @GetMapping("/admin_panel")
    public String get_all_users(Model model){
        model.addAttribute("users",userService.getAll());
        return "admin_panel";
    }

    @RequestMapping(value = "/delete_user",method = RequestMethod.POST)
    public String delete_user(@RequestParam(value = "id") Long id){
        userService.delete_user_by_id(id);
        return "redirect:/admin_panel";

    }

}
