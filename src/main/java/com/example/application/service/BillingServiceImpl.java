package com.example.application.service;

import com.example.application.model.Billing;
import com.example.application.repository.BillingRepository;
import lombok.NonNull;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class BillingServiceImpl implements  BillingService{

    private final BillingRepository billingRepository;

    public BillingServiceImpl(@NonNull @Lazy BillingRepository billingRepository) {
        this.billingRepository = billingRepository;
    }

    @Override
    public Billing findById(Long id) {
        return billingRepository.getById(id);
    }

    @Override
    public void removeById(Long id) {
        billingRepository.deleteById(id);
    }
}
