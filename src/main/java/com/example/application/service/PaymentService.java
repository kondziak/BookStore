package com.example.application.service;

import com.example.application.model.Payment;

public interface PaymentService {

    Payment findById(Long id);
    void removeById(Long id);
}
