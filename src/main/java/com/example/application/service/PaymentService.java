package com.example.application.service;

import com.example.application.model.Payment;
import com.example.application.model.User;

import java.util.List;

public interface PaymentService {
    List<Payment> findAllByUser(User user);
    Payment findById(Long id);
    void removeById(Long id);
    void save(Payment payment);
}
