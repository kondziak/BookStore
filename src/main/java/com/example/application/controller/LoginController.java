package com.example.application.controller;

import com.example.application.model.Role;
import com.example.application.model.User;
import com.example.application.repository.UserRepository;
import lombok.NonNull;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class LoginController {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    public LoginController(@NonNull @Lazy UserRepository userRepository, @NonNull @Lazy BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/login")
    public String get_login_page(){
        return "login";
    }

    @RequestMapping(value = "/loginCheck",method = RequestMethod.POST)
    public String check_account(@RequestParam(value = "email",required = false) String email,
                                @RequestParam(value = "password",required = false) String password,
                                Model model){
        User user = userRepository.findByEmail(email);
        if(user == null || !passwordEncoder.matches(password,user.getPassword())){
            model.addAttribute("error", "Wrong email or password");
            return "login";
        }
        List<Role> roles = (List<Role>) user.getRoles().stream().toList();
        Role role = roles.get(0);
        if(role.getName().compareTo("USER_ROLE") == 0){
            return "/home";
        }
        return "redirect:/admin_panel";
    }


}
