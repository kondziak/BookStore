package com.example.application.service;

import com.example.application.model.User;
import com.example.application.model.UserRegistration;
import org.springframework.security.core.userdetails.UserDetailsService;
import java.util.List;

public interface UserService extends UserDetailsService {
    User save(UserRegistration userRegistration);
    void delete_user_by_id(Long id);
    List<User> getAll();
}
