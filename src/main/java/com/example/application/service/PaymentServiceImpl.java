package com.example.application.service;

import com.example.application.model.Payment;
import com.example.application.model.User;
import com.example.application.repository.PaymentRepository;
import lombok.NonNull;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentServiceImpl implements  PaymentService{

    private final PaymentRepository paymentRepository;

    public PaymentServiceImpl(@NonNull @Lazy PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public List<Payment> findAllByUser(User user) {
        return paymentRepository.findAllByUser(user);
    }

    @Override
    public Payment findById(Long id) {
        return paymentRepository.getById(id);
    }

    @Override
    public void removeById(Long id) {
        paymentRepository.deleteById(id);
    }

    @Override
    public void save(Payment payment) {
        paymentRepository.save(payment);
    }
}
