package com.example.application.service;

import com.example.application.model.Billing;

public interface BillingService {
    Billing findById(Long id);
    Billing save(Billing billing);
    void removeById(Long id);
}
