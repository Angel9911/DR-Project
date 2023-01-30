package com.example.demo.services;

import com.example.demo.models.PaymentOrder;

public interface PaymentService {
    PaymentOrder makePayment();
    PaymentOrder executePayment();
}
