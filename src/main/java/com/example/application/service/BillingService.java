package com.example.application.service;

import com.example.application.model.Billing;

import java.util.List;

public interface BillingService {
    Billing findById(Long id);
    void removeById(Long id);
}
