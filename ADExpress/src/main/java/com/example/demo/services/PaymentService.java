package com.example.demo.services;

import com.example.demo.models.entity.Customer;
import com.example.demo.models.PaymentOrder;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.Transaction;

import java.util.List;

public interface PaymentService {
    String makePayment(PaymentOrder paymentOrder) throws Exception;
}
