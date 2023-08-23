package com.example.demo.private_lib.payment;

import com.example.demo.models.PaymentOrder;
import com.example.demo.models.entity.Customer;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.Transaction;

import java.util.List;

public interface PaymentModel {
    String processPayment(PaymentOrder paymentOrder) throws Exception;
}
