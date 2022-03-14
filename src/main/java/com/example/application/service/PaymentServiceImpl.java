package com.example.application.service;

import com.example.application.model.Payment;
import com.example.application.repository.PaymentRepository;
import lombok.NonNull;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements  PaymentService{

    private final PaymentRepository paymentRepository;

    public PaymentServiceImpl(@NonNull @Lazy PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public Payment findById(Long id) {
        return paymentRepository.getById(id);
    }

    @Override
    public void removeById(Long id) {
        paymentRepository.deleteById(id);
    }
}
