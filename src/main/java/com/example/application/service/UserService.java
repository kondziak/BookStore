package com.example.application.service;
import com.example.application.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import java.util.List;

public interface UserService extends UserDetailsService {
    User save(User user);
    User createUser(User user) throws Exception;
    User findUserByEmail(String email);
    void delete_user_by_id(Long id);
    List<User> getAll();
}
